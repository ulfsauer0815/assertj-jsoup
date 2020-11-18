package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.assertThat
import com.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import com.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementContainsTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementContainsText(".class", "ex")
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
                elementContainsText(".class", "ex")
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
    fun `should pass if element contains text`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text</div>""")

        // when
        assertThat(document, true) {
            elementContainsText(".class", "ex")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element contains text in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span>text</span></div>""")

        // when
        assertThat(document, true) {
            elementContainsText(".class", "ex")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element contains text in inner nodes`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span><b>t</b>e<strong>x</strong>t</span></div>""")

        // when
        assertThat(document, true) {
            elementContainsText(".class", "ex")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text is the entire text`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text</div>""")

        // when / then
        assertThat(document, true) {
            elementContainsText(".class", "text")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element does not contain the text`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">different</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementContainsText(".class", "text")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to contain text
                  <text>
                but was
                  <different>
                """.trimIndent()
            )
    }
}
