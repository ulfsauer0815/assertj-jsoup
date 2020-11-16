package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.test.ClassWithNullToString
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jsoup.nodes.Document
import kotlin.test.BeforeTest
import kotlin.test.Test

class NodeAssertionsSpecTest {

    lateinit var softAssertions: DocumentSoftAssertions


    @BeforeTest
    fun setup() {
        softAssertions = mockk(relaxed = true)
    }


    @Test
    fun `should call elementExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementExists(any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.exists()

        // then
        verify { softAssertions.assertThat(any<Document>()).elementExists("selector") }
    }

    @Test
    fun `should call elementExists with count`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.exists(42)

        // then
        verify { softAssertions.assertThat(any<Document>()).elementExists("selector", 42) }
    }

    @Test
    fun `should call elementNotExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementNotExists(any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.notExists()

        // then
        verify { softAssertions.assertThat(any<Document>()).elementNotExists("selector") }
    }

    @Test
    fun `should call elementContainsText`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementContainsText(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.containsText("text")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementContainsText("selector", "text") }
    }

    @Test
    fun `should call elementHasText`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasText(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText("text")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasText("selector", "text") }
    }

    @Test
    fun `should call elementHasText with multiple arguments`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasText(any(), any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText("text1", "text2")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasText("selector", "text1", "text2") }
    }

    @Test
    fun `should call elementHasText with object argument`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasText(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(42)

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasText("selector", "42") }
    }

    @Test
    fun `should call elementHasText with null argument`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasText(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(null)

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasText("selector", "") }
    }

    @Test
    fun `should call elementHasText with argument returning null for toString`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasText(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasText(ClassWithNullToString())

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasText("selector", "") }
    }

    @Test
    fun `should call elementHasClass`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementHasClass(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasClass("class")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementHasClass("selector", "class") }
    }

    @Test
    fun `should call elementNotHasClass`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementNotHasClass(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.notHasClass("class")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementNotHasClass("selector", "class") }
    }

    @Test
    fun `should call elementAttributeExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementAttributeExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.hasAttribute("attr")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeExists("selector", "attr") }
    }

    @Test
    fun `should call not elementAttributeNotExists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementAttributeNotExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.notHasAttribute("attr")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeNotExists("selector", "attr") }
    }

    @Test
    fun `should call exists`() {
        // given
        every { softAssertions.assertThat(any<Document>()).elementAttributeExists(any(), any()) } returns dummySoftAssertions()

        val spec = spec()

        // when
        spec.attribute("attr")

        // then
        verify { softAssertions.assertThat(any<Document>()).elementAttributeExists("selector", "attr") }
    }


    private fun spec() = NodeAssertionsSpec(
        softAssertions = softAssertions,
        document = mockk(),
        cssSelector = "selector"
    )

    private fun dummySoftAssertions() = DocumentSoftAssertions()
        .assertThat(mockk<Document>())
}
