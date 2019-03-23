package com.algolia.search.endpoint

import com.algolia.search.configuration.CallType
import com.algolia.search.helper.requestOptionsBuilder
import com.algolia.search.model.IndexName
import com.algolia.search.model.ObjectID
import com.algolia.search.model.request.RequestSearchRules
import com.algolia.search.model.response.ResponseRule
import com.algolia.search.model.response.ResponseRules
import com.algolia.search.model.response.revision.RevisionIndex
import com.algolia.search.model.rule.Anchoring
import com.algolia.search.model.rule.Rule
import com.algolia.search.serialize.KeyClearExistingRules
import com.algolia.search.serialize.KeyForwardToReplicas
import com.algolia.search.serialize.RouteRules
import com.algolia.search.serialize.noDefaults
import com.algolia.search.transport.RequestOptions
import com.algolia.search.transport.Transport
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import kotlinx.serialization.list


internal class EndpointRuleImpl(
    private val transport: Transport,
    override val indexName: IndexName
) : EndpointRule {

    override suspend fun saveRule(
        rule: Rule,
        forwardToReplicas: Boolean?,
        requestOptions: RequestOptions?
    ): RevisionIndex {
        val path = indexName.toPath("/$RouteRules/${rule.objectID}")
        val body = Json.noDefaults.stringify(Rule.serializer(), rule)
        val options = requestOptionsBuilder(requestOptions) {
            parameter(KeyForwardToReplicas, forwardToReplicas)
        }

        return transport.request(HttpMethod.Put, CallType.Write, path, options, body)
    }

    override suspend fun getRule(objectID: ObjectID, requestOptions: RequestOptions?): ResponseRule {
        val path = indexName.toPath("/$RouteRules/$objectID")

        return transport.request(HttpMethod.Get, CallType.Read, path, requestOptions)
    }

    override suspend fun deleteRule(
        objectID: ObjectID,
        forwardToReplicas: Boolean?,
        requestOptions: RequestOptions?
    ): RevisionIndex {
        val path = indexName.toPath("/$RouteRules/$objectID")
        val options = requestOptionsBuilder(requestOptions) {
            parameter(KeyForwardToReplicas, forwardToReplicas)
        }
        return transport.request(HttpMethod.Delete, CallType.Write, path, options)
    }

    override suspend fun searchRules(
        query: String?,
        anchoring: Anchoring?,
        context: String?,
        page: Int?,
        hitsPerPage: Int?,
        enabled: Boolean?,
        requestOptions: RequestOptions?
    ): ResponseRules {
        val path = indexName.toPath("/$RouteRules/search")
        val request = RequestSearchRules(query, anchoring, context, page, hitsPerPage, enabled)
        val body = Json.noDefaults.stringify(RequestSearchRules.serializer(), request)

        return transport.request(HttpMethod.Post, CallType.Read, path, requestOptions, body)
    }

    override suspend fun clearRules(forwardToReplicas: Boolean?, requestOptions: RequestOptions?): RevisionIndex {
        val options = requestOptionsBuilder(requestOptions) {
            parameter(KeyForwardToReplicas, forwardToReplicas)
        }

        return transport.request(HttpMethod.Post, CallType.Write, indexName.toPath("/$RouteRules/clear"), options, "")
    }

    override suspend fun saveRules(
        rules: List<Rule>,
        forwardToReplicas: Boolean?,
        clearExistingRules: Boolean?,
        requestOptions: RequestOptions?
    ): RevisionIndex {
        val body = Json.noDefaults.stringify(Rule.serializer().list, rules)
        val options = requestOptionsBuilder(requestOptions) {
            parameter(KeyForwardToReplicas, forwardToReplicas)
            parameter(KeyClearExistingRules, clearExistingRules)
        }

        return transport.request(HttpMethod.Post, CallType.Write, indexName.toPath("/$RouteRules/batch"), options, body)
    }

    override suspend fun replaceAllRules(
        rules: List<Rule>,
        forwardToReplicas: Boolean?,
        requestOptions: RequestOptions?
    ): RevisionIndex {
        return saveRules(rules, forwardToReplicas, true, requestOptions)
    }
}