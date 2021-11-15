package com.petersamokhin.notionsdk.markdown

import com.petersamokhin.notionsdk.Notion
import com.petersamokhin.notionsdk.data.model.result.*

internal class NotionMarkdownExporterImpl : NotionMarkdownExporter {
    override fun export(
        blocks: List<NotionBlock>,
        settings: NotionMarkdownExporter.Settings,
    ): String {
        var resultMarkdown = ""

        var numberedListItemIndex = 0
        var lastBlockWasList = false

        for (block in blocks) {
            if (block is NotionBlock.NumberedListItem) {
                numberedListItemIndex++
            } else {
                numberedListItemIndex = 0
            }

            var currentBlockMarkdown = block.toMarkdown(settings, numberedListItemIndex)
            val currentBlockIsList = block is NotionBlock.NumberedListItem || block is NotionBlock.BulletedListItem

            if (lastBlockWasList && !currentBlockIsList && currentBlockMarkdown != null) {
                currentBlockMarkdown = "\n$currentBlockMarkdown"
            }

            lastBlockWasList = currentBlockIsList
            resultMarkdown += currentBlockMarkdown?.let { md -> md + '\n' }.orEmpty()
        }

        return resultMarkdown
    }

    // method body is duplicated to make the other method non-suspend
    override suspend fun exportRecursively(
        blocks: List<NotionBlock>,
        settings: NotionMarkdownExporter.Settings,
        notion: Notion,
        depthLevel: Int,
    ): String = exportRecursively(blocks, settings, notion, depthLevel, children = false)

    private suspend fun exportRecursively(
        blocks: List<NotionBlock>,
        settings: NotionMarkdownExporter.Settings,
        notion: Notion,
        depthLevel: Int,
        children: Boolean,
    ): String {
        var resultMarkdown = ""

        var numberedListItemIndex = 0
        var lastBlockWasList = false

        for (block in blocks) {
            if (block is NotionBlock.NumberedListItem) {
                numberedListItemIndex++
            } else {
                numberedListItemIndex = 0
            }

            var currentBlockMarkdown = block.toMarkdown(settings, numberedListItemIndex)
            val currentBlockIsList = block is NotionBlock.NumberedListItem || block is NotionBlock.BulletedListItem

            if (lastBlockWasList && !currentBlockIsList && currentBlockMarkdown != null) {
                currentBlockMarkdown = "\n$currentBlockMarkdown"
            }

            lastBlockWasList = currentBlockIsList
            resultMarkdown += currentBlockMarkdown?.let { md ->
                ("    " * depthLevel.coerceAtMost(1)).takeIf { children }.orEmpty() + md + '\n'
            }.orEmpty()

            if (block.hasChildren && depthLevel > 0) {
                val blockChildren = notion.retrieveBlockChildren(block.id)

                resultMarkdown += exportRecursively(blockChildren, settings, notion, depthLevel - 1, children = true) + '\n'
            }
        }

        return resultMarkdown
    }

