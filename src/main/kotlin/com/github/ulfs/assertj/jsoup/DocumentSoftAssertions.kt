package com.github.ulfs.assertj.jsoup

import org.assertj.core.api.SoftAssertions
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

public class DocumentSoftAssertions(
    private val softly: Boolean = true
) : SoftAssertions() {

    public fun assertThat(actual: Document?): DocumentAssert =
        if (softly) proxy(DocumentAssert::class.java, Document::class.java, actual)
        else Assertions.assertThat(actual)

    public fun assertThatDocument(actual: String?): DocumentAssert = assertThat(Jsoup.parse(actual))
}
