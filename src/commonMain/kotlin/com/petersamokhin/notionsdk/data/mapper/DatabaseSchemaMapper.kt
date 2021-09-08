package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.RetrieveDatabaseResponse
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseSchema

internal fun RetrieveDatabaseResponse.toDomain(): NotionDatabaseSchema =
    NotionDatabaseSchema(
        id = id,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        title = fullTitle(),
        schema = properties.mapValues { (_, value) -> value.toDomain() }
    )