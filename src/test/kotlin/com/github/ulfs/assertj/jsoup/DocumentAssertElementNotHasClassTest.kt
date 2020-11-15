package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementNotHasClassTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementNotHasClass(".class", "content")
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
            assertThat(document).elementNotHasClass(".class", "content")
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
    fun `should pass if element does not have class attribute`() {
        // given
        val document: Document = Jsoup.parse("""<div id="id">""")

        // when / then
        assertThat(document).elementNotHasClass("#id", "content")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element has class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class content">text</div>""")

        // when
        assertThatThrownBy {
            assertThat(document).elementNotHasClass(".class", "content")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                        
                        Expecting element for
                          <.class>
                        to not include class
                          <content>
                        but was
                          <<div class="class content">
                         text
                        </div>>
                        """.trimIndent()
            )

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if only inner element has class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"><span class="content">text</span></div>""")

        // when
        assertThat(document).elementNotHasClass(".class", "content")

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element does not have class`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class cont">text</div>""")

        // when / then
        assertThat(document).elementNotHasClass(".class", "content")
    }
}
