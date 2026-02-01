package com.example.demo.handler

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest

@SpringBootTest
@ApplyExtension(SpringExtension::class)
class HelloHandlerSpec(
    private val helloHandler: HelloHandler,
) : FunSpec() {
    init {
        test("getHello should return Hello message with OK status") {
            val mockRequest = mockk<ServerRequest>()

            runBlocking {
                val response = helloHandler.getHello(mockRequest)

                response.statusCode().value() shouldBe 200
                response.headers().contentType shouldBe MediaType.TEXT_HTML
            }
        }

        test("getHello should set TEXT_HTML content type") {
            val mockRequest = mockk<ServerRequest>()

            runBlocking {
                val response = helloHandler.getHello(mockRequest)

                response.headers().contentType shouldBe MediaType.TEXT_HTML
            }
        }

        test("getHello should handle multiple requests") {
            val mockRequest1 = mockk<ServerRequest>()
            val mockRequest2 = mockk<ServerRequest>()

            runBlocking {
                val response1 = helloHandler.getHello(mockRequest1)
                val response2 = helloHandler.getHello(mockRequest2)

                response1.statusCode().value() shouldBe 200
                response2.statusCode().value() shouldBe 200
            }
        }
    }
}
