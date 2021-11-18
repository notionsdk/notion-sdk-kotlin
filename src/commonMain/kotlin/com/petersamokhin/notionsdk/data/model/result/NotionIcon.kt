package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class NotionIcon {
    @Serializable
    @SerialName("emoji")
    public data class Emoji(
        val emoji: String,
    ) : NotionIcon()

    @Serializable
    @SerialName("file")
    public data class File(
        val url: String,
        @SerialName("expiry_time")
        val expiryTime: String? = null,
    ) : NotionIcon()
}