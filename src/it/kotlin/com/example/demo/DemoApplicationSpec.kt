package com.example.demo

import io.kotest.core.extensions.ApplyExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
@ApplyExtension(SpringExtension::class)
class DemoApplicationSpec(
    private val context: ApplicationContext,
) : FunSpec() {
    init {
        test("context should load successfully") {
            context shouldNotBe null
        }

        test("application should start without errors") {
            context.beanDefinitionNames.size shouldNotBe 0
        }
    }
}
