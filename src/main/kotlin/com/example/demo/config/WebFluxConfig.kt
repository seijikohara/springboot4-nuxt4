package com.example.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.ResourceHandlerRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.util.concurrent.TimeUnit

@Configuration
class WebFluxConfig(
    val applicationProperties: ApplicationProperties,
) : WebFluxConfigurer {
    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        val corsProperties = applicationProperties.cors
        corsRegistry
            .addMapping(corsProperties.mappingPathPattern)
            .allowedOrigins(*corsProperties.allowedOrigins.toTypedArray())
            .allowedMethods(*corsProperties.allowedMethods.toTypedArray())
            .maxAge(corsProperties.maxAge)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry
            .addResourceHandler("/_nuxt/**")
            .addResourceLocations("classpath:/static/_nuxt/")
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic())

        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCacheControl(CacheControl.noCache())
    }
}
