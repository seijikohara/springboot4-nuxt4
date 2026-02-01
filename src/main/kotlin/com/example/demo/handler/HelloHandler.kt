package com.example.demo.handler

import com.example.demo.logger.logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class HelloHandler {
    suspend fun getHello(request: ServerRequest): ServerResponse {
        logger.info("Called hello api")
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(mapOf("message" to "Hello"))
    }
}
