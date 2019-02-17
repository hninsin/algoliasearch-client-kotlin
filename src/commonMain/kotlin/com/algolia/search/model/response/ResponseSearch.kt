package com.algolia.search.model.response

import com.algolia.search.model.Attribute
import com.algolia.search.model.IndexName
import com.algolia.search.model.search.Cursor
import com.algolia.search.model.search.Facet
import com.algolia.search.model.search.FacetStats
import com.algolia.search.serialize.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


@Serializable
data class ResponseSearch(
    @Optional @SerialName(KeyHits) val hits: List<Hit>? = null,
    @Optional @SerialName(KeyNbHits) val nbHits: Int? = null,
    @Optional @SerialName(KeyPage) val page: Int? = null,
    @Optional @SerialName(KeyHitsPerPage) val hitsPerPage: Int? = null,
    @Optional @SerialName(KeyUserData) val userData: List<JsonObject>? = null,
    @Optional @SerialName(KeyNbPages) val nbPages: Int? = null,
    @Optional @SerialName(KeyProcessingTimeMS) val processingTimeMS: Long? = null,
    @Optional @SerialName(KeyExhaustiveNbHits) val exhaustiveNbHits: Boolean? = null,
    @Optional @SerialName(KeyExhaustiveFacetsCount) val exhaustiveFacetsCount: Boolean? = null,
    @Optional @SerialName(KeyQuery) val query: String? = null,
    @Optional @SerialName(KeyQueryAfterRemoval) val queryAfterRemoval: String? = null,
    @Optional @SerialName(KeyParams) val params: String? = null,
    @Optional @SerialName(KeyMessage) val message: String? = null,
    @Optional @SerialName(KeyAroundLatLng) val aroundLatLng: String? = null,
    @Optional @SerialName(KeyAutomaticRadius) val automaticRadius: Float? = null,
    @Optional @SerialName(KeyServerUsed) val serverUsed: String? = null,
    @Optional @SerialName(KeyIndexUsed) val indexUsed: String? = null,
    @Optional @SerialName(KeyAbTestVariantID) val abTestVariantID: Int? = null,
    @Optional @SerialName(KeyParsedQuery) val parsedQuery: String? = null,
    @Optional @SerialName(KeyFacets) @Serializable(KSerializerFacets::class) val facets: Map<Attribute, List<Facet>>? = null,
    @Optional @SerialName(KeyFacets_Stats) val facetStats: Map<Attribute, FacetStats>? = null,
    @Optional @SerialName(KeyCursor) val cursor: Cursor? = null,
    @Optional @SerialName(KeyIndex) val indexName: IndexName? = null,
    @Optional @SerialName(KeyProcessed) val processed: Boolean? = null,
    @Optional @SerialName(KeyQueryID) val queryID: String? = null
) {

    @Serializable(Hit.Companion::class)
    data class Hit(
        val json: JsonObject
    ) {

        val highlights = json.getObjectOrNull(Key_HighlightResult)?.toHighlights()
        val snippets = json.getObjectOrNull(Key_SnippetResult)?.toSnippets()
        val rankingInfo = json.getObjectOrNull(Key_RankingInfo)?.toRankingInfo()
        val distinctSequentialID = json.getPrimitiveOrNull(Key_DistinctSeqID)?.int

        fun <T> get(serializer: KSerializer<T>, attribute: Attribute): T {
            return Json.plain.fromJson(serializer, json[attribute.raw])
        }

        fun <T> parse(serializer: KSerializer<T>): T {
            return Json.nonstrict.fromJson(serializer, json)
        }

        @Serializer(Hit::class)
        companion object : KSerializer<Hit> {

            override fun deserialize(decoder: Decoder): Hit {
                return Hit(decoder.asJsonInput().jsonObject)
            }

            override fun serialize(encoder: Encoder, obj: Hit) {
                encoder.asJsonOutput().encodeJson(obj.json)
            }
        }
    }
}