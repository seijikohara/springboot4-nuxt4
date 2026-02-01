package com.example.demo.config

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest

class SpaFallbackConfigSpec : FunSpec() {
    private val predicate = SpaFallbackConfig.isSpaRequest("/api")

    init {
        context("matching requests") {
            test("HTML root request matches") {
                val request = mockRequest("/", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe true
            }

            test("non-API non-file path matches") {
                val request = mockRequest("/about", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe true
            }

            test("nested path matches") {
                val request = mockRequest("/users/profile", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe true
            }

            test("path with dot in directory but not in filename matches") {
                val request = mockRequest("/v1.0/docs", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe true
            }

            test("multiple accept types including text/html matches") {
                val request =
                    mockRequest(
                        "/about",
                        listOf(
                            MediaType.TEXT_HTML,
                            MediaType.APPLICATION_XHTML_XML,
                            MediaType.ALL,
                        ),
                    )

                predicate.test(request) shouldBe true
            }

            test("path starting with API prefix but different word matches") {
                val request = mockRequest("/apiary", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe true
            }
        }

        context("non-matching requests") {
            test("API sub-path does not match") {
                val request = mockRequest("/api/hello", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe false
            }

            test("API base path does not match") {
                val request = mockRequest("/api", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe false
            }

            test("file extension does not match") {
                val request = mockRequest("/favicon.ico", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe false
            }

            test("Nuxt asset does not match") {
                val request = mockRequest("/_nuxt/entry.js", listOf(MediaType.TEXT_HTML))

                predicate.test(request) shouldBe false
            }

            test("JSON accept does not match") {
                val request = mockRequest("/about", listOf(MediaType.APPLICATION_JSON))

                predicate.test(request) shouldBe false
            }

            test("empty accept does not match") {
                val request = mockRequest("/about", emptyList())

                predicate.test(request) shouldBe false
            }

            test("wildcard accept does not match") {
                val request = mockRequest("/about", listOf(MediaType.ALL))

                predicate.test(request) shouldBe false
            }
        }
    }

    private fun mockRequest(
        path: String,
        acceptTypes: List<MediaType>,
    ): ServerRequest {
        val headers = mockk<ServerRequest.Headers>()
        every { headers.accept() } returns acceptTypes
        val request = mockk<ServerRequest>()
        every { request.path() } returns path
        every { request.headers() } returns headers
        return request
    }
}
