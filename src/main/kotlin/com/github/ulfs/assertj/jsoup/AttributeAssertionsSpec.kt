package com.github.ulfs.assertj.jsoup

import org.jsoup.nodes.Document

@DocumentAssertionsMarker
public data class AttributeAssertionsSpec(
    private val softAssertions: DocumentSoftAssertions,
    private val document: Document,
    private val cssSelector: String,
    private val attributeName: String
) {
    public fun exists(): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeExists(cssSelector, attributeName)
    }

    public fun notExists(): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeNotExists(cssSelector, attributeName)
    }

    public fun hasText(text: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, text)
    }

    public fun hasText(vararg texts: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, *texts)
    }

    public fun hasText(obj: Any?): AttributeAssertionsSpec = apply {
        hasText(obj?.toString() ?: "")
    }

    public fun containsText(substring: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeContainsText(cssSelector, attributeName, substring)
    }

    public fun matchesText(regex: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeMatchesText(cssSelector, attributeName, regex)
    }
}