    private fun NotionBlock.toMarkdown(
        settings: NotionMarkdownExporter.Settings,
        numberedListItemIndex: Int?,
    ): String? =
        when (this) {
            is NotionBlock.Paragraph -> text.toMarkdown(settings)
            is NotionBlock.Code -> {
                val codeLang = if (language == "plain text") "" else language
                val codeText = text.toMarkdown(settings)

                "```$codeLang\n${codeText}\n```"
            }
            is NotionBlock.HeadingOne -> "# ${text.toMarkdown(settings)}"
            is NotionBlock.HeadingTwo -> "## ${text.toMarkdown(settings)}"
            is NotionBlock.HeadingThree -> "### ${text.toMarkdown(settings)}"
            is NotionBlock.BulletedListItem -> "- ${text.toMarkdown(settings)}"
            is NotionBlock.NumberedListItem -> "$numberedListItemIndex. ${text.toMarkdown(settings)}"
            is NotionBlock.ToDo -> {
                val formattedText = text.let {
                    if (settings.todoCheckedStrikethrough && checked) {
                        text.map { textEntity -> textEntity.forceStrikethrough() }
                    } else {
                        text
                    }
                }
                val prefix = if (checked) settings.todoCheckedPrefix else settings.todoUncheckedPrefix

                "$prefix ${formattedText.toMarkdown(settings)}"
            }
            is NotionBlock.Toggle -> "> ▶ ${text.toMarkdown(settings)}"
            // todo: https://notion.so/workspace/:id is buggy with multiple children, thus there is no url
            is NotionBlock.ChildPage -> "### Notion page ($id): «$title»"
            // todo: https://notion.so/workspace/:id is buggy with multiple children, thus there is no url
            is NotionBlock.ChildDatabase -> "### Notion database ($id): «$title»"
            is NotionBlock.Image,
            is NotionBlock.Video,
            is NotionBlock.File,
            is NotionBlock.Pdf,
            -> {
                (this as NotionFileBlock)
                    .toMarkdown(
                        image = this is NotionBlock.Image,
                        altName = this::class.simpleName ?: "file",
                        settings = settings,
                    )
            }
            is NotionBlock.Bookmark -> "[${
                caption.toMarkdown(settings).takeIf(String::isNotBlank) ?: "bookmark"
            }]($url)"
            is NotionBlock.Callout -> "> ${icon.toMarkdown(settings)} ${text.toMarkdown(settings)}\n"
            is NotionBlock.Quote -> "> ${text.toMarkdown(settings)}\n"
            is NotionBlock.Equation -> if (settings.formatEquationAsCode) "`${expression}`" else expression
            is NotionBlock.Divider -> "\n---\n"
            is NotionBlock.LinkPreview -> url

            // empty objects, etc.
            is NotionBlock.TableOfContents,
            is NotionBlock.Column,
            is NotionBlock.ColumnList,
            is NotionBlock.Unsupported,
            is NotionBlock.Embed,
            -> null
        }

    private fun List<NotionRichText>.toMarkdown(settings: NotionMarkdownExporter.Settings): String =
        joinToString("") { textEntity -> textEntity.toMarkdown(settings) }

    private fun NotionRichText.toMarkdown(settings: NotionMarkdownExporter.Settings): String {
        if (plainText.isBlank()) return plainText

        var modifiers = ""
        val trimmedPlainText = plainText.trim()

        if (annotations.bold) {
            modifiers += "**"
        }

        if (annotations.italic) {
            modifiers += "*"
        }

        if (annotations.strikethrough) {
            modifiers += "~~"
        }

        if (annotations.code || (type == NotionRichTextType.Equation && settings.formatEquationAsCode)) {
            modifiers += "`"
        }

        var formattedText =
            plainText.replace(trimmedPlainText, "${modifiers}${trimmedPlainText}${modifiers.reversed()}")

        if (href != null) {
            formattedText = "[$formattedText]($href)"
        }

        return formattedText
    }

    private fun NotionRichText.forceStrikethrough(): NotionRichText =
        when (this) {
            is NotionRichText.Text -> copy(annotations = annotations.copy(strikethrough = true))
            is NotionRichText.Mention -> copy(annotations = annotations.copy(strikethrough = true))
            is NotionRichText.Equation -> copy(annotations = annotations.copy(strikethrough = true))
        }

    private fun NotionBlock.Callout.Icon.toMarkdown(settings: NotionMarkdownExporter.Settings): String =
        when (this) {
            is NotionBlock.Callout.Icon.Emoji -> emoji
            is NotionBlock.Callout.Icon.File -> {
                val resultMarkdown = "![image](${url})"

                if (expiryTime != null && settings.addExpiryNoticeForInternalFiles) {
                    "*( below file link will expire at $expiryTime )*\n$resultMarkdown"
                } else {
                    resultMarkdown
                }
            }
        }

    private fun NotionFileBlock.toMarkdown(
        image: Boolean,
        altName: String,
        settings: NotionMarkdownExporter.Settings,
    ): String {
        val resultMarkdown = (if (image) "!" else "") + "[$altName](${file.url})"
        val expiryTime = (file as? NotionFile.File)?.expiryTime

        return if (expiryTime != null && settings.addExpiryNoticeForInternalFiles) {
            "*( below file link will expire at $expiryTime )*\n$resultMarkdown"
        } else {
            resultMarkdown
        }
    }
}

private operator fun String.times(count: Int): String =
    (0 until count).joinToString("") { this }
