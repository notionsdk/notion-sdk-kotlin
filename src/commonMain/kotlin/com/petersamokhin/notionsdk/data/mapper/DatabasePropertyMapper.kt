package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.QueryDatabaseResponseResultPageProperty
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseProperty

internal fun QueryDatabaseResponseResultPageProperty.toDomain(): NotionDatabaseProperty =
    when (this) {
        is QueryDatabaseResponseResultPageProperty.Title -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Text -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Number -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Select -> toDomain()
        is QueryDatabaseResponseResultPageProperty.MultiSelect -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Date -> toDomain()
        is QueryDatabaseResponseResultPageProperty.People -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Files -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Checkbox -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Url -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Email -> toDomain()
        is QueryDatabaseResponseResultPageProperty.PhoneNumber -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Formula -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Relation -> toDomain()
        is QueryDatabaseResponseResultPageProperty.CreatedTime -> toDomain()
        is QueryDatabaseResponseResultPageProperty.LastEditedTime -> toDomain()
        is QueryDatabaseResponseResultPageProperty.CreatedBy -> toDomain()
        is QueryDatabaseResponseResultPageProperty.LastEditedBy -> toDomain()
        is QueryDatabaseResponseResultPageProperty.Rollup -> toDomain()
    }

internal fun QueryDatabaseResponseResultPageProperty.Title.toDomain(): NotionDatabaseProperty.Title =
    NotionDatabaseProperty.Title(
        id = id,
        text = plainText()
    )

internal fun QueryDatabaseResponseResultPageProperty.Text.toDomain(): NotionDatabaseProperty.Text =
    NotionDatabaseProperty.Text(
        id = id,
        text = plainText(),
        parts = richText.map { NotionDatabaseProperty.Text.Part(it.plainText, it.href) }
    )

internal fun QueryDatabaseResponseResultPageProperty.Number.toDomain(): NotionDatabaseProperty.Number =
    NotionDatabaseProperty.Number(
        id = id,
        number = number
    )

internal fun QueryDatabaseResponseResultPageProperty.Select.toDomain(): NotionDatabaseProperty.Select =
    NotionDatabaseProperty.Select(
        id = id,
        selected = select?.toDomain()
    )

internal fun QueryDatabaseResponseResultPageProperty.Select.Value.toDomain(): NotionDatabaseProperty.Select.Option =
    NotionDatabaseProperty.Select.Option(
        id = id,
        name = name
    )

internal fun QueryDatabaseResponseResultPageProperty.MultiSelect.toDomain(): NotionDatabaseProperty.MultiSelect =
    NotionDatabaseProperty.MultiSelect(
        id = id,
        selected = multiSelect.map(QueryDatabaseResponseResultPageProperty.Select.Value::toDomain)
    )

internal fun QueryDatabaseResponseResultPageProperty.Date.toDomain(): NotionDatabaseProperty.Date =
    NotionDatabaseProperty.Date(
        id = id,
        start = date?.start,
        end = date?.end
    )

internal fun QueryDatabaseResponseResultPageProperty.People.toDomain(): NotionDatabaseProperty.People =
    NotionDatabaseProperty.People(
        id = id,
        people = people.map { value -> value.toDomain() }
    )

internal fun QueryDatabaseResponseResultPageProperty.People.Value.toDomain(): NotionDatabaseProperty.People.Person =
    when (this) {
        is QueryDatabaseResponseResultPageProperty.People.Value.Person ->
            NotionDatabaseProperty.People.Person.User(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                email =  person.email
            )
        is QueryDatabaseResponseResultPageProperty.People.Value.Bot ->
            NotionDatabaseProperty.People.Person.Bot(
                id = id,
                name = name,
                avatarUrl = avatarUrl
            )
    }

internal fun QueryDatabaseResponseResultPageProperty.Files.toDomain(): NotionDatabaseProperty.Files =
    NotionDatabaseProperty.Files(
        id = id,
        files = files.map { value ->
            when (value) {
                is QueryDatabaseResponseResultPageProperty.Files.Value.External ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.url,
                        name = null,
                        expiryTime = null,
                        type = NotionDatabaseProperty.Files.Item.Type.External
                    )
                is QueryDatabaseResponseResultPageProperty.Files.Value.File ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.file.url,
                        name = value.name,
                        expiryTime = value.file.expiryTime,
                        type = NotionDatabaseProperty.Files.Item.Type.File
                    )
            }
        }
    )

internal fun QueryDatabaseResponseResultPageProperty.Checkbox.toDomain(): NotionDatabaseProperty.Checkbox =
    NotionDatabaseProperty.Checkbox(
        id = id,
        selected = checkbox
    )

internal fun QueryDatabaseResponseResultPageProperty.Url.toDomain(): NotionDatabaseProperty.Url =
    NotionDatabaseProperty.Url(
        id = id,
        url = url
    )

internal fun QueryDatabaseResponseResultPageProperty.Email.toDomain(): NotionDatabaseProperty.Email =
    NotionDatabaseProperty.Email(
        id = id,
        email = email
    )

internal fun QueryDatabaseResponseResultPageProperty.PhoneNumber.toDomain(): NotionDatabaseProperty.PhoneNumber =
    NotionDatabaseProperty.PhoneNumber(
        id = id,
        phoneNumber = phoneNumber
    )

internal fun QueryDatabaseResponseResultPageProperty.Formula.toDomain(): NotionDatabaseProperty.Formula =
    NotionDatabaseProperty.Formula(
        id = id,
        formula = when (formula) {
            is QueryDatabaseResponseResultPageProperty.Formula.Value.Str -> NotionDatabaseProperty.Formula.Item.Str(formula.string)
            is QueryDatabaseResponseResultPageProperty.Formula.Value.Number -> NotionDatabaseProperty.Formula.Item.Number(formula.number)
            is QueryDatabaseResponseResultPageProperty.Formula.Value.Bool -> NotionDatabaseProperty.Formula.Item.Bool(formula.boolean)
            is QueryDatabaseResponseResultPageProperty.Formula.Value.Date -> NotionDatabaseProperty.Formula.Item.Date(formula.date)
        }
    )

internal fun QueryDatabaseResponseResultPageProperty.Relation.toDomain(): NotionDatabaseProperty.Relation =
    NotionDatabaseProperty.Relation(
        id = id
    )

internal fun QueryDatabaseResponseResultPageProperty.CreatedBy.toDomain(): NotionDatabaseProperty.CreatedBy =
    NotionDatabaseProperty.CreatedBy(
        id = id,
        createdBy = createdBy.toDomain()
    )

internal fun QueryDatabaseResponseResultPageProperty.LastEditedBy.toDomain(): NotionDatabaseProperty.LastEditedBy =
    NotionDatabaseProperty.LastEditedBy(
        id = id,
        lastEditedBy = lastEditedBy.toDomain()
    )

internal fun QueryDatabaseResponseResultPageProperty.CreatedTime.toDomain(): NotionDatabaseProperty.CreatedTime =
    NotionDatabaseProperty.CreatedTime(
        id = id,
        createdTime = createdTime
    )

internal fun QueryDatabaseResponseResultPageProperty.LastEditedTime.toDomain(): NotionDatabaseProperty.LastEditedTime =
    NotionDatabaseProperty.LastEditedTime(
        id = id,
        lastEditedTime = lastEditedTime
    )

internal fun QueryDatabaseResponseResultPageProperty.Rollup.toDomain(): NotionDatabaseProperty.Rollup =
    NotionDatabaseProperty.Rollup(
        id = id,
    )