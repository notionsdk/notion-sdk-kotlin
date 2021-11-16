package com.petersamokhin.notionsdk.data.model.internal.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class User {
    abstract val id: String
    abstract val name: String?

    @SerialName("avatar_url")
    abstract val avatarUrl: String?

    @Serializable
    @SerialName("person")
    data class Person(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        val person: Value,
    ) : User() {

        @Serializable
        data class Value(
            val email: String,
        )
    }

    @Serializable
    @SerialName("bot")
    data class Bot(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        val bot: Bot? = null,
    ) : User() {

        @Serializable
        object Bot
    }
}