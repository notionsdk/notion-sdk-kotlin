package com.petersamokhin.notionsdk

import com.petersamokhin.notionsdk.data.NotionApiVersion
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
     * @see [Notion documentation](https://developers.notion.com/reference/post-database-query)
     */
    public suspend fun queryDatabase(
        databaseId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionDatabase

    /**
     * @see [Notion documentation](https://developers.notion.com/reference/retrieve-a-database)
     */
    public suspend fun retrieveDatabase(
        databaseId: String,
    ): NotionDatabaseSchema

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