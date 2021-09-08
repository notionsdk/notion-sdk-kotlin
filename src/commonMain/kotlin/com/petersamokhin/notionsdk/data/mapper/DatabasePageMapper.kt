package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.QueryDatabaseResponseResult
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseRow
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseColumn

internal fun QueryDatabaseResponseResult.Page.toDomain(): NotionDatabaseRow =
    NotionDatabaseRow(
        columns = properties.mapValues { (key, value) -> NotionDatabaseColumn(key, value.toDomain()) }
    )