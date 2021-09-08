package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.RetrieveDatabaseSchemaProperty
import com.petersamokhin.notionsdk.data.model.result.NotionDatabasePropertySchema

internal fun RetrieveDatabaseSchemaProperty.toDomain(): NotionDatabasePropertySchema =
    when (this) {
        is RetrieveDatabaseSchemaProperty.Title -> toDomain()
        is RetrieveDatabaseSchemaProperty.Text -> toDomain()
        is RetrieveDatabaseSchemaProperty.Number -> toDomain()
        is RetrieveDatabaseSchemaProperty.Select -> toDomain()
        is RetrieveDatabaseSchemaProperty.MultiSelect -> toDomain()
        is RetrieveDatabaseSchemaProperty.Formula -> toDomain()
        is RetrieveDatabaseSchemaProperty.Date -> toDomain()
        is RetrieveDatabaseSchemaProperty.People -> toDomain()
        is RetrieveDatabaseSchemaProperty.Files -> toDomain()
        is RetrieveDatabaseSchemaProperty.Checkbox -> toDomain()
        is RetrieveDatabaseSchemaProperty.Url -> toDomain()
        is RetrieveDatabaseSchemaProperty.Email -> toDomain()
        is RetrieveDatabaseSchemaProperty.PhoneNumber -> toDomain()
        is RetrieveDatabaseSchemaProperty.CreatedTime -> toDomain()
        is RetrieveDatabaseSchemaProperty.CreatedBy -> toDomain()
        is RetrieveDatabaseSchemaProperty.LastEditedTime -> toDomain()
        is RetrieveDatabaseSchemaProperty.LastEditedBy -> toDomain()
        is RetrieveDatabaseSchemaProperty.Rollup -> toDomain()
    }

internal fun RetrieveDatabaseSchemaProperty.Title.toDomain(): NotionDatabasePropertySchema.Title =
    NotionDatabasePropertySchema.Title(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Text.toDomain(): NotionDatabasePropertySchema.Text =
    NotionDatabasePropertySchema.Text(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Number.toDomain(): NotionDatabasePropertySchema.Number =
    NotionDatabasePropertySchema.Number(id = id, name = name, format = number.format.toDomain())

internal fun RetrieveDatabaseSchemaProperty.Select.toDomain(): NotionDatabasePropertySchema.Select =
    NotionDatabasePropertySchema.Select(id = id, name = name, options = select.options.map { it.toDomain() })

internal fun RetrieveDatabaseSchemaProperty.Select.Option.toDomain(): NotionDatabasePropertySchema.Select.Option =
    NotionDatabasePropertySchema.Select.Option(id, name)

internal fun RetrieveDatabaseSchemaProperty.MultiSelect.toDomain(): NotionDatabasePropertySchema.MultiSelect =
    NotionDatabasePropertySchema.MultiSelect(id = id, name = name, options = multiSelect.options.map { it.toDomain() })

internal fun RetrieveDatabaseSchemaProperty.Formula.toDomain(): NotionDatabasePropertySchema.Formula =
    NotionDatabasePropertySchema.Formula(id = id, name = name, expression = formula.expression)

internal fun RetrieveDatabaseSchemaProperty.Date.toDomain(): NotionDatabasePropertySchema.Date =
    NotionDatabasePropertySchema.Date(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.People.toDomain(): NotionDatabasePropertySchema.People =
    NotionDatabasePropertySchema.People(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Files.toDomain(): NotionDatabasePropertySchema.Files =
    NotionDatabasePropertySchema.Files(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Checkbox.toDomain(): NotionDatabasePropertySchema.Checkbox =
    NotionDatabasePropertySchema.Checkbox(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Url.toDomain(): NotionDatabasePropertySchema.Url =
    NotionDatabasePropertySchema.Url(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Email.toDomain(): NotionDatabasePropertySchema.Email =
    NotionDatabasePropertySchema.Email(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.PhoneNumber.toDomain(): NotionDatabasePropertySchema.PhoneNumber =
    NotionDatabasePropertySchema.PhoneNumber(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.CreatedTime.toDomain(): NotionDatabasePropertySchema.CreatedTime =
    NotionDatabasePropertySchema.CreatedTime(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.CreatedBy.toDomain(): NotionDatabasePropertySchema.CreatedBy =
    NotionDatabasePropertySchema.CreatedBy(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.LastEditedTime.toDomain(): NotionDatabasePropertySchema.LastEditedTime =
    NotionDatabasePropertySchema.LastEditedTime(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.LastEditedBy.toDomain(): NotionDatabasePropertySchema.LastEditedBy =
    NotionDatabasePropertySchema.LastEditedBy(id = id, name = name)

internal fun RetrieveDatabaseSchemaProperty.Rollup.toDomain(): NotionDatabasePropertySchema.Rollup =
    NotionDatabasePropertySchema.Rollup(id = id, name = name)