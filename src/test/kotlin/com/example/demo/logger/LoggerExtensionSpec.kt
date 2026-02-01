package com.example.demo.logger

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.slf4j.Logger

class LoggerExtensionSpec : FunSpec() {
    init {
        test("logger should return a Logger instance") {
            val instance = SampleClass()

            instance.logger.shouldBeInstanceOf<Logger>()
        }

        test("logger should have correct name matching the class") {
            val instance = SampleClass()

            instance.logger.name shouldBe SampleClass::class.java.name
        }

        test("logger should return consistent logger for different instances") {
            val instance1 = SampleClass()
            val instance2 = SampleClass()

            instance1.logger.name shouldBe instance2.logger.name
        }
    }
}

private class SampleClass
