package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.obj.RichText
import com.petersamokhin.notionsdk.data.model.internal.obj.RichTextAnnotations
import com.petersamokhin.notionsdk.data.model.internal.obj.RichTextColor
import com.petersamokhin.notionsdk.data.model.internal.obj.RichTextType
import com.petersamokhin.notionsdk.data.model.result.NotionRichText
import com.petersamokhin.notionsdk.data.model.result.NotionRichTextAnnotations
import com.petersamokhin.notionsdk.data.model.result.NotionRichTextColor
import com.petersamokhin.notionsdk.data.model.result.NotionRichTextType

internal fun RichText.toDomain(): NotionRichText =
    when (this) {
        is RichText.Text -> NotionRichText.Text(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
        )
        is RichText.Mention -> when (mention) {
            is RichText.Mention.Value.User -> NotionRichText.Mention.User(
                plainText = plainText,
                url = href,
                type = type.toDomain(),
                annotations = annotations.toDomain(),
                user = mention.user.toDomain(),
            )
            is RichText.Mention.Value.Page -> NotionRichText.Mention.Page(
                plainText = plainText,
                url = href,
                type = type.toDomain(),
                annotations = annotations.toDomain(),
                id = mention.page.id,
            )
            is RichText.Mention.Value.Database -> NotionRichText.Mention.Database(
                plainText = plainText,
                url = href,
                type = type.toDomain(),
                annotations = annotations.toDomain(),
                id = mention.database.id,
            )
            is RichText.Mention.Value.Date -> NotionRichText.Mention.Date(
                plainText = plainText,
                url = href,
                type = type.toDomain(),
                annotations = annotations.toDomain(),
                start = mention.date.start,
                end = mention.date.end,
            )
            RichText.Mention.Value.LinkPreview -> NotionRichText.Mention.LinkPreview(
                plainText = plainText,
                url = href,
                type = type.toDomain(),
                annotations = annotations.toDomain(),
            )
        }
        is RichText.Equation -> NotionRichText.Equation(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
            expression = equation.expression,
        )
    }

internal fun RichTextType.toDomain(): NotionRichTextType =
    when (this) {
        RichTextType.Text -> NotionRichTextType.Text
        RichTextType.Mention -> NotionRichTextType.Mention
        RichTextType.Equation -> NotionRichTextType.Equation
    }

internal fun RichTextAnnotations.toDomain(): NotionRichTextAnnotations =
    NotionRichTextAnnotations(
        bold = bold,
        italic = italic,
        strikethrough = strikethrough,
        underline = underline,
        code = code,
        color = color.toDomain(),
    )

internal fun RichTextColor.toDomain(): NotionRichTextColor =
    when (this) {
        RichTextColor.Default -> NotionRichTextColor.Default
        RichTextColor.Gray -> NotionRichTextColor.Gray
        RichTextColor.Brown -> NotionRichTextColor.Brown
        RichTextColor.Orange -> NotionRichTextColor.Orange
        RichTextColor.Yellow -> NotionRichTextColor.Yellow
        RichTextColor.Green -> NotionRichTextColor.Green
        RichTextColor.Blue -> NotionRichTextColor.Blue
        RichTextColor.Purple -> NotionRichTextColor.Purple
        RichTextColor.Pink -> NotionRichTextColor.Pink
        RichTextColor.Red -> NotionRichTextColor.Red
        RichTextColor.GrayBackground -> NotionRichTextColor.GrayBackground
        RichTextColor.BrownBackground -> NotionRichTextColor.BrownBackground
        RichTextColor.OrangeBackground -> NotionRichTextColor.OrangeBackground
        RichTextColor.YellowBackground -> NotionRichTextColor.YellowBackground
        RichTextColor.GreenBackground -> NotionRichTextColor.GreenBackground
        RichTextColor.BlueBackground -> NotionRichTextColor.BlueBackground
        RichTextColor.PurpleBackground -> NotionRichTextColor.PurpleBackground
        RichTextColor.PinkBackground -> NotionRichTextColor.PinkBackground
        RichTextColor.RedBackground -> NotionRichTextColor.RedBackground
    }
