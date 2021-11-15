package com.petersamokhin.notionsdk.data.model.internal.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class PageProperty {
    abstract val id: String

    @Serializable
    @SerialName("title")
    data class Title(
        override val id: String,
        val title: List<Value>,
    ) : PageProperty() {
        @Serializable
        data class Value(
            @SerialName("plain_text")
            val plainText: String,
        )

        fun plainText(): String =
            title.joinToString("") { it.plainText }
    }

    @Serializable
    @SerialName("rich_text")
    data class Text(
        override val id: String,
        @SerialName("rich_text")
        val richText: List<Value>,
    ) : PageProperty() {
        @Serializable
        data class Value(
            @SerialName("plain_text")
            val plainText: String,
            val href: String?,
        )

        fun plainText(): String =
            richText.joinToString("") { it.plainText }
    }

    @Serializable
    @SerialName("number")
    data class Number(
        override val id: String,
        val number: Double? = null,
    ) : PageProperty()

    @Serializable
    @SerialName("select")
    data class Select(
        override val id: String,
        val select: Value? = null,
    ) : PageProperty() {
        @Serializable
        data class Value(
            val id: String,
            val name: String,
            val color: String,
        )
    }

    @Serializable
    @SerialName("multi_select")
    data class MultiSelect(
        override val id: String,
        @SerialName("multi_select")
        val multiSelect: List<Select.Value>,
    ) : PageProperty()

    @Serializable
    @SerialName("date")
    data class Date(
        override val id: String,
        val date: Value? = null,
    ) : PageProperty() {
        @Serializable
        data class Value(
            val start: String,
            val end: String? = null,
        )
    }

    @Serializable
    @SerialName("people")
    data class People(
        override val id: String,
        val people: List<Value>,
    ) : PageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("person")
            data class Person(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
                val person: User,
            ) : Value() {
                @Serializable
                data class User(
                    val email: String,
                )
            }

            @Serializable
            @SerialName("bot")
            data class Bot(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
            ) : Value()
        }
    }

    @Serializable
    @SerialName("files")
    data class Files(
        override val id: String,
        val files: List<Value>,
    ) : PageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("external")
            data class External(
                val url: String,
            ) : Value()

            @Serializable
            @SerialName("file")
            data class File(
                val name: String,
                val file: Item,
            ) : Value() {
                @Serializable
                data class Item(
                    val url: String,
                    @SerialName("expiry_time")
                    val expiryTime: String,
                )
            }
        }
    }

    @Serializable
    @SerialName("checkbox")
    data class Checkbox(
        override val id: String,
        val checkbox: Boolean,
    ) : PageProperty()

    @Serializable
    @SerialName("url")
    data class Url(
        override val id: String,
        val url: String? = null,
    ) : PageProperty()

    @Serializable
    @SerialName("email")
    data class Email(
        override val id: String,
        val email: String? = null,
    ) : PageProperty()

    @Serializable
    @SerialName("phone_number")
    data class PhoneNumber(
        override val id: String,
        @SerialName("phone_number")
        val phoneNumber: String? = null,
    ) : PageProperty()

    @Serializable
    @SerialName("formula")
    data class Formula(
        override val id: String,
        val formula: Value,
    ) : PageProperty() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("string")
            data class Str(val string: String) : Value()

            @Serializable
            @SerialName("number")
            data class Number(val number: Double) : Value()

            @Serializable
            @SerialName("boolean")
            data class Bool(val boolean: Boolean) : Value()

            @Serializable
            @SerialName("date")
            data class Date(val date: String) : Value()
        }
    }

    // todo: empty because no samples provided, only the description which is not reliable
    @Serializable
    @SerialName("relation")
    data class Relation(
        override val id: String,
    ) : PageProperty()

    @Serializable
    @SerialName("created_time")
    data class CreatedTime(
        override val id: String,
        @SerialName("created_time")
        val createdTime: String,
    ) : PageProperty()

    @Serializable
    @SerialName("last_edited_time")
    data class LastEditedTime(
        override val id: String,
        @SerialName("last_edited_time")
        val lastEditedTime: String,
    ) : PageProperty()

    @Serializable
    @SerialName("created_by")
    data class CreatedBy(
        override val id: String,
        @SerialName("created_by")
        val createdBy: People.Value.Person,
    ) : PageProperty()

    @Serializable
    @SerialName("last_edited_by")
    data class LastEditedBy(
        override val id: String,
        @SerialName("last_edited_by")
        val lastEditedBy: People.Value.Person,
    ) : PageProperty()

    @Serializable
    @SerialName("rollup")
    data class Rollup(override val id: String) : PageProperty()
}