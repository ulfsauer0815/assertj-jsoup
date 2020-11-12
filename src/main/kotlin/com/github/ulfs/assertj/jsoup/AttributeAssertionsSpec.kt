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
        softAssertions.assertThat(document).elementAttributeExists(cssSelector, attributeName)
    }

    public fun hasText(text: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, text)
    }

    public fun hasText(text0: String, vararg texts: String): AttributeAssertionsSpec = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, text0, *texts)
    }

    public fun hasText(obj: Any?): AttributeAssertionsSpec = apply {
        hasText(obj?.toString() ?: "")
    }
}
