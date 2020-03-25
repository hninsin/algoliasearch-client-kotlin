package com.algolia.search.model.rule

import com.algolia.search.model.Raw
import com.algolia.search.model.search.Query
import com.algolia.search.serialize.KeyContains
import com.algolia.search.serialize.KeyEndsWith
import com.algolia.search.serialize.KeyIs
import com.algolia.search.serialize.KeyStartsWith
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.StringSerializer

@Serializable(Anchoring.Companion::class)
sealed class Anchoring(override val raw: String) : Raw<String> {

    /**
     * The [Pattern] matches the [Query.query].
     */
    object Is : Anchoring(KeyIs)

    /**
     * The [Pattern] matches the beginning of the [Query.query].
     */
    object StartsWith : Anchoring(KeyStartsWith)

    /**
     * The [Pattern] matches the beginning of the [Query.query].
     */
    object EndsWith : Anchoring(KeyEndsWith)

    /**
     * The [Pattern] is contained by the [Query.query].
     */
    object Contains : Anchoring(KeyContains)

    data class Other(override val raw: String) : Anchoring(raw)

    companion object : KSerializer<Anchoring> {

        private val serializer = StringSerializer

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: Anchoring) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): Anchoring {
            return when (val string = serializer.deserialize(decoder)) {
                KeyIs -> Is
                KeyEndsWith -> EndsWith
                KeyStartsWith -> StartsWith
                KeyContains -> Contains
                else -> Other(string)
            }
        }
    }
}
