package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.Companion.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementHasTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementHasText(".class", "text")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(actualIsNull())
    }

    @Test
    fun `should fail if element does not exist`() {
        // given
        val document: Document = Jsoup.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasText(".class", "text")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                but found nothing
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if element has text`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text</div>""")

        // when
        assertThat(document).elementHasText(".class", "text")

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element has text in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span>text</span></div>""")

        // when
        assertThat(document).elementHasText(".class", "text")

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element has text in inner nodes`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span><b>t</b>e<strong>x</strong>t</span></div>""")

        // when
        assertThat(document).elementHasText(".class", "text")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element contains substring`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasText(".class", "ex")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                to have text
                  <ex>
                but was
                  <text>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if element contains different text`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">different</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasText(".class", "text")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                to have text
                  <text>
                but was
                  <different>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if elements have corresponding text`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this</div>
            <div class="class">is</div>
            <div class="class">text</div>
            """.trimIndent()
        )

        // when
        assertThat(document).elementHasText(".class", "this", "is", "text")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if elements dont have matching text`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this</div>
            <div class="class">is not</div>
            <div class="class">text</div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasText(".class", "this", "is", "text")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element at position 1 in list for
                  <.class>
                to have text
                  <is>
                but was
                  <is not>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if elements are missing one element`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this</div>
            <div class="class">is</div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasText(".class", "this", "is", "text")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting 1 more element(s) for
                  .class
                to be
                  [text]
                in list
                  <div class="class">
                   this
                  </div>
                  <div class="class">
                   is
                  </div>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if elements have more elements than given`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this</div>
            <div class="class">is</div>
            <div class="class">text</div>
            <div class="class">but more</div>
            """.trimIndent()
        )

        // when / then
        assertThat(document).elementHasText(".class", "this", "is", "text")

        // then
        // no exception is thrown
    }
}
