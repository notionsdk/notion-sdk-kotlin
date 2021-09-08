package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.QueryDatabaseResponse
import com.petersamokhin.notionsdk.data.model.internal.response.QueryDatabaseResponseResult
import com.petersamokhin.notionsdk.data.model.result.NotionDatabase

internal fun QueryDatabaseResponse.toDomain(): NotionDatabase =
    NotionDatabase(
        rows = results.asSequence()
            .filterIsInstance<QueryDatabaseResponseResult.Page>()
            .map { it.toDomain() }
            .toList(),
        nextCursor = nextCursor,
        hasMore = hasMore,
    )