package io.github.ulfs.assertj.jsoup

import io.mockk.clearStaticMockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkStatic
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.BeforeTest
import kotlin.test.Test

class DocumentSoftAssertionsTest {

    @BeforeTest
    fun setup() {
        clearStaticMockk(Jsoup::class)
    }


    @Test
    fun `should collect assertion and throw later`() {
        // given
        val assertions = DocumentSoftAssertions(true)

        // when
        assertions.assertThat(Jsoup.parse(""))
            .elementExists(".class")
        // then
        // no exception is thrown

        // when / then
        assertThatThrownBy {
            assertions.assertAll()
        }
            .isInstanceOf(AssertionError::class.java)
    }

    @Test
    fun `should throw assertion immediately`() {
        // given
        val assertions = DocumentSoftAssertions(false)

        // when / then
        assertThatThrownBy {
            assertions.assertThat(Jsoup.parse(""))
                .elementExists(".class")
        }
            .isInstanceOf(AssertionError::class.java)
    }

    @Test
    fun `should parse and call assertThat`() {
        // given
        val assertions = spyk<DocumentSoftAssertions>()

        // when / then
        assertions.assertThatDocument("html")

        // then
        verify { assertions.assertThat(any<Document>()) }
        verify { Jsoup.parse("html") }
    }

    @Test
    fun `should call assertThat on null argument`() {
        // given
        val assertions = spyk<DocumentSoftAssertions>()

        // when / then
        assertions.assertThatDocument(null)

        // then
        verify { assertions.assertThat(null as Document?) }
        verify(exactly = 0) { Jsoup.parse(any<String>()) }
    }


    companion object {
        @JvmStatic
        @BeforeClass
        fun setupAll() {
            mockkStatic(Jsoup::class)
        }

        @JvmStatic
        @AfterClass
        fun tearDownAll() {
            unmockkStatic(Jsoup::class)
        }
    }
}
