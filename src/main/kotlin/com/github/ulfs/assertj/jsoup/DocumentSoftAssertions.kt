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

    fun assertThat(actual: Document?): DocumentAssert =
        if (softly) proxy(DocumentAssert::class.java, Document::class.java, actual)
        else assertThat(actual)

    fun assertThatDocument(actual: String?): DocumentAssert = assertThat(Jsoup.parse(actual))
}
