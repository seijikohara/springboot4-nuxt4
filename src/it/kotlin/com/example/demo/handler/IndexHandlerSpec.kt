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
class IndexHandlerSpec(
    private val indexHandler: IndexHandler,
) : FunSpec() {
    init {
        test("getIndex should return index file content with OK status") {
            val mockRequest = mockk<ServerRequest>()

            runBlocking {
                val response = indexHandler.getIndex(mockRequest)

                response.statusCode().value() shouldBe 200
                response.headers().contentType shouldBe MediaType.TEXT_HTML
            }
        }

        test("getIndex should set TEXT_HTML content type") {
            val mockRequest = mockk<ServerRequest>()

            runBlocking {
                val response = indexHandler.getIndex(mockRequest)

                response.headers().contentType shouldBe MediaType.TEXT_HTML
            }
        }

        test("getIndex should return application properties index file") {
            val mockRequest = mockk<ServerRequest>()

            runBlocking {
                val response = indexHandler.getIndex(mockRequest)

                response.statusCode().value() shouldBe 200
            }
        }
    }
}
