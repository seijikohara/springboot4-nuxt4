package com.example.demo.handler

import com.example.demo.config.ApplicationProperties
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class IndexHandler(
    private val applicationProperties: ApplicationProperties,
) {
    suspend fun getIndex(request: ServerRequest): ServerResponse =
        ServerResponse
            .ok()
            .contentType(MediaType.TEXT_HTML)
            .cacheControl(CacheControl.noCache())
            .bodyValueAndAwait(applicationProperties.indexFile)
}
