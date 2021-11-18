package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class NotionBlock {
    public abstract val id: String
    public abstract val archived: Boolean

    @SerialName("created_time")
    public abstract val createdTime: String

    @SerialName("last_edited_time")
    public abstract val lastEditedTime: String

    @SerialName("has_children")
    public abstract val hasChildren: Boolean

    @SerialName("paragraph")
    @Serializable
    public data class Paragraph(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("code")
    @Serializable
    public data class Code(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        val text: List<NotionRichText>,
        val language: String,
    ) : NotionBlock()

    @SerialName("heading_1")
    @Serializable
    public data class HeadingOne(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("heading_2")
    @Serializable
    public data class HeadingTwo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("heading_3")
    @Serializable
    public data class HeadingThree(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("bulleted_list_item")
    @Serializable
    public data class BulletedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("numbered_list_item")
    @Serializable
    public data class NumberedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("to_do")
    @Serializable
    public data class ToDo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
        public val checked: Boolean,
    ) : NotionBlock()

    @SerialName("toggle")
    @Serializable
    public data class Toggle(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("child_page")
    @Serializable
    public data class ChildPage(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val title: String,
    ) : NotionBlock()

    @SerialName("child_database")
    @Serializable
    public data class ChildDatabase(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val title: String,
    ) : NotionBlock()

    // todo: actually handled by "bookmark"
    @SerialName("embed")
    @Serializable
    public data class Embed(
        override val id: String,
        override val archived: Boolean,
        override val createdTime: String,
        override val lastEditedTime: String,
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("image")
    @Serializable
    public data class Image(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("video")
    @Serializable
    public data class Video(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("file")
    @Serializable
    public data class File(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("pdf")
    @Serializable
    public data class Pdf(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("bookmark")
    @Serializable
    public data class Bookmark(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val caption: List<NotionRichText>,
        public val url: String,
    ) : NotionBlock()

    @SerialName("callout")
    @Serializable
    public data class Callout(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val text: List<NotionRichText>,
        public val icon: NotionIcon,
    ) : NotionBlock()

    @SerialName("quote")
    @Serializable
    public data class Quote(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("equation")
    @Serializable
    public data class Equation(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,

        public val expression: String,
    ) : NotionBlock()

    @SerialName("divider")
    @Serializable
    public data class Divider(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("table_of_contents")
    @Serializable
    public data class TableOfContents(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("column")
    @Serializable
    public data class Column(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("column_list")
    @Serializable
    public data class ColumnList(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("link_preview")
    @Serializable
    public data class LinkPreview(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
        public val url: String,
    ) : NotionBlock()

    @SerialName("unsupported")
    @Serializable
    public data class Unsupported(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time")
        override val createdTime: String,
        @SerialName("last_edited_time")
        override val lastEditedTime: String,
        @SerialName("has_children")
        override val hasChildren: Boolean,
    ) : NotionBlock()
}

public interface NotionFileBlock {
    public val file: NotionFile
}