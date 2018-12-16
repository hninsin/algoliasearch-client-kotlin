package client

import client.host.Hosts
import client.query.Query
import client.response.FacetHits
import client.response.Hits
import client.response.ListIndexes
import client.serialize.toMap
import io.ktor.client.HttpClient
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post


class Client(
    val applicationId: ApplicationId,
    val apiKey: ApiKey,
    val readTimeout: Long = 30000,
    val searchTimeout: Long = 5000
) {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().also {
                it.register<FacetHits>()
                it.register<FacetHits.Item>()
                it.register<ListIndexes>()
                it.register<ListIndexes.Item>()
                it.register<Hits>()
            }
        }
        install(DefaultRequest) {
            setApplicationId(applicationId)
            setApiKey(apiKey)
        }
    }

    private val hosts = Hosts(applicationId)

    private val RequestOptions?.computedReadTimeout get() = this?.readTimeout ?: readTimeout
    private val RequestOptions?.computedSearchTimeout get() = this?.searchTimeout ?: searchTimeout

    private fun Index.pathIndexes(suffix: String = ""): String {
        return "/1/indexes/${encode().string}" + suffix
    }

    suspend fun getListIndexes(requestOptions: RequestOptions? = null): ListIndexes {
        return hosts.retryLogic(requestOptions.computedReadTimeout, "/1/indexes") { path ->
            httpClient.get<ListIndexes>(path) {
                setRequestOptions(requestOptions)
            }
        }
    }

    internal suspend fun search(index: Index, requestOptions: RequestOptions? = null): Hits {
        return hosts.retryLogic(requestOptions.computedSearchTimeout, index.pathIndexes()) { path ->
            httpClient.get<Hits>(path) {
                setRequestOptions(requestOptions)
            }
        }
    }

    suspend fun search(index: Index, query: Query? = null, requestOptions: RequestOptions? = null): Hits {
        return hosts.retryLogic(requestOptions.computedSearchTimeout, index.pathIndexes("/query")) { path ->
            httpClient.post<Hits>(path) {
                setRequestOptions(requestOptions)
                setQuery(query)
            }
        }
    }

    suspend fun browse(index: Index, query: Query? = null, requestOptions: RequestOptions? = null): Hits {
        return hosts.retryLogic(requestOptions.computedSearchTimeout, index.pathIndexes("/browse")) { path ->
            httpClient.post<Hits>(path) {
                setRequestOptions(requestOptions)
                setQuery(query)
            }
        }
    }

    suspend fun browse(index: Index, cursor: String, requestOptions: RequestOptions? = null): Hits {
        return hosts.retryLogic(requestOptions.computedSearchTimeout, index.pathIndexes("/browse")) { path ->
            httpClient.get<Hits>(path) {
                setRequestOptions(requestOptions)
                parameter("cursor", cursor)
            }
        }
    }

    suspend fun searchForFacetValue(
        index: Index,
        facetName: String,
        query: Query? = null,
        facetQuery: String? = null,
        maxFacetHits: Int? = null,
        requestOptions: RequestOptions? = null
    ): FacetHits {
        return hosts.retryLogic(
            requestOptions.computedSearchTimeout,
            index.pathIndexes("/facets/$facetName/query")
        ) { path ->
            httpClient.post<FacetHits>(path) {
                setRequestOptions(requestOptions)
                val map = query?.toMap() ?: mutableMapOf()

                maxFacetHits?.let { map["maxFacetHits"] = it }
                facetQuery?.let { map["facetQuery"] = it }
                setBody(map)
            }
        }
    }
}