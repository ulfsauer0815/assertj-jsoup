package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.Companion.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementAttributeExistsTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementAttributeExists(".class", "attr")
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
            assertThat(document).elementAttributeExists(".class", "attr")
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
    fun `should fail if element exists without attribute`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementAttributeExists(".class", "attr")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting attribute
                  <attr>
                on elements for
                  <.class>
                but found
                  <<div class="class"></div>>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if element with attribute exists`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr/>""")

        // when
        assertThat(document).elementAttributeExists(".class", "attr")

        // then
        // no exception is thrown
    }
}
