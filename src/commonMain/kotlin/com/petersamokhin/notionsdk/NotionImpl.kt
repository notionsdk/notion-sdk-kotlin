package com.petersamokhin.notionsdk

import co.touchlab.stately.concurrency.AtomicReference
import com.petersamokhin.notionsdk.data.NotionApiVersion
import com.petersamokhin.notionsdk.data.mapper.toDomain
import com.petersamokhin.notionsdk.data.model.internal.obj.Block
import com.petersamokhin.notionsdk.data.model.internal.request.QueryDatabaseRequest
import com.petersamokhin.notionsdk.data.model.internal.response.PageObject
import com.petersamokhin.notionsdk.data.model.internal.response.ResultsResponse
import com.petersamokhin.notionsdk.data.model.internal.response.RetrieveDatabaseResponse
import com.petersamokhin.notionsdk.data.model.result.NotionBlock
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseRow
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseSchema
import com.petersamokhin.notionsdk.data.model.result.NotionResults
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*

internal class NotionImpl(
    token: String,
    private val version: NotionApiVersion,
    private var httpClient: HttpClient,
) : Notion {
    private val tokenReference = AtomicReference(token)

    init {
        httpClient = httpClient.withTokenAndVersion()
    }

    override val token: String
        get() = tokenReference.get()

    override fun setToken(token: String) =
        tokenReference.set(token)

    override fun setHttpClient(newHttpClient: HttpClient) {
        httpClient = newHttpClient.withTokenAndVersion()
    }

    override fun close() =
        httpClient.close()

    private fun HttpClient.withTokenAndVersion(): HttpClient =
        config {
            defaultRequest {
                header(HttpHeaders.Authorization, "Bearer $token")
                header(Notion.HEADER_VERSION, version.stringValue)
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json { encodeDefaults = false; ignoreUnknownKeys = true }
                )
            }
        }

    override suspend fun queryDatabase(
        databaseId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionDatabaseRow> =
        httpClient.post<ResultsResponse<PageObject>>("${Notion.API_BASE_URL}/${ENDPOINT_DATABASES}/$databaseId/$PATH_QUERY") {
            contentType(ContentType.Application.Json)
            body = QueryDatabaseRequest(
                startCursor = startCursor,
                pageSize = pageSize,
            )
        }.toDomain()

    override suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionDatabaseRow> =
        httpClient.post<ResultsResponse<PageObject>>("${Notion.API_BASE_URL}/${ENDPOINT_DATABASES}/$databaseId/$PATH_QUERY") {
            body = TextContent(jsonRequestBody, ContentType.Application.Json)
        }.toDomain()

    override suspend fun retrieveDatabase(
        databaseId: String,
    ): NotionDatabaseSchema =
        httpClient.get<RetrieveDatabaseResponse>("${Notion.API_BASE_URL}/${ENDPOINT_DATABASES}/$databaseId")
            .toDomain()

    override suspend fun retrieveBlock(blockId: String): NotionBlock =
        httpClient.get<Block>("${Notion.API_BASE_URL}/${ENDPOINT_BLOCKS}/$blockId")
            .toDomain()

    override suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionBlock> =
        httpClient.get<ResultsResponse<Block>>("${Notion.API_BASE_URL}/${ENDPOINT_BLOCKS}/$blockId/$PATH_CHILDREN") {
            parameter(QUERY_PARAM_START_CURSOR, startCursor)
            parameter(QUERY_PARAM_PAGE_SIZE, pageSize)
        }.toDomain()

    companion object {
        private const val ENDPOINT_BLOCKS = "blocks"
        private const val ENDPOINT_DATABASES = "databases"

        private const val PATH_QUERY = "query"
        private const val PATH_CHILDREN = "children"

        private const val QUERY_PARAM_START_CURSOR = "start_cursor"
        private const val QUERY_PARAM_PAGE_SIZE = "page_size"
    }
}