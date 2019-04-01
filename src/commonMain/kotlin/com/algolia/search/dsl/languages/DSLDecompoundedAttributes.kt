package com.algolia.search.dsl.languages

import com.algolia.search.dsl.DSLParameters
import com.algolia.search.dsl.attributes.DSLAttributes
import com.algolia.search.model.search.QueryLanguage
import com.algolia.search.model.settings.DecompoundedAttributes


@DSLParameters
public class DSLDecompoundedAttributes(
    private val decompoundedAttributes: MutableList<DecompoundedAttributes> = mutableListOf()
) {

    private infix fun QueryLanguage.decompounded(block: DSLAttributes.() -> Unit): DecompoundedAttributes {
        return DecompoundedAttributes(this, DSLAttributes().apply(block).build())
    }

    public infix fun german(block: DSLAttributes.() -> Unit): DecompoundedAttributes {
        return QueryLanguage.German.decompounded(block)
    }

    public infix fun finnish(block: DSLAttributes.() -> Unit): DecompoundedAttributes {
        return QueryLanguage.Finnish.decompounded(block)
    }

    public infix fun dutch(block: DSLAttributes.() -> Unit): DecompoundedAttributes {
        return QueryLanguage.Dutch.decompounded(block)
    }

    public operator fun DecompoundedAttributes.unaryPlus() {
        decompoundedAttributes += this
    }

    public fun build(): List<DecompoundedAttributes> {
        return decompoundedAttributes.toList()
    }
}