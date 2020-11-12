package com.github.ulfs.assertj.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertionsProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.function.Consumer

open class Assertions {
    companion object {
        @JvmStatic
        fun assertThat(actual: Document?): DocumentAssert = DocumentAssert(actual)

        @JvmStatic
        fun assertThatDocument(actual: String?): DocumentAssert = DocumentAssert(Jsoup.parse(actual))
            .also { assertThat(actual).withFailMessage("%nExpecting document but found%n  null").isNotNull }

        @JvmStatic
        fun qa(value: String): String = "*[data-qa=$value]"

        @JvmStatic
        fun assertSoftly(softly: Consumer<DocumentSoftAssertions>) {
            SoftAssertionsProvider.assertSoftly(DocumentSoftAssertions::class.java, softly)
        }

        @JvmStatic
        @JvmOverloads
        fun assertThatDocument(actual: String?, softly: Boolean = false, assert: DocumentAssert.() -> DocumentAssert) {
            val softAssertions = DocumentSoftAssertions(softly)
            val assertions = softAssertions.assertThatDocument(actual)
            assertions.assert()
            softAssertions.assertAll()
        }

        fun assertThatDocumentSpec(
            actual: String?,
            assert: DocumentAssertionsSpec.() -> DocumentAssertionsSpec
        ) = assertThatDocumentSpec(actual, false, assert)

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
