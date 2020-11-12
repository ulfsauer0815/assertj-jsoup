package com.github.ulfs.assertj.jsoup

import org.jsoup.nodes.Document

@DocumentAssertionsMarker
data class DocumentAssertionsSpec(
    private val softAssertions: DocumentSoftAssertions,
    private val document: Document
) {
    fun node(cssSelector: String, assert: NodeAssertionsSpec.() -> NodeAssertionsSpec): DocumentAssertionsSpec {
        val spec = NodeAssertionsSpec(softAssertions, document, cssSelector)
        spec.assert()
        return this
    }
}
