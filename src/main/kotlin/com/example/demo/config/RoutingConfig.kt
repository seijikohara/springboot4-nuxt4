package com.example.demo.config

import com.example.demo.handler.HelloHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RoutingConfig(
    private val applicationProperties: ApplicationProperties,
    private val helloHandler: HelloHandler,
) {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun apiRouter() =
        coRouter {
            (applicationProperties.apiBasePath).nest {
                GET("/hello", helloHandler::getHello)
            }
        }
}
