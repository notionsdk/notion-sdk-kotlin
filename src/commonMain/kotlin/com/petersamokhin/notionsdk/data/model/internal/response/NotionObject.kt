package com.petersamokhin.notionsdk.data.model.internal.response

import com.petersamokhin.notionsdk.data.model.internal.obj.Icon
import kotlinx.serialization.Serializable

@Serializable
internal data class PageObject(
    val id: String,
    val url: String,
    val properties: Map<String, PageProperty>,
    val icon: Icon? = null,
)