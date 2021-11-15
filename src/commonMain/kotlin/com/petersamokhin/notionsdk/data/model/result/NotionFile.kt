package com.petersamokhin.notionsdk.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class NotionFile {
    public abstract val url: String

    @Serializable
    @SerialName("file")
    public data class File(
        override val url: String,
        @SerialName("expiry_time")
        public val expiryTime: String,
    ) : NotionFile()

    @Serializable
    @SerialName("external")
    public data class External(
        override val url: String,
    ) : NotionFile()
}
