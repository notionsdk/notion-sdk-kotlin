package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.obj.Block
import com.petersamokhin.notionsdk.data.model.internal.response.PageObject
import com.petersamokhin.notionsdk.data.model.internal.response.ResultsResponse
import com.petersamokhin.notionsdk.data.model.result.NotionBlock
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseRow
import com.petersamokhin.notionsdk.data.model.result.NotionResults

@Suppress("UNCHECKED_CAST")
internal inline fun <reified T : Any, reified R : Any> ResultsResponse<T>.toDomain(): NotionResults<R> =
    NotionResults(
        results = (
            when (T::class) {
                PageObject::class -> when (R::class) {
                    NotionDatabaseRow::class -> results.filterIsInstance<PageObject>().map(PageObject::toDomain)
                    else -> null
                }
                Block::class -> when (R::class) {
                    NotionBlock::class -> results.filterIsInstance<Block>().map(Block::toDomain)
                    else -> null
                }
                else -> error("${T::class} results response domain mapping is not supported")
            } ?: error("${T::class} -> ${R::class} results response domain mapping is not supported")
        ) as List<R>,
        nextCursor = nextCursor,
        hasMore = hasMore,
    )