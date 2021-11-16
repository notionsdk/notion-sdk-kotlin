package com.petersamokhin.notionsdk.data.model.internal.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class RichText {
    @SerialName("plain_text")
    abstract val plainText: String
    abstract val href: String?
    abstract val type: RichTextType
    abstract val annotations: RichTextAnnotations

    @Serializable
    @SerialName("text")
    data class Text(
        @SerialName("plain_text")
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val annotations: RichTextAnnotations,
        val text: Value,
    ) : RichText() {

        @Serializable
        data class Value(
            val content: String,
            val link: RichTextInlineLink? = null,
        )
    }

    @Serializable
    @SerialName("mention")
    data class Mention(
        @SerialName("plain_text")
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val annotations: RichTextAnnotations,
        val mention: Value,
    ) : RichText() {

        @Serializable
        internal sealed class Value {
            @Serializable
            @SerialName("user")
            data class User(
                val user: com.petersamokhin.notionsdk.data.model.internal.obj.User,
            ) : Value()

            @Serializable
            @SerialName("page")
            data class Page(
                val page: Value,
            ) : Value() {

                @Serializable
                data class Value(
                    val id: String,
                )
            }

            @Serializable
            @SerialName("database")
            data class Database(
                val database: Value,
            ) : Value() {

                @Serializable
                data class Value(
                    val id: String,
                )
            }

            @Serializable
            @SerialName("date")
            data class Date(
                val date: Value,
            ) : Value() {

                @Serializable
                data class Value(
                    val start: String? = null,
                    val end: String? = null,
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
    data class Equation(
        @SerialName("plain_text")
        override val plainText: String,
        override val href: String?,
        override val type: RichTextType,
        override val annotations: RichTextAnnotations,

        val equation: Value,
    ) : RichText() {

        @Serializable
        data class Value(
            val expression: String,
        )
    }
}

@Serializable
internal data class RichTextInlineLink(
    val url: String,
)

@Serializable
public enum class RichTextType {
    @SerialName("text")
    Text,

    @SerialName("mention")
    Mention,

    @SerialName("equation")
    Equation;
}

@Serializable
internal data class RichTextAnnotations(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: RichTextColor,
)

@Serializable
public enum class RichTextColor {
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