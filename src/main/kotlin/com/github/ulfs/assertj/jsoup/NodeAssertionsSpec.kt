package com.github.ulfs.assertj.jsoup

import org.jsoup.nodes.Document

@DocumentAssertionsMarker
public data class NodeAssertionsSpec(
    private val softAssertions: DocumentSoftAssertions,
    private val document: Document,
    private val cssSelector: String
) {
    public fun exists(): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementExists(cssSelector)
    }

    public fun exists(count: Int): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementExists(cssSelector, count)
    }

    public fun notExists(): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementNotExists(cssSelector)
    }

    public fun containsText(substring: String): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementContainsText(cssSelector, substring)
    }

    public fun hasText(text: String): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, text)
    }

    public fun hasText(vararg texts: String): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, *texts)
    }

    public fun hasText(obj: Any?): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementHasText(cssSelector, obj?.toString() ?: "")
    }

    public fun hasClass(name: String): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementHasClass(cssSelector, name)
    }

    public fun notHasClass(name: String): NodeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementNotHasClass(cssSelector, name)
    }

    public fun hasAttribute(name: String): NodeAssertionsSpec = apply {
        attribute(name) { exists() }
    }

    public fun notHasAttribute(name: String): NodeAssertionsSpec = apply {
        attribute(name) { notExists() }
    }

    public fun attribute(
        attributeName: String,
        assert: AttributeAssertionsSpec.() -> AttributeAssertionsSpec
    ): NodeAssertionsSpec =
        apply {
            val spec = AttributeAssertionsSpec(softAssertions, document, cssSelector, attributeName)
            spec.assert()
        }

    public fun attribute(attributeName: String): NodeAssertionsSpec = attribute(attributeName) { exists() }
}
