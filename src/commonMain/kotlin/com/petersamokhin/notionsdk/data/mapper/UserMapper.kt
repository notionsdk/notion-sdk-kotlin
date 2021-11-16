package com.petersamokhin.notionsdk.data.mapper

import com.petersamokhin.notionsdk.data.model.internal.obj.User
import com.petersamokhin.notionsdk.data.model.result.NotionUser

internal fun User.toDomain(): NotionUser =
    when (this) {
        is User.Person -> NotionUser.Person(
            id = id,
            name = name,
            avatarUrl = avatarUrl,
            email = person.email,
        )
        is User.Bot -> NotionUser.Bot(
            id = id,
            name = name,
            avatarUrl = avatarUrl,
        )
    }