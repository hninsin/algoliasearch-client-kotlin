package com.algolia.search.saas.client

import com.algolia.search.saas.data.APIKey
import com.algolia.search.saas.data.ApplicationID
import io.ktor.client.features.logging.LogLevel


interface ConfigurationInterface {

    val applicationID: ApplicationID
    val apiKey: APIKey
    val writeTimeout: Long
    val readTimeout: Long
    val logLevel: LogLevel

    val RequestOptions?.computedWriteTimeout get() = this?.writeTimeout ?: writeTimeout
    val RequestOptions?.computedReadTimeout get() = this?.readTimeout ?: readTimeout
}