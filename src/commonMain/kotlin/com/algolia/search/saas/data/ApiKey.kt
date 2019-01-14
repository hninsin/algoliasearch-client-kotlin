package com.algolia.search.saas.data

import com.algolia.search.saas.toAPIKey
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringSerializer


@Serializable(APIKey.Companion::class)
data class APIKey(override val raw: String) : Raw<String> {

    override fun toString(): String {
        return raw
    }

    @Serializer(APIKey::class)
    internal companion object: KSerializer<APIKey> {

        private val serializer = StringSerializer

        override fun serialize(encoder: Encoder, obj: APIKey) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): APIKey {
            return serializer.deserialize(decoder).toAPIKey()
        }
    }
}