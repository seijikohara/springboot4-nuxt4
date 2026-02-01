package com.example.demo.config

import com.example.demo.handler.IndexHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicate
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class SpaFallbackConfig(
    private val applicationProperties: ApplicationProperties,
    private val indexHandler: IndexHandler,
) {
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    fun spaFallbackRouter() =
        coRouter {
            val apiBasePath = applicationProperties.apiBasePath
            val isSpaRequest =
                RequestPredicate { request ->
                    val path = request.path()
                    val acceptsHtml =
                        request.headers().accept().any {
                            it.type == "text" && it.subtype == "html"
                        }
                    val isNotApi =
                        !path.startsWith("$apiBasePath/") && path != apiBasePath
                    val isNotFile = !path.substringAfterLast('/').contains('.')
                    acceptsHtml && isNotApi && isNotFile
                }
            GET("/**", isSpaRequest, indexHandler::getIndex)
        }
}
