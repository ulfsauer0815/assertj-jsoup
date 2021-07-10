package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementMatchesTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementMatchesText(".class", "ex")
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
                elementMatchesText(".class", "ex")
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
    fun `should pass if element text matches`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text</div>""")

        // when
        assertThat(document, true) {
            elementMatchesText(".class", "t[ae]xt")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text matches in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span>text</span></div>""")

        // when
        assertThat(document, true) {
            elementMatchesText(".class", "t[ae]xt")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text matches in inner nodes`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span><b>t</b>e<strong>x</strong>t</span></div>""")

        // when
        assertThat(document, true) {
            elementMatchesText(".class", "t[ae]xt")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element text does not match`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">different</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementMatchesText(".class", "t[ae]xt")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to match regex
                  <t[ae]xt>
                but was
                  <different>
                """.trimIndent()
            )
    }
}
