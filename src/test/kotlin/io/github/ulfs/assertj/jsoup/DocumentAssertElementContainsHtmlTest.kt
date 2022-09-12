package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementContainsHtmlTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementContainsHtml(".class", "<br>")
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
                elementContainsHtml(".class", "<br>")
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
    fun `should pass if element contains html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">html<br>text</div>""")

        // when
        assertThat(document, true) {
            elementContainsHtml(".class", """ml<br>te""")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element contains html in different form`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">html<br/>text</div>""")

        // when
        assertThat(document, true) {
            elementContainsHtml(".class", """ml<br>te""")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element contains nested html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><p><span>text</span><span>html</span></p></div>""")

        // when
        assertThat(document, true) {
            elementContainsHtml(".class", "<span>html</span>")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element contains complex html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span><b>h</b>t<strong>m</strong>l</span></div>""")

        // when
        assertThat(document, true) {
            elementContainsHtml(".class", "<b>h</b>t<strong>m</strong>l")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text is the entire html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">html<br>text</div>""")

        // when / then
        assertThat(document, true) {
            elementContainsHtml(".class", "html<br>text")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element does not contain the html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">different<img>html</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementContainsHtml(".class", "text")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to contain html
                  <text>
                but was
                  <different<img>html>
                """.trimIndent()
            )
    }
}
