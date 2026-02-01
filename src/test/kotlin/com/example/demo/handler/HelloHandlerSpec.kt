package com.example.demo.handler

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest

class HelloHandlerSpec : FunSpec() {
    private val helloHandler = HelloHandler()

    init {
        test("getHello should return OK status") {
            val request = mockk<ServerRequest>()

            val response = helloHandler.getHello(request)

            response.statusCode().value() shouldBe 200
        }

        test("getHello should return APPLICATION_JSON content type") {
            val request = mockk<ServerRequest>()

            val response = helloHandler.getHello(request)

            response.headers().contentType shouldBe MediaType.APPLICATION_JSON
        }
    }
}
