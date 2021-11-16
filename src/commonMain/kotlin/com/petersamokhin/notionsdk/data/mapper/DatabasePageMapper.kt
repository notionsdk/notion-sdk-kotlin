package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.PageObject
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseColumn
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseRow

internal fun PageObject.toDomain(): NotionDatabaseRow =
    NotionDatabaseRow(
        id = id,
        columns = properties.mapValues { (key, value) -> NotionDatabaseColumn(key, value.toDomain()) }
    )