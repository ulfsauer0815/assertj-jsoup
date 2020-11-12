package com.github.ulfs.assertj.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.SoftAssertionsProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.function.Consumer

class DocumentSoftAssertions(
    private val softly: Boolean = true
) : SoftAssertions() {
    fun assertThat(actual: Document?): DocumentAssertions =
        if (softly) proxy(DocumentAssertions::class.java, Document::class.java, actual)
        else DocumentAssertions.assertThat(actual)

    fun assertThatDocument(actual: String?): DocumentAssertions = assertThat(Jsoup.parse(actual))

    companion object {
        @JvmStatic
        @JvmName("assertDocumentSoftly")
        fun assertSoftly(softly: Consumer<DocumentSoftAssertions>) {
            SoftAssertionsProvider.assertSoftly(DocumentSoftAssertions::class.java, softly)
        }

        @JvmStatic
        @JvmOverloads
        fun assertThatDocument(actual: String?, softly: Boolean = false, assert: DocumentAssertions.() -> DocumentAssertions) {
            val softAssertions = DocumentSoftAssertions(softly)
            val assertions = softAssertions.assertThatDocument(actual)
            assertions.assert()
            softAssertions.assertAll()
        }

        fun assertThatDocumentSpec(
            actual: String?,
            softly: Boolean = true,
            assert: DocumentAssertionsSpec.() -> DocumentAssertionsSpec
        ) {
            val softAssertions = DocumentSoftAssertions(softly)
            assertThat(actual)
                .withFailMessage("%nExpecting document but found%n  null")
                .isNotNull
            val document = Jsoup.parse(actual)
            val spec = DocumentAssertionsSpec(softAssertions, document)
            spec.assert()
            softAssertions.assertAll()
        }
    }
}
