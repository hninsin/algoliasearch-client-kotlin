package com.algolia.search.model.settings

import com.algolia.search.model.Attribute
import com.algolia.search.model.IndexName
import com.algolia.search.model.SearchableAttribute
import com.algolia.search.model.enums.*
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable


@Serializable
data class Settings(
    @Optional var searchableAttributes: List<SearchableAttribute>? = null,
    @Optional var attributesForFaceting: List<Attribute>? = null,
    @Optional var unretrievableAttributes: List<Attribute>? = null,
    @Optional var attributesToRetrieve: List<Attribute>? = null,
    @Optional var ranking: List<Ranking>? = null,
    @Optional var customRanking: List<CustomRanking>? = null,
    @Optional var replicas: List<IndexName>? = null,
    @Optional var maxValuesPerFacet: Int? = null,
    @Optional var sortFacetValuesBy: SortFacetValuesBy? = null,
    @Optional var attributesToHighlight: List<Attribute>? = null,
    @Optional var attributesToSnippet: List<Snippet>? = null,
    @Optional var highlightPreTag: String? = null,
    @Optional var highlightPostTag: String? = null,
    @Optional var snippetEllipsisText: String? = null,
    @Optional var restrictHighlightAndSnippetArrays: Boolean? = null,
    @Optional var hitsPerPage: Int? = null,
    @Optional var paginationLimitedTo: Int? = null,
    @Optional var minWordSizefor1Typo: Int? = null,
    @Optional var minWordSizefor2Typos: Int? = null,
    @Optional var typoTolerance: TypoTolerance? = null,
    @Optional var allowTyposOnNumericTokens: Boolean? = null,
    @Optional var disableTypoToleranceOnAttributes: List<Attribute>? = null,
    @Optional var disableTypoToleranceOnWords: List<String>? = null,
    @Optional var separatorsToIndex: String? = null,
    @Optional var ignorePlurals: BooleanOrQueryLanguages? = null,
    @Optional var removeStopWords: BooleanOrQueryLanguages? = null,
    @Optional var camelCaseAttributes: List<Attribute>? = null,
    @Optional var decompoundedAttributes: List<DecompoundedAttributes>? = null,
    @Optional var keepDiacriticsOnCharacters: String? = null,
    @Optional var queryLanguages: List<QueryLanguage>? = null,
    @Optional var enableRules: Boolean? = null,
    @Optional var queryType: QueryType? = null,
    @Optional var removeWordsIfNoResults: RemoveWordIfNoResults? = null,
    @Optional var advancedSyntax: Boolean? = null,
    @Optional var optionalWords: List<String>? = null,
    @Optional var disablePrefixOnAttributes: List<Attribute>? = null,
    @Optional var disableExactOnAttributes: List<Attribute>? = null,
    @Optional var exactOnSingleWordQuery: ExactOnSingleWordQuery? = null,
    @Optional var alternativesAsExact: List<AlternativesAsExact>? = null,
    @Optional var numericAttributesForFiltering: List<NumericAttributeFilter>? = null,
    @Optional var allowCompressionOfIntegerArray: Boolean? = null,
    @Optional var attributeForDistinct: Attribute? = null,
    @Optional var distinct: Int? = null,
    @Optional var replaceSynonymsInHighlight: Boolean? = null,
    @Optional var minProximity: Int? = null,
    @Optional var responseFields: List<ResponseFields>? = null,
    @Optional var maxFacetHits: Int? = null
)