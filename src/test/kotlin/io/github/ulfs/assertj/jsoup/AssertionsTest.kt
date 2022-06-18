package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.test.ReflectionUtils.callGetter
import io.mockk.called
import io.mockk.clearStaticMockk
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkConstructor
import io.mockk.unmockkStatic
import io.mockk.verify
import io.mockk.verifyAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.SoftAssertionsProvider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.junit.AfterClass
import org.junit.BeforeClass
import java.util.function.Consumer
import kotlin.test.BeforeTest
import kotlin.test.Test

class AssertionsTest {

    @BeforeTest
    fun setup() {
        clearStaticMockk(Jsoup::class)
        clearStaticMockk(SoftAssertionsProvider::class)
        clearStaticMockk(DocumentSoftAssertions::class)
    }


    @Test
    fun `should initialize assertion object`() {
        // given
        val document: Document = mockk()

        val expectedDocumentAssert = DocumentAssert(document)
        // when
        val documentAssert = Assertions.assertThat(document)

        // then
        assertThat(documentAssert).usingRecursiveComparison().isEqualTo(expectedDocumentAssert)
    }

    @Test
    fun `should initialize document and assertion object`() {
        // given
        val document: Document = mockk()
        val element: Element = mockk()
        every { Jsoup.parse(any<String>()) } returns document
        every { document.selectFirst(any<String>()) } returns element

        // when
        val documentAssert = Assertions.assertThatDocument("html")
        // then
        verify { Jsoup.parse("html") }

        // when
        documentAssert.elementExists(".class")
        // then
        verify { document.selectFirst(".class") }
    }

    @Test
    fun `should fail to parse null input`() {
        // when // then
        assertThatThrownBy {
            Assertions.assertThatDocument(null)
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting document but found null
                """.trimIndent()
            )
    }

    @Test
    fun `should return data-qa selector`() {
        // when
        val qa = Assertions.qa("value")

        // then
        assertThat(qa).isEqualTo("*[data-qa=value]")
    }

    @Test
    fun `should return SoftAssertions`() {
        every { SoftAssertionsProvider.assertSoftly(any<Class<DocumentSoftAssertions>>(), any()) } returns mockk()
        val assertions: Consumer<DocumentSoftAssertions> = mockk()

        // when
        Assertions.assertSoftly(assertions)

        // then
        verify { SoftAssertionsProvider.assertSoftly(DocumentSoftAssertions::class.java, assertions) }
    }

    @Test
    fun `should return DocumentSoftAssertions assertThatDocument`() {
        val assert: DocumentAssert = mockk()
        every { anyConstructed<DocumentSoftAssertions>().assertThatDocument(any()) } returns assert
        var documentAssert: DocumentAssert? = null

        // when
        Assertions.assertThatDocument("html") {
            documentAssert = this
            this
        }

        // then
        assertThat(documentAssert).isEqualTo(assert)
        verify {
            anyConstructed<DocumentSoftAssertions>().assertThatDocument("html")
            anyConstructed<DocumentSoftAssertions>().assertAll()
        }
    }

    @Test
    fun `should return DocumentSoftAssertions assertThat`() {
        val document: Document = mockk()
        val assert: DocumentAssert = mockk(relaxed = true)
        every { anyConstructed<DocumentSoftAssertions>().assertThat(any<Document>()) } returns assert
        var documentAssert: DocumentAssert? = null

        // when
        Assertions.assertThat(document) {
            documentAssert = this
            elementExists(".class")
            this
        }

        // then
        assertThat(documentAssert).isEqualTo(assert)
        verify {
            anyConstructed<DocumentSoftAssertions>().assertThatDocument("html") wasNot called
            anyConstructed<DocumentSoftAssertions>().assertAll()
            assert.elementExists(".class")
        }
    }

    @Test
    fun `should return DocumentSoftAssertions assertThat softly`() {
        val document: Document = mockk()
        val assert: DocumentAssert = mockk()
        every { anyConstructed<DocumentSoftAssertions>().assertThat(any<Document>()) } returns assert
        var documentAssert: DocumentAssert? = null

        // when
        Assertions.assertThat(document, softly = true) {
            documentAssert = this
            this
        }

        // then
        assertThat(documentAssert).isEqualTo(assert)
        verify {
            anyConstructed<DocumentSoftAssertions>().assertThat(document)
            anyConstructed<DocumentSoftAssertions>().assertAll()
        }
    }

    @Test
    fun `should return DocumentSoftAssertions assertThatDocumentSpec`() {
        var documentAssert: DocumentAssertionsSpec? = null

        // when
        Assertions.assertThatDocumentSpec("html") {
            documentAssert = this
            this
        }

        // then
        verify {
            anyConstructed<DocumentSoftAssertions>().assertAll()
        }

        val softAssertions = callGetter<DocumentSoftAssertions>(documentAssert!!, "softAssertions")
        val softly = callGetter<Boolean>(softAssertions, "softly")

        assertThat(documentAssert).isNotNull
        assertThat(softly).isFalse
    }

    @Test
    fun `should return DocumentSoftAssertions assertThatDocumentSpec softly`() {
        justRun { anyConstructed<DocumentSoftAssertions>().assertAll() }

        var documentAssert: DocumentAssertionsSpec? = null

        // when
        Assertions.assertThatDocumentSpec("html", true) {
            documentAssert = this
            this
        }

        // then
        verifyAll {
            anyConstructed<DocumentSoftAssertions>().assertAll()
        }

        val softAssertions = callGetter<DocumentSoftAssertions>(documentAssert!!, "softAssertions")
        val softly = callGetter<Boolean>(softAssertions, "softly")

        assertThat(documentAssert).isNotNull
        assertThat(softly).isTrue
    }

    @Test
    fun `should return DocumentSoftAssertions assertThatSpec`() {
        every { SoftAssertionsProvider.assertSoftly(any<Class<DocumentSoftAssertions>>(), any()) } returns mockk()

        var documentAssert: DocumentAssertionsSpec? = null

        // when
        Assertions.assertThatSpec(Jsoup.parse("html")) {
            documentAssert = this
            this
        }

        // then
        verify {
            anyConstructed<DocumentSoftAssertions>().assertAll()
        }

        val softAssertions = callGetter<DocumentSoftAssertions>(documentAssert!!, "softAssertions")
        val softly = callGetter<Boolean>(softAssertions, "softly")

        assertThat(documentAssert).isNotNull
        assertThat(softly).isFalse
    }


    companion object {
        @JvmStatic
        @BeforeClass
        fun setupAll() {
            mockkStatic(Jsoup::class)
            mockkStatic(SoftAssertionsProvider::class)
            mockkConstructor(DocumentSoftAssertions::class)
        }

        @JvmStatic
        @AfterClass
        fun tearDownAll() {
            unmockkStatic(Jsoup::class)
            unmockkStatic(SoftAssertionsProvider::class)
            unmockkConstructor(DocumentSoftAssertions::class)
        }
    }
}
