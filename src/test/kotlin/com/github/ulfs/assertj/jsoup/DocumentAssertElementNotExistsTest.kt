package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementNotExistsTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementNotExists(".class")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(actualIsNull())
    }

    @Test
    fun `should pass if element does not exist`() {
        // given
        val document: Document = Jsoup.parse("")

        // when
        assertThat(document).elementNotExists(".class")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element exists`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementNotExists(".class")
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting element for
                  <.class>
                to be absent, but was
                  <div class="class"></div>
                """.trimIndent()
            )
    }
}
