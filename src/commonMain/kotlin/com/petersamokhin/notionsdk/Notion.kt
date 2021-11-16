package com.petersamokhin.notionsdk

import com.petersamokhin.notionsdk.data.NotionApiVersion
import com.petersamokhin.notionsdk.data.model.result.NotionBlock
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseSchema
import com.petersamokhin.notionsdk.data.model.result.NotionDatabase
import io.ktor.client.*
import io.ktor.utils.io.core.*
import kotlin.jvm.JvmStatic

public interface Notion : Closeable {
    public val token: String
    public fun setHttpClient(newHttpClient: HttpClient)
    public fun setToken(token: String)

    /**
     * @see <a href="https://developers.notion.com/reference/post-database-query">Notion documentation</a>
     */
    public suspend fun queryDatabase(
        databaseId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionDatabase

    /**
     * Notion API filter & sort params are too complicated to cover all the cases via strictly-typed models.
     *
     * @param jsonRequestBody Will be sent as the JSON request body.
     * @see <a href="https://developers.notion.com/reference/post-database-query">Notion documentation</a>
     */
    public suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionDatabase

    /**
     * @see <a href="https://developers.notion.com/reference/retrieve-a-database">Notion documentation</a>
     */
    public suspend fun retrieveDatabase(
        databaseId: String,
    ): NotionDatabaseSchema

    /**
     * @see <a href="https://developers.notion.com/reference/retrieve-a-block">Notion documentation</a>
     */
    public suspend fun retrieveBlock(
        blockId: String,
    ): NotionBlock

    /**
     * @see <a href="https://developers.notion.com/reference/get-block-children">Notion documentation</a>
     */
    public suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): List<NotionBlock>

    public companion object {
        public const val HEADER_VERSION: String = "Notion-Version"
        public const val API_BASE_URL: String = "https://api.notion.com/v1"

        @JvmStatic
        public fun fromToken(
            token: String,
            version: NotionApiVersion = NotionApiVersion.V_2021_08_16,
            httpClient: HttpClient
        ): Notion =
            NotionImpl(token, version, httpClient)
    }
}