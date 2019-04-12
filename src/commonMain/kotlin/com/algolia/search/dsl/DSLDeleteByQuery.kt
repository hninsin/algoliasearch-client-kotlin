package com.algolia.search.dsl

import com.algolia.search.dsl.filtering.DSLFacetFilters
import com.algolia.search.dsl.filtering.DSLFilters
import com.algolia.search.dsl.filtering.DSLNumericFilters
import com.algolia.search.dsl.filtering.DSLTagFilters
import com.algolia.search.dsl.geosearch.DSLBoundingBox
import com.algolia.search.dsl.geosearch.DSLPolygon
import com.algolia.search.model.filter.FilterGroupConverter
import com.algolia.search.model.indexing.DeleteByQuery


public fun deleteByQuery(block: DeleteByQuery.() -> Unit): DeleteByQuery {
    return DeleteByQuery().apply(block)
}

public fun DeleteByQuery.filters(block: DSLFilters.() -> Unit) {
    filters = FilterGroupConverter.SQL(DSLFilters(block))
}

public fun DeleteByQuery.facetFilters(block: DSLFacetFilters.() -> Unit) {
    facetFilters = FilterGroupConverter.Legacy.Facet(DSLFacetFilters(block))
}

public fun DeleteByQuery.numericFilters(block: DSLNumericFilters.() -> Unit) {
    numericFilters = FilterGroupConverter.Legacy.Numeric(DSLNumericFilters(block))
}

public fun DeleteByQuery.tagFilters(block: DSLTagFilters.() -> Unit) {
    tagFilters = FilterGroupConverter.Legacy.Tag(DSLTagFilters(block))
}

public fun DeleteByQuery.insideBoundingBox(block: DSLBoundingBox.() -> Unit) {
    insideBoundingBox = DSLBoundingBox(block)
}

public fun DeleteByQuery.insidePolygon(block: DSLPolygon.() -> Unit) {
    insidePolygon = DSLPolygon(block)
}