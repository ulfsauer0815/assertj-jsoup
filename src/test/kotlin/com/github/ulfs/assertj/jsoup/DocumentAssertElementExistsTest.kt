package com.github.ulfs.assertj.jsoup

import com.github.ulfs.assertj.jsoup.Assertions.Companion.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.util.FailureMessages.actualIsNull
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import kotlin.test.Test

class DocumentAssertElementExistsTest {

    @Test
    fun `should fail if element is null`() {
        // given
        val nullDocument: Document? = null

        // when / then
        assertThatThrownBy {
            assertThat(nullDocument).elementExists(".class")
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
            assertThat(document).elementExists(".class")
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
    fun `should pass if element exists`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when
        assertThat(document).elementExists(".class")

        // then
        // no exception is thrown
    }

    @Test
    fun `should pass if element exists more than once`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/><div class="class"/>""")

        // when
        assertThat(document).elementExists(".class")

        // then
        // no exception is thrown
    }

    @Test
    fun `should fail if element exists less than twice`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementExists(".class", 2)
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting elements for
                  <.class>
                to have size of
                  <2>
                but had
                  <1>
                with elements:
                  <div class="class"></div>
                """.trimIndent()
            )
    }

    @Test
    fun `should fail if element exists more than twice`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/><div class="class"/><div class="class"/>""")

        // when / then
        assertThatThrownBy {
            assertThat(document).elementExists(".class", 2)
        }
            .isInstanceOf(AssertionError::class.java)
            .hasMessage(
                """
                
                Expecting elements for
                  <.class>
                to have size of
                  <2>
                but had
                  <3>
                with elements:
                  <div class="class"></div>
                  <div class="class"></div>
                  <div class="class"></div>
                """.trimIndent()
            )
    }

    @Test
    fun `should pass if element exists exactly twice`() {
        // given
        val document: Document = Jsoup.parse("""<div class="class"/><div class="class"/>""")

        // when
        assertThat(document).elementExists(".class", 2)

        // then
        // no exception is thrown
    }
}
