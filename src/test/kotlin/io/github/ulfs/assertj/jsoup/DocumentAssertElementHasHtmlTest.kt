package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.error.AssertJMultipleFailuresError
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementHasHtmlTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementHasHtml(".class", "<br>")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(actualIsNull())
    }

    @Test
    fun `should fail if element does not exist`() {
        // given
        val document = Jsoup.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "<br>")
            }
        }
            .isInstanceOf(AssertJMultipleFailuresError::class.java)
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
    fun `should throw if insufficient parameters are given`() {
        // given
        val document: Document = Jsoup.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class")
            }
        }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `should fail if element does not exist for multiple strings`() {
        // given
        val document: Document = Jsoup.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "<br>", "<br>")
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
    fun `should pass if element has html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text<br>html</div>""")

        // when
        assertThat(document, true) {
            elementHasHtml(".class", "text<br>html")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element has html in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span>text<br>html</span></div>""")

        // when
        assertThat(document, true) {
            elementHasHtml(".class", "<span>text<br>html</span>")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element has html in inner nodes`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span><b>h</b>t<strong>m</strong>l</span></div>""")

        // when
        assertThat(document, true) {
            elementHasHtml(".class", "<span><b>h</b>t<strong>m</strong>l</span>")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element contains substring`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">text<br>html</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "<br>")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to have html
                  <<br>>
                but was
                  <text<br>html>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if element contains different html`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class">different<br>html</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "text<br>html")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to have html
                  <text<br>html>
                but was
                  <different<br>html>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if elements have corresponding html`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this<br>is</div>
            <div class="class">really<br>long</div>
            <div class="class">html<br>text</div>
            """.trimIndent()
        )

        // when
        assertThat(document, true) {
            elementHasHtml(".class", "this<br>is", "really<br>long", "html<br>text")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if elements dont have matching html`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this<br>is</div>
            <div class="class">really<br>wrong</div>
            <div class="class">html<br>text</div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "this<br>is", "really<br>long", "html<br>text")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element at position 1 in list for
                  <.class>
                to have html
                  <really<br>long>
                but was
                  <really<br>wrong>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if elements are missing one element`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this<br>is</div>
            <div class="class">really<br>long</div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasHtml(".class", "this<br>is", "really<br>long", "html<br>text")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting 1 more element(s) for
                  .class
                to be html
                  [html<br>text]
                in list
                  <div class="class">
                   this<br>is
                  </div>
                  <div class="class">
                   really<br>long
                  </div>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if elements have more elements than given`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class">this<br>is</div>
            <div class="class">really<br>long</div>
            <div class="class">html<br>text</div>
            <div class="class">but<br>more</div>
            """.trimIndent()
        )

        // when / then
        assertThat(document, true) {
            elementHasHtml(".class", "this<br>is", "really<br>long", "html<br>text")
        }

        // then
        // no exception is thrown
    }
}
