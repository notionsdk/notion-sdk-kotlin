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

        public val email: String,
    ) : NotionUser()

    @Serializable
    @SerialName("bot")
    public data class Bot(
        override val id: String,
        override val name: String? = null,
        @SerialName("avatar_url")
        override val avatarUrl: String? = null,
    ) : NotionUser()
}