package com.github.ulfs.assertj.jsoup

import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertionsSpecTest {

    @Test
    fun `should create NodeSpec`() {
        // given
        val softAssertions: DocumentSoftAssertions = mockk(relaxed = true)
        val document: Document = mockk()

        val spec = DocumentAssertionsSpec(
            softAssertions = softAssertions,
            document = document
        )

        val expectedNodeSpec = NodeAssertionsSpec(softAssertions, document, "cssSelector")

        var nodeSpec: NodeAssertionsSpec? = null

        // when
        spec.node("cssSelector") {
            nodeSpec = this
            this
        }

        // then
        assertThat(nodeSpec).usingRecursiveComparison().isEqualTo(expectedNodeSpec)
    }
}
