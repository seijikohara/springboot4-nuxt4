package com.example.demo.handler

import com.example.demo.config.ApplicationProperties
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.mockk.mockk
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest

class IndexHandlerSpec : FunSpec() {
    private val applicationProperties =
        mockk<ApplicationProperties> {
            every { indexFile } returns ByteArrayResource("<html></html>".toByteArray())
        }
    private val indexHandler = IndexHandler(applicationProperties)

    init {
        test("getIndex should return OK status") {
            val request = mockk<ServerRequest>()

            val response = indexHandler.getIndex(request)

            response.statusCode().value() shouldBe 200
        }

        test("getIndex should return TEXT_HTML content type") {
            val request = mockk<ServerRequest>()

            val response = indexHandler.getIndex(request)

            response.headers().contentType shouldBe MediaType.TEXT_HTML
        }

        test("getIndex should set Cache-Control to no-cache") {
            val request = mockk<ServerRequest>()

            val response = indexHandler.getIndex(request)

            response.headers().cacheControl shouldContain "no-cache"
        }
    }
}
