package com.github.ulfs.assertj.jsoup

import org.jsoup.nodes.Document

@DocumentAssertionsMarker
data class NodeAssertionsSpec(
    private val softAssertions: DocumentSoftAssertions,
    private val document: Document,
    private val cssSelector: String
) {
    fun exists(): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementExists(cssSelector)
    }

    fun exists(count: Int) = apply {
        softAssertions.assertThat(document).elementExists(cssSelector, count)
    }

    fun notExists() = apply {
        softAssertions.assertThat(document).elementNotExists(cssSelector)
    }

    fun containsText(substring: String) = apply {
        softAssertions.assertThat(document).elementContainsText(cssSelector, substring)
    }

    fun hasText(text: String) = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, text)
    }

    fun hasText(vararg texts: String) = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, *texts)
    }

    fun hasText(obj: Any?) = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, obj?.toString() ?: "")
    }

    fun hasClass(name: String) = apply {
        softAssertions.assertThat(document).elementHasClass(cssSelector, name)
    }

    fun notHasClass(name: String) = apply {
        softAssertions.assertThat(document).elementNotHasClass(cssSelector, name)
    }

    fun hasAttribute(name: String) = apply {
        attribute(name) { exists() }
    }

    fun notHasAttribute(name: String) = apply {
        attribute(name) { notExists() }
    }

    fun attribute(attributeName: String, assert: AttributeAssertionsSpec.() -> AttributeAssertionsSpec) = apply {
        val spec = AttributeAssertionsSpec(softAssertions, document, cssSelector, attributeName)
        spec.assert()
    }
}
