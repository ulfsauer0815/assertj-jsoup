package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementAttributeMatchesTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementAttributeMatchesText(".class", "attr", "v[ae]lue")
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
            assertThat(document).elementAttributeMatchesText(".class", "attr", "v[ae]lue")
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
    fun `should pass if element matches`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="value"></div>""")

        // when
        assertThat(document).elementAttributeMatchesText(".class", "attr", "v[ae]lue")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element has attribute in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span attr="value"></span></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementAttributeMatchesText(".class", "attr", "v[ae]lue")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
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
    fun `should fail if attribute does not match`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="different"></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementAttributeMatchesText(".class", "attr", "v[ae]lue")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting attribute
                  <attr>
                on element for
                  <.class>
                to match regex
                  <v[ae]lue>
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
            assertThat(document).elementAttributeMatchesText(".class", "attr", "v[ae]lue")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
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
