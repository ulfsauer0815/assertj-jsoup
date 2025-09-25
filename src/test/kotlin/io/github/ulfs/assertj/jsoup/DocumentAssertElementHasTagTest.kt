package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.error.AssertJMultipleFailuresError
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementHasTagTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementHasTag(".class", "div")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(actualIsNull())
    }

    @Test
    fun `should fail if element does not exist`() {
        // given
        val document = JsoupUtils.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasTag(".class", "div")
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
    fun `should pass if element has tag`() {
        // given
        val document: Document = JsoupUtils.parse("""<div class="class">text</div>""")

        // when
        assertThat(document, true) {
            elementHasTag(".class", "div")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element is of a different tag`() {
        // given
        val document: Document = JsoupUtils.parse("""<span class="class">different</span>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementHasTag(".class", "div")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element for
                  <.class>
                to be of tag
                  <div>
                but was
                  <span>
                """.trimIndent()
            )
    }
}
