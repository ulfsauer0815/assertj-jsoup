package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementMatchesHtmlTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementMatchesHtml(".class", "<br>")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(actualIsNull())
    }

    @Test
    fun `should fail if element does not exist`() {
        // given
        val document: Document = JsoupUtils.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementMatchesHtml(".class", "<br>")
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
    fun `should pass if element html matches`() {
        // given
        val document: Document = JsoupUtils.parse("""<div class="class">text<br>html</div>""")
            .outputSettings(Document.OutputSettings().prettyPrint(false))

        // when
        assertThat(document, true) {
            elementMatchesHtml(".class", "text<br?>html")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text matches in inner node`() {
        // given
        val document: Document = JsoupUtils.parse("""<div class="class"><span>text<br>html</span></div>""")
            .outputSettings(Document.OutputSettings().prettyPrint(false))

        // when
        assertThat(document, true) {
            elementMatchesHtml(".class", "text<br?>html")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element text matches in inner nodes`() {
        // given
        val document: Document = JsoupUtils.parse("""<div class="class"><span><b>h</b>t<strong>m</strong>l</span></div>""")

        // when
        assertThat(document, true) {
            elementMatchesHtml(".class", "<strong>[mn]</strong>")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element html does not match`() {
        // given
        val document: Document = JsoupUtils.parse("""<div class="class">text<img>html</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementMatchesHtml(".class", "text<br?>html")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to match regex for html
                  <text<br?>html>
                but was
                  <text<img>html>
                """.trimIndent()
            )
    }
}
