package com.github.ulfs.assertj.jsoup

import org.jsoup.nodes.Document

@DocumentAssertionsMarker
data class AttributeAssertionsSpec(
    private val softAssertions: DocumentSoftAssertions,
    private val document: Document,
    private val cssSelector: String,
    private val attributeName: String
) {
    fun exists() = apply {
        softAssertions.assertThat(document).elementAttributeExists(cssSelector, attributeName)
    }

    fun notExists() = apply {
        softAssertions.assertThat(document).elementAttributeExists(cssSelector, attributeName)
    }

    fun hasText(text: String) = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, text)
    }

    fun hasText(vararg texts: String) = apply {
        softAssertions.assertThat(document).elementAttributeHasText(cssSelector, attributeName, *texts)
    }

    fun hasText(obj: Any?) = apply {
        hasText(obj?.toString() ?: "")
    }
}
