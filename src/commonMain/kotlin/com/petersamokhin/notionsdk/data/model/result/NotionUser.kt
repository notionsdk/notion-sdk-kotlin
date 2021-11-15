package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class NotionUser {
    public abstract val id: String
    public abstract val name: String?

    @SerialName("avatar_url")
    public abstract val avatarUrl: String?

    @Serializable
    @SerialName("person")
    public data class Person(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        public val person: Value,
    ) : NotionUser() {

        @Serializable
        public data class Value(
            val email: String,
        )
    }

    @Serializable
    @SerialName("bot")
    public data class Bot(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
        public val bot: Bot? = null,
    ) : NotionUser() {

        @Serializable
        public object Bot
    }
}