package com.algolia.search.dsl.rule

import com.algolia.search.dsl.DSL
import com.algolia.search.dsl.DSLParameters
import com.algolia.search.model.Attribute
import com.algolia.search.model.ObjectID
import com.algolia.search.model.rule.Anchoring
import com.algolia.search.model.rule.AutomaticFacetFilters
import com.algolia.search.model.rule.Condition
import com.algolia.search.model.rule.Consequence
import com.algolia.search.model.rule.Edit
import com.algolia.search.model.rule.Pattern
import com.algolia.search.model.rule.Promotion
import com.algolia.search.model.rule.Rule
import com.algolia.search.model.rule.TimeRange
import com.algolia.search.model.search.Query
import kotlinx.serialization.json.JsonObject

/**
 * DSL for building a [List] of [Rule].
 */
@Suppress("PropertyName", "FunctionName")
@DSLParameters
class DSLRules(
    private val rules: MutableList<Rule> = mutableListOf()
) {

    val Is = Anchoring.Is
    val StartsWith = Anchoring.StartsWith
    val EndsWith = Anchoring.EndsWith
    val Contains = Anchoring.Contains

    /**
     * Convenience method.
     */
    fun Facet(attribute: String): Pattern.Facet {
        return Facet(Attribute(attribute))
    }

    /**
     * Create a [Pattern.Facet] with an [attribute].
     */
    fun Facet(attribute: Attribute): Pattern.Facet {
        return Pattern.Facet(attribute)
    }

    /**
     * Create a [Pattern.Literal] with a [value].
     */
    fun Literal(value: String): Pattern.Literal {
        return Pattern.Literal(value)
    }

    /**
     * Create a [Condition] with [anchoring], [pattern] and an optional [context].
     */
    fun condition(anchoring: Anchoring, pattern: Pattern, context: String? = null): Condition {
        return Condition(anchoring, pattern, context)
    }

    /**
     * Create a [List] of [AutomaticFacetFilters] with [DSLAutomaticFacetFilters].
     */
    fun automaticFacetFilters(block: DSLAutomaticFacetFilters.() -> Unit): List<AutomaticFacetFilters> {
        return DSLAutomaticFacetFilters(block)
    }

    /**
     * Create a [List] of [Edit] with [DSLEdits].
     */
    fun edits(block: DSLEdits.() -> Unit): List<Edit> {
        return DSLEdits(block)
    }

    /**
     * Create a [List] of [Promotion] with [DSLPromotions].
     */
    fun promotions(block: DSLPromotions.() -> Unit): List<Promotion> {
        return DSLPromotions(block)
    }

    /**
     * Create a [List] of [TimeRange] with [DSLTimeRanges].
     */
    fun timeRanges(block: DSLTimeRanges.() -> Unit): List<TimeRange> {
        return DSLTimeRanges(block)
    }

    /**
     * Create a [Consequence] with [automaticFacetFilters], [automaticOptionalFacetFilters], [edits], [promote],
     * [userData], [hide], [query]
     */
    fun consequence(
        automaticFacetFilters: List<AutomaticFacetFilters>? = null,
        automaticOptionalFacetFilters: List<AutomaticFacetFilters>? = null,
        edits: List<Edit>? = null,
        promote: List<Promotion>? = null,
        filterPromotes: Boolean? = null,
        userData: JsonObject? = null,
        hide: List<ObjectID>? = null,
        query: Query? = null
    ): Consequence {
        return Consequence(
            automaticFacetFilters = automaticFacetFilters,
            automaticOptionalFacetFilters = automaticOptionalFacetFilters,
            edits = edits,
            promote = promote,
            userData = userData,
            hide = hide,
            query = query,
            filterPromotes = filterPromotes
        )
    }

    /**
     * Add [this] to [rules].
     */
    operator fun Rule.unaryPlus() {
        rules += this
    }

    /**
     * Convenience method.
     */
    fun rule(
        objectID: String,
        condition: Condition,
        consequence: Consequence,
        enabled: Boolean? = null,
        validity: List<TimeRange>? = null,
        description: String? = null
    ) {
        +Rule(ObjectID(objectID), condition, consequence, enabled, validity, description)
    }

    /**
     * Create a [Rule] with [objectID], [condition], [consequence], [enabled], [validity] and [description].
     */
    fun rule(
        objectID: ObjectID,
        condition: Condition,
        consequence: Consequence,
        enabled: Boolean? = null,
        validity: List<TimeRange>? = null,
        description: String? = null
    ) {
        +Rule(objectID, condition, consequence, enabled, validity, description)
    }

    companion object : DSL<DSLRules, List<Rule>> {

        override operator fun invoke(block: DSLRules.() -> Unit): List<Rule> {
            return DSLRules().apply(block).rules
        }
    }
}
