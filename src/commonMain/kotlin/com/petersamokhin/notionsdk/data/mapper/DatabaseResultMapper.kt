package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.PageObject
import com.petersamokhin.notionsdk.data.model.internal.response.ResultsResponse
import com.petersamokhin.notionsdk.data.model.result.NotionDatabase

internal fun ResultsResponse<PageObject>.toDomain(): NotionDatabase =
    NotionDatabase(
        rows = results.asSequence()
            .map(PageObject::toDomain)
            .toList(),
        nextCursor = nextCursor,
        hasMore = hasMore,
    )