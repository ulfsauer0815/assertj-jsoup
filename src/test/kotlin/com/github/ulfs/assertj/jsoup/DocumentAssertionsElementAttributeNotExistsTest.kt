package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.DocumentAssertions.Companion.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertionsElementAttributeNotExistsTest {

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
            assertThat(document).elementAttributeNotExists(".class", "attr")
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
    fun `should pass if element exists without attribute`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when
        assertThat(document).elementAttributeNotExists(".class", "attr")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element with attribute exists`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementAttributeNotExists(".class", "attr")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
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
