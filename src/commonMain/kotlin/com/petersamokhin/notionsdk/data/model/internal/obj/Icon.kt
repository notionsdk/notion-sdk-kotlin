package com.petersamokhin.notionsdk.data.model.internal.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Icon(
    val type: String,
    val emoji: String? = null,
    val file: File? = null,
) {
    @Serializable
    data class File(
        val url: String,
        @SerialName("expiry_time")
        val expiryTime: String? = null,
    )
}