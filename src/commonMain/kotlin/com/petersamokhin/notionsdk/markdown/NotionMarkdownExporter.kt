package com.petersamokhin.notionsdk.markdown

import com.petersamokhin.notionsdk.Notion
import com.petersamokhin.notionsdk.data.model.result.NotionBlock
import kotlin.jvm.JvmStatic

@Suppress("KDocUnresolvedReference")
public interface NotionMarkdownExporter {

    /**
     * Convert a [block] to a simple Markdown string, without many features like HTML blocks, etc.
     */
    public fun export(
        blocks: List<NotionBlock>,
        settings: Settings = Settings(),
    ): String

    /**
     * Convert a [block] to a simple Markdown string, without many features like HTML blocks, etc.
     * Retrieve the children blocks as well.
     *
     * @param depthLevel How many times to retrieve the children blocks
     */
    public suspend fun exportRecursively(
        blocks: List<NotionBlock>,
        settings: Settings = Settings(),
        notion: Notion,
        depthLevel: Int = DEPTH_LEVEL_NO,
    ): String

    /**
     * @property todoCheckedPrefix Default is emoji, the simplest option. Might be `- [x]` for GFM
     * @property todoUncheckedPrefix Default is emoji, the simplest option. Might be `- [ ]` for GFM
     * @property todoCheckedStrikethrough Format checked to_do items' text as strikethrough
     * @property addExpiryNoticeForInternalFiles Internal (hosted) Notion files have expiry time. If enabled, a note will be added before such blocks.
     * @property formatEquationAsCode Self-explanatory
     */
    public data class Settings(
        public val todoCheckedPrefix: String = "☑",
        public val todoUncheckedPrefix: String = "☐",
        public val todoCheckedStrikethrough: Boolean = true,
        public val addExpiryNoticeForInternalFiles: Boolean = true,
        public val formatEquationAsCode: Boolean = true,
    )

    public companion object {
        public const val DEPTH_LEVEL_NO: Int = 0

        @JvmStatic
        public fun create(): NotionMarkdownExporter =
            NotionMarkdownExporterImpl()
    }
}