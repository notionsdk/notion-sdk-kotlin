package com.petersamokhin.notionsdk.data.model.internal.response

import com.petersamokhin.notionsdk.data.model.internal.serializer.ResultsResponseTypedSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = ResultsResponseTypedSerializer::class)
internal data class ResultsResponse<T : Any>(
    val results: List<T>,

    val nextCursor: String? = null,
    val hasMore: Boolean,
)