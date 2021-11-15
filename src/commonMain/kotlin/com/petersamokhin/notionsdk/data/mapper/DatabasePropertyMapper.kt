package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.response.PageProperty
import com.petersamokhin.notionsdk.data.model.result.NotionDatabaseProperty

internal fun PageProperty.toDomain(): NotionDatabaseProperty =
    when (this) {
        is PageProperty.Title -> toDomain()
        is PageProperty.Text -> toDomain()
        is PageProperty.Number -> toDomain()
        is PageProperty.Select -> toDomain()
        is PageProperty.MultiSelect -> toDomain()
        is PageProperty.Date -> toDomain()
        is PageProperty.People -> toDomain()
        is PageProperty.Files -> toDomain()
        is PageProperty.Checkbox -> toDomain()
        is PageProperty.Url -> toDomain()
        is PageProperty.Email -> toDomain()
        is PageProperty.PhoneNumber -> toDomain()
        is PageProperty.Formula -> toDomain()
        is PageProperty.Relation -> toDomain()
        is PageProperty.CreatedTime -> toDomain()
        is PageProperty.LastEditedTime -> toDomain()
        is PageProperty.CreatedBy -> toDomain()
        is PageProperty.LastEditedBy -> toDomain()
        is PageProperty.Rollup -> toDomain()
    }

internal fun PageProperty.Title.toDomain(): NotionDatabaseProperty.Title =
    NotionDatabaseProperty.Title(
        id = id,
        text = plainText()
    )

internal fun PageProperty.Text.toDomain(): NotionDatabaseProperty.Text =
    NotionDatabaseProperty.Text(
        id = id,
        text = plainText(),
        parts = richText.map { NotionDatabaseProperty.Text.Part(it.plainText, it.href) }
    )

internal fun PageProperty.Number.toDomain(): NotionDatabaseProperty.Number =
    NotionDatabaseProperty.Number(
        id = id,
        number = number
    )

internal fun PageProperty.Select.toDomain(): NotionDatabaseProperty.Select =
    NotionDatabaseProperty.Select(
        id = id,
        selected = select?.toDomain()
    )

internal fun PageProperty.Select.Value.toDomain(): NotionDatabaseProperty.Select.Option =
    NotionDatabaseProperty.Select.Option(
        id = id,
        name = name
    )

internal fun PageProperty.MultiSelect.toDomain(): NotionDatabaseProperty.MultiSelect =
    NotionDatabaseProperty.MultiSelect(
        id = id,
        selected = multiSelect.map(PageProperty.Select.Value::toDomain)
    )

internal fun PageProperty.Date.toDomain(): NotionDatabaseProperty.Date =
    NotionDatabaseProperty.Date(
        id = id,
        start = date?.start,
        end = date?.end
    )

internal fun PageProperty.People.toDomain(): NotionDatabaseProperty.People =
    NotionDatabaseProperty.People(
        id = id,
        people = people.map { value -> value.toDomain() }
    )

internal fun PageProperty.People.Value.toDomain(): NotionDatabaseProperty.People.Person =
    when (this) {
        is PageProperty.People.Value.Person ->
            NotionDatabaseProperty.People.Person.User(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                email =  person.email
            )
        is PageProperty.People.Value.Bot ->
            NotionDatabaseProperty.People.Person.Bot(
                id = id,
                name = name,
                avatarUrl = avatarUrl
            )
    }

internal fun PageProperty.Files.toDomain(): NotionDatabaseProperty.Files =
    NotionDatabaseProperty.Files(
        id = id,
        files = files.map { value ->
            when (value) {
                is PageProperty.Files.Value.External ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.url,
                        name = null,
                        expiryTime = null,
                        type = NotionDatabaseProperty.Files.Item.Type.External
                    )
                is PageProperty.Files.Value.File ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.file.url,
                        name = value.name,
                        expiryTime = value.file.expiryTime,
                        type = NotionDatabaseProperty.Files.Item.Type.File
                    )
            }
        }
    )

internal fun PageProperty.Checkbox.toDomain(): NotionDatabaseProperty.Checkbox =
    NotionDatabaseProperty.Checkbox(
        id = id,
        selected = checkbox
    )

internal fun PageProperty.Url.toDomain(): NotionDatabaseProperty.Url =
    NotionDatabaseProperty.Url(
        id = id,
        url = url
    )

internal fun PageProperty.Email.toDomain(): NotionDatabaseProperty.Email =
    NotionDatabaseProperty.Email(
        id = id,
        email = email
    )

internal fun PageProperty.PhoneNumber.toDomain(): NotionDatabaseProperty.PhoneNumber =
    NotionDatabaseProperty.PhoneNumber(
        id = id,
        phoneNumber = phoneNumber
    )

internal fun PageProperty.Formula.toDomain(): NotionDatabaseProperty.Formula =
    NotionDatabaseProperty.Formula(
        id = id,
        formula = when (formula) {
            is PageProperty.Formula.Value.Str -> NotionDatabaseProperty.Formula.Item.Str(formula.string)
            is PageProperty.Formula.Value.Number -> NotionDatabaseProperty.Formula.Item.Number(formula.number)
            is PageProperty.Formula.Value.Bool -> NotionDatabaseProperty.Formula.Item.Bool(formula.boolean)
            is PageProperty.Formula.Value.Date -> NotionDatabaseProperty.Formula.Item.Date(formula.date)
        }
    )

internal fun PageProperty.Relation.toDomain(): NotionDatabaseProperty.Relation =
    NotionDatabaseProperty.Relation(
        id = id
    )

internal fun PageProperty.CreatedBy.toDomain(): NotionDatabaseProperty.CreatedBy =
    NotionDatabaseProperty.CreatedBy(
        id = id,
        createdBy = createdBy.toDomain()
    )

internal fun PageProperty.LastEditedBy.toDomain(): NotionDatabaseProperty.LastEditedBy =
    NotionDatabaseProperty.LastEditedBy(
        id = id,
        lastEditedBy = lastEditedBy.toDomain()
    )

internal fun PageProperty.CreatedTime.toDomain(): NotionDatabaseProperty.CreatedTime =
    NotionDatabaseProperty.CreatedTime(
        id = id,
        createdTime = createdTime
    )

internal fun PageProperty.LastEditedTime.toDomain(): NotionDatabaseProperty.LastEditedTime =
    NotionDatabaseProperty.LastEditedTime(
        id = id,
        lastEditedTime = lastEditedTime
    )

internal fun PageProperty.Rollup.toDomain(): NotionDatabaseProperty.Rollup =
    NotionDatabaseProperty.Rollup(
        id = id,
    )