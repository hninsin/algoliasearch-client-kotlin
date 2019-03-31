package com.algolia.search.dsl.filtering

import com.algolia.search.dsl.DSLParameters


@DSLParameters
public class DSLFilters(
    private val groups: MutableList<Group<*>> = mutableListOf()
) {

    private fun <T : Filter> put(group: Group<T>) {
        if (group.filters.isNotEmpty()) groups += group
    }

    public fun and(block: DSLGroupFilter.() -> Unit) {
        put(GroupAnd.All(DSLGroupFilter().apply(block).build()))
    }

    public fun orFacet(block: DSLGroupFacet.() -> Unit) {
        put(GroupOr.Facet(DSLGroupFacet().apply(block).build()))
    }

    public fun orNumeric(block: DSLGroupNumeric.() -> Unit) {
        put(GroupOr.Numeric(DSLGroupNumeric().apply(block).build()))
    }

    public fun orTag(block: DSLGroupTag.() -> Unit) {
        put(GroupOr.Tag(DSLGroupTag().apply(block).build()))
    }

    public fun build(): String {
        val (andEntries, orEntries) = groups.partition { it is GroupAnd }
        val ands = andEntries.joinToString(separator = " AND ") { group ->
            val condition = andEntries.size > 1 && group.filters.size > 1
            val prefix = if (condition) "(" else ""
            val postfix = if (condition) ")" else ""
            group.filters.joinToString(prefix = prefix, postfix = postfix, separator = " AND ") { it.build() }
        }
        val begin = if (andEntries.isNotEmpty() && orEntries.isNotEmpty()) " AND " else ""
        val ors = orEntries.joinToString(prefix = begin, separator = " AND ") { group ->
            val condition = group.filters.size > 1 && (orEntries.size > 1 || andEntries.isNotEmpty())
            val prefix = if (condition) "(" else ""
            val postfix = if (condition) ")" else ""
            group.filters.joinToString(prefix = prefix, postfix = postfix, separator = " OR ") { it.build() }
        }
        return ands + ors
    }
}