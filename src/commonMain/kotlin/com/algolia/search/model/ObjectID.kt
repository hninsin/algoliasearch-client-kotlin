package com.algolia.search.model

import com.algolia.search.exception.EmptyStringException
import com.algolia.search.helper.toObjectID
import com.algolia.search.serialize.KeyObjectID
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.internal.StringSerializer

/**
 * Identifies a record uniquely. ObjectIDs are autogenerated server-side if they are not specified at record creation.
 */
@Serializable(ObjectID.Companion::class)
data class ObjectID(@SerialName(KeyObjectID) override val raw: String) : Raw<String> {

    init {
        if (raw.isBlank()) throw EmptyStringException(KeyObjectID)
    }

    override fun toString(): String {
        return raw
    }

    companion object : KSerializer<ObjectID> {

        private val serializer = StringSerializer

        override val descriptor = serializer.descriptor

        override fun serialize(encoder: Encoder, obj: ObjectID) {
            serializer.serialize(encoder, obj.raw)
        }

        override fun deserialize(decoder: Decoder): ObjectID {
            return serializer.deserialize(decoder).toObjectID()
        }
    }
}
