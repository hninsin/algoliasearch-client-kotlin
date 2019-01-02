package com.algolia.search.saas.client

import com.algolia.search.saas.data.IndexName
import com.algolia.search.saas.data.Scope
import com.algolia.search.saas.data.Task
import com.algolia.search.saas.data.TaskDelete
import com.algolia.search.saas.serialize.*
import io.ktor.client.request.delete
import io.ktor.client.request.post
import kotlinx.serialization.json.JSON
import kotlinx.serialization.json.json
import kotlinx.serialization.list


class ClientIndices(
    val client: Client,
    override val indexName: IndexName
) : EndpointsIndices {

    private suspend fun copyOrMove(
        destination: IndexName,
        key: String,
        scopes: List<Scope>? = null,
        requestOptions: RequestOptions?
    ): Task {
        return client.run {
            write.retry(writeTimeout, indexName.pathIndexes("/operation")) { path ->
                httpClient.post<Task>(path) {
                    setRequestOptions(requestOptions)
                    body = json {
                        KeyOperation to key
                        KeyDestination to destination.raw
                        scopes?.let { KeyScope to JSON.stringify(Scope.list, it) }
                    }.toString()
                }
            }
        }
    }

    override suspend fun copyIndex(
        destination: IndexName,
        scopes: List<Scope>?,
        requestOptions: RequestOptions?
    ): Task {
        return copyOrMove(destination, KeyCopy, scopes, requestOptions)
    }

    override suspend fun moveIndex(destination: IndexName, requestOptions: RequestOptions?): Task {
        return copyOrMove(destination, KeyMove, null, requestOptions)
    }

    override suspend fun deleteIndex(requestOptions: RequestOptions?): TaskDelete {
        return client.run {
            write.retry(writeTimeout, indexName.pathIndexes()) { path ->
                httpClient.delete<TaskDelete>(path) {
                    setRequestOptions(requestOptions)
                }
            }
        }
    }
}