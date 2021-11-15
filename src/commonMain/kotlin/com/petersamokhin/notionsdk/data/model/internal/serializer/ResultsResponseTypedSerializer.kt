package com.petersamokhin.notionsdk.data.model.internal.serializer

import com.petersamokhin.notionsdk.data.model.internal.response.ResultsResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = ResultsResponse::class)
internal class ResultsResponseTypedSerializer<T : Any>(
    resultsItemSerializer: KSerializer<T>,
) : KSerializer<ResultsResponse<T>> {
    private val resultsSerializer: KSerializer<List<T>> = ListSerializer(resultsItemSerializer)
    private val nextCursorSerializer: KSerializer<String?> = String.serializer().nullable

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ResultsResponseTypedSerializer") {
        element("results", resultsSerializer.descriptor)
        element("next_cursor", PrimitiveSerialDescriptor("next_cursor", PrimitiveKind.STRING))
        element("has_more", PrimitiveSerialDescriptor("has_more", PrimitiveKind.BOOLEAN))
    }

    override fun deserialize(decoder: Decoder): ResultsResponse<T> {
        val inp = decoder.beginStructure(descriptor)
        var results: List<T>? = null
        var nextCursor: String? = null
        var hasMore: Boolean? = null
        loop@ while (true) {
            when (val i = inp.decodeElementIndex(descriptor)) {
                CompositeDecoder.DECODE_DONE -> break@loop
                0 -> results = inp.decodeSerializableElement(descriptor, i, resultsSerializer)
                1 -> nextCursor = inp.decodeNullableSerializableElement(descriptor, i, nextCursorSerializer)
                2 -> hasMore = inp.decodeBooleanElement(descriptor, i)
                else -> throw SerializationException("Unknown index $i")
            }
        }
        inp.endStructure(descriptor)
        return ResultsResponse(
            results = results ?: throw SerializationException("required field 'results' is null"),
            nextCursor = nextCursor,
            hasMore = hasMore ?: throw SerializationException("required field 'has_more' is null"),
        )
    }

    override fun serialize(encoder: Encoder, value: ResultsResponse<T>) {
        encoder.beginStructure(descriptor).apply {
            encodeNullableSerializableElement(descriptor, 0, resultsSerializer, value.results)
            encodeNullableSerializableElement(descriptor, 1, nextCursorSerializer, value.nextCursor)
            encodeBooleanElement(descriptor, 2, value.hasMore)
            endStructure(descriptor)
        }
    }
}