package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.Companion.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementHasClassTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementHasClass(".class", "content")
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
            assertThat(document).elementHasClass(".class", "content")
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
    fun `should pass if element has class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class content">text</div>""")

        // when
        assertThat(document).elementHasClass(".class", "content")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if only inner element has class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span class="content">text</span></div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasClass(".class", "content")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                to include class
                  <content>
                but found
                  <div class="class">
                   <span class="content">text</span>
                  </div>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if element does not have class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class cont">text</div>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementHasClass(".class", "content")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                to include class
                  <content>
                but found
                  <div class="class cont">
                   text
                  </div>
                """.trimIndent()
            )
    }
}
