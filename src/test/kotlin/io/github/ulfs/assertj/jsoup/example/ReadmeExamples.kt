package io.github.ulfs.assertj.jsoup.example

import io.github.ulfs.assertj.jsoup.Assertions
import io.github.ulfs.assertj.jsoup.Assertions.assertThatSpec
import org.jsoup.Jsoup
import kotlin.test.Test


class ReadmeExamples {

    @Test
    fun `Java Kotlin example`() {
        // given
        val document = Jsoup.parse(loadExample("javaKotlinExample.html"))

        // then
        Assertions.assertThat(document)
            .elementExists("h1")

            .elementContainsText(".example-code", "Kotlin")

            .elementHasText(".github-link", "Fork me on GitHub")
            .elementAttributeContainsText(".github-link", "href", "github")
            .elementAttributeContainsText(".github-link", "href", "assertj-jsoup")
            .elementAttributeHasText(".github-link", "target", "_blank")
            .elementAttributeNotExists(".github-link", "disabled")

        // then
        // no exception is thrown
    }

    @Test
    fun `Kotlin DSL example`() {
        // given
        val document = Jsoup.parse(loadExample("javaKotlinExample.html"))

        // then
        assertThatSpec(document) {

            node("h1") { exists() }

            node(".example-code") { containsText("Kotlin") }

            node(".github-link") {
                hasText("Fork me on GitHub")
                attribute("href") {
                    containsText("github")
                    containsText("assertj-jsoup")
                }
                attribute("target") { hasText("_blank") }
                attribute("disabled") { notExists() }
            }
        }

        // then
        // no exception is thrown
    }


    fun loadExample(filename: String): String = this::class.java
        .getResource("/example/$filename")
        .readText()
}
