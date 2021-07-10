package io.github.ulfs.assertj.jsoup

import io.github.ulfs.assertj.jsoup.Assertions.assertThat
import io.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import io.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementAttributeNotExistsTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementAttributeNotExists(".class", "attr")
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
                elementAttributeNotExists(".class", "attr")
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
    fun `should pass if element exists without attribute`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when
        assertThat(document, true) {
            elementAttributeNotExists(".class", "attr")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element with attribute exists`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeNotExists(".class", "attr")
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
                to be absent, but was
                  <<div class="class" attr></div>>
                """.trimIndent()
            )
    }
}
