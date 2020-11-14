package com.github.ulfs.assertj.jsoup

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jsoup.nodes.Document
import kotlin.test.BeforeTest
import kotlin.test.Test

class AttributeAssertionsSpecTest {

    lateinit var softAssertions: DocumentSoftAssertions


    @BeforeTest
    fun setup() {
        softAssertions = mockk(relaxed = true)
    }


    @Test
    fun `should call elementAttributeExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementAttributeExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.exists()

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeExists("selector", "name") }
    }

    @Test
    fun `should call elementAttributeNotExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementAttributeNotExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.notExists()

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeNotExists("selector", "name") }
    }

    @Test
    fun `should call elementHasText`() {
        // given
        every {
            softAssertions.assertThat(any<Document>()).elementAttributeHasText(any(), any(), any())
        } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText("text")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeHasText("selector", "name", "text") }
    }

    @Test
    fun `should call elementHasText with varargs`() {
        // given
        every {
            softAssertions.assertThat(any<Document>()).elementAttributeHasText(any(), any(), any(), any())
        } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText("text1", "text2")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeHasText("selector", "name", "text1", "text2") }
    }

    @Test
    fun `should call elementHasText with object`() {
        // given
        every {
            softAssertions.assertThat(any<Document>()).elementAttributeHasText(any(), any(), any())
        } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(42)

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeHasText("selector", "name", "42") }
    }

    @Test
    fun `should call elementHasText with null`() {
        // given
        every {
            softAssertions.assertThat(any<Document>()).elementAttributeHasText(any(), any(), any())
        } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(null)

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeHasText("selector", "name", "") }
    }

    @Test
    fun `should call elementHasText with toString returning null`() {
        // given
        every {
            softAssertions.assertThat(any<Document>()).elementAttributeHasText(any(), any(), any())
        } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(ClassWithNullToString())

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeHasText("selector", "name", "") }
    }


    private fun spec() = AttributeAssertionsSpec(
        softAssertions = softAssertions,
        document = mockk(),
        cssSelector = "selector",
        attributeName = "name"
    )

    private fun dummySoftAssertions() = DocumentSoftAssertions()
        .assertThat(mockk<Document>())
}