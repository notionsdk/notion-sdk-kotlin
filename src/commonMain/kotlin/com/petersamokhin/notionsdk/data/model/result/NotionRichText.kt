package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class NotionRichText {
    @SerialName("plain_text")
    public abstract val plainText: String
    public abstract val href: String?
    public abstract val type: NotionRichTextType
    public abstract val annotations: NotionRichTextAnnotations

    @Serializable
    @SerialName("text")
    public data class Text(
        @SerialName("plain_text")
        public override val plainText: String,
        public override val href: String?,
        public override val type: NotionRichTextType,
        public override val annotations: NotionRichTextAnnotations,
        public val text: Value,
    ) : NotionRichText() {

        @Serializable
        public data class Value(
            public val content: String,
            public val link: NotionRichTextInlineLink? = null,
        )
    }

    @Serializable
    @SerialName("mention")
    public data class Mention(
        @SerialName("plain_text")
        public override val plainText: String,
        public override val href: String?,
        public override val type: NotionRichTextType,
        public override val annotations: NotionRichTextAnnotations,
        public val mention: Value,
    ) : NotionRichText() {

        @Serializable
        public sealed class Value {
            @Serializable
            @SerialName("user")
            public data class User(
                public val user: NotionUser,
            ) : Value()

            @Serializable
            @SerialName("page")
            public data class Page(
                public val page: Value,
            ) : Value() {

                @Serializable
                public data class Value(
                    public val id: String,
                )
            }

            @Serializable
            @SerialName("database")
            public data class Database(
                public val database: Value,
            ) : Value() {

                @Serializable
                public data class Value(
                    public val id: String,
                )
            }

            @Serializable
            @SerialName("date")
            public data class Date(
                public val date: Value,
            ) : Value() {

                @Serializable
                public data class Value(
                    public val start: String? = null,
                    public val end: String? = null,
                )
            }

            // todo: broken api example provided
            @Serializable
            @SerialName("link_preview")
            public object LinkPreview : Value()
        }
    }

    @Serializable
    @SerialName("equation")
    public data class Equation(
        @SerialName("plain_text")
        public override val plainText: String,
        public override val href: String?,
        public override val type: NotionRichTextType,
        public override val annotations: NotionRichTextAnnotations,

        public val equation: Value,
    ) : NotionRichText() {

        @Serializable
        public data class Value(
            public val expression: String,
        )
    }
}

@Serializable
public data class NotionRichTextInlineLink(
    public val url: String,
)

@Serializable
public enum class NotionRichTextType {
    @SerialName("text")
    Text,

    @SerialName("mention")
    Mention,

    @SerialName("equation")
    Equation;
}

@Serializable
public data class NotionRichTextAnnotations(
    public val bold: Boolean,
    public val italic: Boolean,
    public val strikethrough: Boolean,
    public val underline: Boolean,
    public val code: Boolean,
    public val color: NotionRichTextColor,
)

@Serializable
public enum class NotionRichTextColor {
    @SerialName("default")
    Default,

    @SerialName("gray")
    Gray,

    @SerialName("brown")
    Brown,

    @SerialName("orange")
    Orange,

    @SerialName("yellow")
    Yellow,

    @SerialName("green")
    Green,

    @SerialName("blue")
    Blue,

    @SerialName("purple")
    Purple,

    @SerialName("pink")
    Pink,

    @SerialName("red")
    Red,

    @SerialName("gray_background")
    GrayBackground,

    @SerialName("brown_background")
    BrownBackground,

    @SerialName("orange_background")
    OrangeBackground,

    @SerialName("yellow_background")
    YellowBackground,

    @SerialName("green_background")
    GreenBackground,

    @SerialName("blue_background")
    BlueBackground,

    @SerialName("purple_background")
    PurpleBackground,

    @SerialName("pink_background")
    PinkBackground,

    @SerialName("red_background")
    RedBackground,
}