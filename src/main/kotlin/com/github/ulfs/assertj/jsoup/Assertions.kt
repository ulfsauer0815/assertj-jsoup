package com.github.ulfs.assertj.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertionsProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.function.Consumer

public open class Assertions private constructor() {

    public companion object {
        @JvmStatic
        public fun assertThat(actual: Document?): DocumentAssert = DocumentAssert(actual)

        @JvmStatic
        public fun assertThatDocument(actual: String?): DocumentAssert =
            DocumentAssert(
                Jsoup.parse(actual
                    .also { assertThat(actual).withFailMessage("%nExpecting document but found null").isNotNull })
            )

        @JvmStatic
        public fun assertSoftly(assertions: Consumer<DocumentSoftAssertions>) {
            SoftAssertionsProvider.assertSoftly(DocumentSoftAssertions::class.java, assertions)
        }

        @JvmStatic
        @JvmOverloads
        public fun assertThatDocument(actual: String?, softly: Boolean = false, assert: DocumentAssert.() -> DocumentAssert) {
            val softAssertions = DocumentSoftAssertions(softly)
            val assertions = softAssertions.assertThatDocument(actual)
            assertions.assert()
            softAssertions.assertAll()
        }

        public fun assertThatDocumentSpec(
            actual: String?,
            softly: Boolean = false,
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

        @JvmStatic
        public fun qa(value: String): String = "*[data-qa=$value]"
    }
}
