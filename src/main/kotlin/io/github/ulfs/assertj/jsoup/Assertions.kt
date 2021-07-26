package io.github.ulfs.assertj.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertionsProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.function.Consumer

public object Assertions {

    @JvmStatic
    public fun assertThat(actual: Document?): DocumentAssert = DocumentAssert(actual)

    @JvmStatic
    public fun assertThatDocument(actual: String?): DocumentAssert {
        assertThat(actual)
            .withFailMessage("%nExpecting document but found null")
            .isNotNull

        return DocumentAssert(Jsoup.parse(actual!!))
    }

    @JvmStatic
    public fun assertSoftly(assertions: Consumer<DocumentSoftAssertions>) {
        SoftAssertionsProvider.assertSoftly(DocumentSoftAssertions::class.java, assertions)
    }

    @JvmStatic
    @JvmOverloads
    public fun assertThatDocument(
        actual: String?,
        softly: Boolean = false,
        assert: DocumentAssert.() -> DocumentAssert
    ) {
        val softAssertions = DocumentSoftAssertions(softly)
        val assertions = softAssertions.assertThatDocument(actual)
        assertions.assert()
        softAssertions.assertAll()
    }

    @JvmStatic
    @JvmOverloads
    public fun assertThat(
        actual: Document,
        softly: Boolean = false,
        assert: DocumentAssert.() -> DocumentAssert
    ) {
        val softAssertions = DocumentSoftAssertions(softly)
        val assertions = softAssertions.assertThat(actual)
        assertions.assert()
        softAssertions.assertAll()
    }

    @JvmSynthetic
    public fun assertThatDocumentSpec(
        actual: String?,
        softly: Boolean = false,
        assert: DocumentAssertionsSpec.() -> DocumentAssertionsSpec
    ) {
        assertThat(actual)
            .withFailMessage("%nExpecting document but found null")
            .isNotNull

        actual?.let {
            val document = Jsoup.parse(it)
            assertThatSpec(document, softly, assert)
        }
    }

    @JvmSynthetic
    public fun assertThatSpec(
        document: Document,
        softly: Boolean = false,
        assert: DocumentAssertionsSpec.() -> DocumentAssertionsSpec
    ) {
        val softAssertions = DocumentSoftAssertions(softly)
        val spec = DocumentAssertionsSpec(softAssertions, document)
        spec.assert()
        softAssertions.assertAll()
    }

    @JvmStatic
    public fun qa(value: String): String = "*[data-qa=$value]"
}
