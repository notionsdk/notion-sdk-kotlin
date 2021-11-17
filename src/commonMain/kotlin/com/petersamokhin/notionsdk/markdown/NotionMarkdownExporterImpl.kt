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
    ): String = exportRecursively(blocks, settings, notion, depthLevel, depthLevel)

    private suspend fun exportRecursively(
        blocks: List<NotionBlock>,
        settings: NotionMarkdownExporter.Settings,
        notion: Notion,
        initialDepth: Int,
        currentDepth: Int,
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

            val indentation = "    " * (initialDepth - currentDepth)
            lastBlockWasList = currentBlockIsList
            resultMarkdown += currentBlockMarkdown?.let { md -> indentation + md + '\n' }.orEmpty()

            if (block.hasChildren && currentDepth > 0) {
                val blockChildren = block.getAllChildren(notion)

                resultMarkdown += exportRecursively(
                    blocks = blockChildren,
                    settings = settings,
                    notion = notion,
                    initialDepth = initialDepth,
                    currentDepth = currentDepth - 1
                ) + '\n'
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
            is NotionBlock.ChildPage -> "### \uD83D\uDD17 [$title](https://notion.so/${id.replace("-", "")})"
            is NotionBlock.ChildDatabase -> "### \uD83D\uDD17 [$title](https://notion.so/${id.replace("-", "")})"
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

        if (url != null) {
            formattedText = "[$formattedText]($url)"
        }

        return formattedText
    }

    private fun NotionRichText.forceStrikethrough(): NotionRichText =
        when (this) {
            is NotionRichText.Text -> copy(annotations = annotations.copy(strikethrough = true))
            is NotionRichText.Mention -> when (this) {
                is NotionRichText.Mention.User -> copy(annotations = annotations.copy(strikethrough = true))
                is NotionRichText.Mention.Page -> copy(annotations = annotations.copy(strikethrough = true))
                is NotionRichText.Mention.Database -> copy(annotations = annotations.copy(strikethrough = true))
                is NotionRichText.Mention.Date -> copy(annotations = annotations.copy(strikethrough = true))
                is NotionRichText.Mention.LinkPreview -> copy(annotations = annotations.copy(strikethrough = true))
            }
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

    private suspend fun NotionBlock.getAllChildren(notion: Notion): List<NotionBlock> {
        var lastResponse = notion.retrieveBlockChildren(id)
        val result = lastResponse.results.toMutableList()

        while (lastResponse.hasMore && lastResponse.nextCursor != null) {
            lastResponse = notion.retrieveBlockChildren(id, startCursor = lastResponse.nextCursor)
            result += lastResponse.results
        }

        return result
    }
}

private operator fun String.times(count: Int): String =
    (0 until count).joinToString("") { this }
