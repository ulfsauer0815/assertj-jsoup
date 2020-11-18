package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.assertThat
import com.github.ulfs.assertj.jsoup.test.hasErrorWithMessage
import com.github.ulfs.assertj.jsoup.test.hasOneError
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementAttributeHasTextTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementAttributeHasText(".class", "attr", "value")
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
                elementAttributeHasText(".class", "attr", "value")
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
    fun `should throw if insufficient parameters are given`() {
        // given
        val document: Document = Jsoup.parse("")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr")
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
                elementAttributeHasText(".class", "attr", "value", "value")
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
    fun `should pass if element has attribute value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="value"></div>""")

        // when
        assertThat(document, true) {
            elementAttributeHasText(".class", "attr", "value")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element has attribute in inner node`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span attr="value"></span></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "value", "value")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element at position 0 in list for
                  <.class>
                to have attribute
                  <attr>
                but was
                  <<div class="class">
                 <span attr="value"></span>
                </div>>
                in list
                  <div class="class">
                   <span attr="value"></span>
                  </div>
                    """.trimIndent()
            )
    }

    @Test
    fun `should fail if searched value is substring of attribute value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="value"></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "al")
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
                to be
                  <al>
                but was
                  <value>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if attribute contains different value`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class" attr="different"></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "value")
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
                to be
                  <value>
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
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "value")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
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

    @Test
    fun `should pass if elements have corresponding attributes`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class" attr="this"></div>
            <div class="class" attr="is"></div>
            <div class="class" attr="value"></div>
            """.trimIndent()
        )

        // when
        assertThat(document, true) {
            elementAttributeHasText(".class", "attr", "this", "is", "value")
        }

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if elements do not have matching attributes`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class" attr="this"></div>
            <div class="class" attr="is not"></div>
            <div class="class" attr="value"></div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "this", "is", "value")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting element at position 1 in list for
                  <.class>
                to have attribute value
                  <is>
                but was
                  <is not>
                in list
                  <div class="class" attr="this"></div>
                  <div class="class" attr="is not"></div>
                  <div class="class" attr="value"></div>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if elements are missing one element with attribute`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class" attr="this"></div>
            <div class="class" attr="is"></div>
            """.trimIndent()
        )

        // when / then
        assertThatThrownBy {
            assertThat(document, true) {
                elementAttributeHasText(".class", "attr", "this", "is", "value")
            }
        }
            .isInstanceOf(AssertionError::class.java)
            .hasOneError()
            .hasErrorWithMessage(
                """
                
                Expecting 1 remaining elements:
                  <.class>
                in list for
                  <[value]>
                but was
                  <  <div class="class" attr="this"></div>
                  <div class="class" attr="is"></div>>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if elements have more elements with attribute than given`() {
        // given
        val document: Document = Jsoup.parse(
            """
            <div class="class" attr="this"></div>
            <div class="class" attr="is"></div>
            <div class="class" attr="value"></div>
            <div class="class" attr="one more"></div>
            """.trimIndent()
        )

        // when / then
        assertThat(document, true) {
            elementAttributeHasText(".class", "attr", "this", "is", "value")
        }

        // then
        // no exception is thrown
    }
}
