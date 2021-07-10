package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementAttributeContainsTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementAttributeContainsText(".class", "attr", "al")
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
            assertThat(document, true) {
                elementAttributeContainsText(".class", "attr", "al")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                but found nothing
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if element has entire attribute value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="value"></div>""")

        // when
        assertThat(document, true) {
            elementAttributeContainsText(".class", "attr", "value")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element has attribute in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span attr="value"></span></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeContainsText(".class", "attr", "al")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting attribute
                  <attr>
                on elements for
                  <.class>
                but found
                  <div class="class">
                   <span attr="value"></span>
                  </div>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if searched value is substring of attribute value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="value"></div>""")

        // when / then
        assertThat(document, true) {
            elementAttributeContainsText(".class", "attr", "al")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if attribute contains different value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="different"></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeContainsText(".class", "attr", "al")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting attribute
                  <attr>
                on element for
                  <.class>
                to contain
                  <al>
                but was
                  <different>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if attribute is missing on element`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class""></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeContainsText(".class", "attr", "al")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting attribute
                  <attr>
                on elements for
                  <.class>
                but found
                  <div class="class" "></div>
                """.trimIndent()
            )
    }
}
