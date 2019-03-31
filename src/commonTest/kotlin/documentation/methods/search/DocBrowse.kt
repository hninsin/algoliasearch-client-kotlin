package documentation.methods.search

import com.algolia.search.helper.requestOptionsBuilder
import com.algolia.search.model.search.Query
import io.ktor.client.features.ResponseException
import runBlocking
import shouldFailWith
import documentation.index
import kotlin.test.Test


internal class DocBrowse {

//    suspend fun Index.browseObjects(
//         #{query}: __Query__ = Query(),
//         #{requestOptions}: __RequestOptions?__ = null,
//         block: __suspend (ResponseSearch) -> Unit)__
//    )

    @Test
    fun example() {
        runBlocking {
            val query = Query()

            index.browseObjects(query) { responseSearch ->
                println(responseSearch.hits)
            }
        }
    }

    @Test
    fun extraHttpHeaders() {
        shouldFailWith<ResponseException> {
            runBlocking {
                val query = Query(query = "")
                val requestOptions = requestOptionsBuilder {
                    header("X-Algolia-User-ID", "user123")
                }

                index.browseObjects(query, requestOptions) { responseSearch ->
                    println(responseSearch.hits)
                }
            }
        }
    }
}