package com.github.ulfs.assertj.jsoup

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jsoup.nodes.Document
import kotlin.test.BeforeTest
import kotlin.test.Test

class DocumentAssertionsSpecTest {

    lateinit var softAssertions: DocumentSoftAssertions


    @BeforeTest
    fun setup() {
        softAssertions = mockk(relaxed = true)
    }


    @Test
    fun `should create NodeSpec`() {
        // given
        val document: Document = mockk()

        val spec = DocumentAssertionsSpec(
            softAssertions = softAssertions,
            document = document
        )

        val expectedNodeSpec = NodeAssertionsSpec(softAssertions, document, "selector")

        var nodeSpec: NodeAssertionsSpec? = null

        // when
        spec.node("selector") {
            nodeSpec = this
            this
        }

        // then
        assertThat(nodeSpec).usingRecursiveComparison().isEqualTo(expectedNodeSpec)
    }

    @Test
    fun `should call exists`() {
        // given
        val document: Document = mockk()
        every { softAssertions.assertThat(any<Document>()).elementExists(any()) } returns dummySoftAssertions()

        val spec = DocumentAssertionsSpec(
            softAssertions = softAssertions,
            document = document
        )

        // when
        spec.node("selector")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementExists("selector") }
        confirmVerified(softAssertions)
    }


    private fun dummySoftAssertions() = DocumentSoftAssertions()
        .assertThat(mockk<Document>())
}
