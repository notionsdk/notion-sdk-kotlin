package com.petersamokhin.notionapi.request

import com.petersamokhin.notionapi.model.request.QueryCollectionRequestBody
import com.petersamokhin.notionapi.model.response.NotionResponse
import com.petersamokhin.notionapi.request.base.NotionRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

public class QueryNotionCollectionRequest(
    httpClient: HttpClient
) : NotionRequest<QueryCollectionRequestBody>(httpClient) {

    override suspend fun execute(requestBody: QueryCollectionRequestBody): NotionResponse =
        httpClient.post("$API_BASE_URL/${Endpoint.QUERY_COLLECTION}") {
            headers.appendAll(BASE_HEADERS)
            contentType(ContentType.Application.Json)
            body = requestBody
        }
}