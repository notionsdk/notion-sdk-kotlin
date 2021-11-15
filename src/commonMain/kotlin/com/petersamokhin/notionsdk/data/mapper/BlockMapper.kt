package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.obj.Block
import com.petersamokhin.notionsdk.data.model.internal.obj.BlockFileValue
import com.petersamokhin.notionsdk.data.model.result.NotionBlock
import com.petersamokhin.notionsdk.data.model.result.NotionFile

internal fun Block.toDomain(): NotionBlock =
    when (this) {
        is Block.Paragraph -> NotionBlock.Paragraph(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = paragraph.text,
        )
        is Block.Code -> NotionBlock.Code(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = code.text,
            language = code.language,
        )
        is Block.HeadingOne -> NotionBlock.HeadingOne(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = heading.text,
        )
        is Block.HeadingTwo -> NotionBlock.HeadingTwo(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = heading.text,
        )
        is Block.HeadingThree -> NotionBlock.HeadingThree(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = heading.text,
        )
        is Block.BulletedListItem -> NotionBlock.BulletedListItem(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = bulletedListItem.text,
        )
        is Block.NumberedListItem -> NotionBlock.NumberedListItem(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = numberedListItem.text,
        )
        is Block.ToDo -> NotionBlock.ToDo(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = todo.text,
            checked = todo.checked,
        )
        is Block.Toggle -> NotionBlock.Toggle(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = toggle.text,
        )
        is Block.ChildPage -> NotionBlock.ChildPage(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            title = childPage.title,
        )
        is Block.ChildDatabase -> NotionBlock.ChildDatabase(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            title = childDatabase.title,
        )
        is Block.Embed -> NotionBlock.Embed(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
        is Block.Image -> NotionBlock.Image(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            file = image.toDomain(),
        )
        is Block.Video -> NotionBlock.Video(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            file = video.toDomain(),
        )
        is Block.File -> NotionBlock.File(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            file = file.toDomain(),
        )
        is Block.Pdf -> NotionBlock.Pdf(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            file = pdf.toDomain(),
        )
        is Block.Bookmark -> NotionBlock.Bookmark(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            caption = bookmark.caption,
            url = bookmark.url,
        )
        is Block.Callout -> NotionBlock.Callout(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = callout.text,
            icon = callout.icon.toDomain(),
        )
        is Block.Quote -> NotionBlock.Quote(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            text = quote.text,
        )
        is Block.Equation -> NotionBlock.Equation(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            expression = equation.expression,
        )
        is Block.Divider -> NotionBlock.Divider(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
        is Block.TableOfContents -> NotionBlock.TableOfContents(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
        is Block.Column -> NotionBlock.Column(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
        is Block.ColumnList -> NotionBlock.ColumnList(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
        is Block.LinkPreview -> NotionBlock.LinkPreview(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
            url = linkPreview.url,
        )
        is Block.Unsupported -> NotionBlock.Unsupported(
            id = id,
            archived = archived,
            createdTime = createdTime,
            lastEditedTime = lastEditedTime,
            hasChildren = hasChildren,
        )
    }

internal fun BlockFileValue.toDomain(): NotionFile =
    when {
        file != null -> NotionFile.File(url = file.url, expiryTime = file.expiryTime)
        external != null -> NotionFile.External(url = external.url)
        else -> error("cannot map block file value: $this")
    }

internal fun Block.Callout.Value.Icon.toDomain(): NotionBlock.Callout.Icon =
    when (this) {
        is Block.Callout.Value.Icon.Emoji -> NotionBlock.Callout.Icon.Emoji(emoji = emoji)
        is Block.Callout.Value.Icon.File -> NotionBlock.Callout.Icon.File(url = url, expiryTime = expiryTime)
    }