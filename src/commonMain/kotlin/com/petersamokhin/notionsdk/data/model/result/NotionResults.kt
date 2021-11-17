package com.petersamokhin.notionsdk.data.model.result

import com.petersamokhin.notionsdk.data.model.serializer.NotionResultsTypedSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = NotionResultsTypedSerializer::class)
public data class NotionResults<T>(
    val results: List<T>,

    @SerialName("next_cursor")
    val nextCursor: String? = null,
    @SerialName("has_more")
    val hasMore: Boolean,
)

@Serializable
public data class NotionDatabaseRow(
    val id: String,
    val columns: Map<String, NotionDatabaseColumn>,
)

@Serializable
public data class NotionDatabaseColumn(
    val key: String,
    val value: NotionDatabaseProperty,
)

@Serializable
public sealed class NotionDatabaseProperty {
    public abstract val id: String

    @Serializable
    @SerialName("title")
    public data class Title(override val id: String, val text: String) : NotionDatabaseProperty()

    @Serializable
    @SerialName("rich_text")
    public data class Text(override val id: String, val text: String, val parts: List<Part>) :
        NotionDatabaseProperty() {
        @Serializable
        public data class Part(val text: String, val url: String?)
    }

    @Serializable
    @SerialName("number")
    public data class Number(override val id: String, val number: Double?) : NotionDatabaseProperty()

    @Serializable
    @SerialName("select")
    public data class Select(
        override val id: String,
        val selected: Option?,
    ) : NotionDatabaseProperty() {
        @Serializable
        public data class Option(
            val id: String,
            val name: String,
        )
    }

    @Serializable
    @SerialName("multi_select")
    public data class MultiSelect(
        override val id: String,
        val selected: List<Select.Option>,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("date")
    public data class Date(override val id: String, val start: String?, val end: String?) : NotionDatabaseProperty()

    @Serializable
    @SerialName("people")
    public data class People(override val id: String, val people: List<Person>) : NotionDatabaseProperty() {
        @Serializable
        public sealed class Person {
            @Serializable
            @SerialName("user")
            public data class User(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String?,
                val email: String,
            ) : Person()

            @Serializable
            @SerialName("user")
            public data class Bot(
                val id: String,
                val name: String,
                @SerialName("avatar_url")
                val avatarUrl: String? = null,
            ) : Person()
        }
    }

    @Serializable
    @SerialName("files")
    public data class Files(override val id: String, val files: List<Item>) : NotionDatabaseProperty() {
        @Serializable
        public data class Item(val url: String, val name: String?, val expiryTime: String?, val type: Type) {
            @Serializable
            public enum class Type {
                @SerialName("external")
                External,

                @SerialName("file")
                File;
            }
        }
    }

    @Serializable
    @SerialName("checkbox")
    public data class Checkbox(override val id: String, val selected: Boolean) : NotionDatabaseProperty()

    @Serializable
    @SerialName("url")
    public data class Url(override val id: String, val url: String? = null) : NotionDatabaseProperty()

    @Serializable
    @SerialName("email")
    public data class Email(override val id: String, val email: String? = null) : NotionDatabaseProperty()

    @Serializable
    @SerialName("phone_number")
    public data class PhoneNumber(
        override val id: String,
        @SerialName("phone_number")
        val phoneNumber: String? = null,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("formula")
    public data class Formula(
        override val id: String, val formula: Item,
    ) : NotionDatabaseProperty() {
        @Serializable
        public sealed class Item {
            @Serializable
            @SerialName("string")
            public data class Str(val string: String) : Item()

            @Serializable
            @SerialName("number")
            public data class Number(val number: Double) : Item()

            @Serializable
            @SerialName("boolean")
            public data class Bool(val boolean: Boolean) : Item()

            @Serializable
            @SerialName("date")
            public data class Date(val date: String) : Item()
        }
    }

    // todo: empty because no samples provided, only the description which is not reliable
    @Serializable
    @SerialName("relation")
    public data class Relation(override val id: String) : NotionDatabaseProperty()

    @Serializable
    @SerialName("created_time")
    public data class CreatedTime(
        override val id: String,
        @SerialName("created_time")
        val createdTime: String,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("last_edited_time")
    public data class LastEditedTime(
        override val id: String,
        @SerialName("last_edited_time")
        val lastEditedTime: String,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("created_by")
    public data class CreatedBy(
        override val id: String,
        @SerialName("created_by")
        val createdBy: People.Person,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("last_edited_by")
    public data class LastEditedBy(
        override val id: String,
        @SerialName("last_edited_by")
        val lastEditedBy: People.Person,
    ) : NotionDatabaseProperty()

    @Serializable
    @SerialName("rollup")
    public data class Rollup(
        override val id: String,
    ) : NotionDatabaseProperty()
}