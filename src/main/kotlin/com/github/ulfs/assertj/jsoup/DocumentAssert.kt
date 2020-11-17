package com.github.ulfs.assertj.jsoup

import com.github.ulfs.test.coverage.Generated
import org.assertj.core.api.AbstractAssert
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

public open class DocumentAssert(
    actual: Document?
) : AbstractAssert<DocumentAssert, Document>(actual, DocumentAssert::class.java) {

    public fun elementExists(cssSelector: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection == null) {
            failWithElementNotFound(cssSelector)
        }
    }

    public fun elementExists(cssSelector: String, count: Int): DocumentAssert = apply {
        isNotNull

        val selection = actual.select(cssSelector)
        if (selection.size != count) {
            failWithMessage(
                "%nExpecting elements for%n" +
                        "  <%s>%n" +
                        "to have size of%n" +
                        "  <%s>%n" +
                        "but had%n" +
                        "  <%s>%n" +
                        "with elements:%n" +
                        "%s",
                cssSelector,
                count,
                selection.size,
                maskSelection(selection)
            )
        }
    }

    public fun elementNotExists(cssSelector: String): DocumentAssert = also {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection != null) {
            failWithActualExpectedAndMessage(
                selection,
                null,
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to be absent, but was%n" +
                        "%s",
                cssSelector,
                maskSelection(selection)
            )
        }
    }

    public fun elementAttributeExists(cssSelector: String, attribute: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.select(cssSelector)
        if (selection.isEmpty()) {
            failWithElementNotFound(cssSelector)
            return this
        }

        if (!selection.hasAttr(attribute)) {
            failWithAttributeNotFound(attribute, cssSelector, selection)
        }
    }

    public fun elementAttributeNotExists(cssSelector: String, attribute: String): DocumentAssert = apply {
        isNotNull

        elementExists(cssSelector)
        val selection = actual.select(cssSelector)
        if (selection.hasAttr(attribute)) {
            failWithActualExpectedAndMessage(
                selection,
                null,
                "%nExpecting attribute%n" +
                        "  <%s>%n" +
                        "on element for%n" +
                        "  <%s>%n" +
                        "to be absent, but was%n" +
                        "  <%s>",
                attribute,
                cssSelector,
                selection
            )
        }
    }

    public fun elementHasText(cssSelector: String, string: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection == null) {
            failWithElementNotFound(cssSelector)
            return this
        }

        val text = selection.text()
        if (text != string) {
            failWithActualExpectedAndMessage(
                text,
                string,
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to have text%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                cssSelector,
                string,
                text
            )
        }
    }

    public fun elementHasText(cssSelector: String, vararg strings: String): DocumentAssert = apply {
        isNotNull

        if (strings.isEmpty()) {
            throw IllegalArgumentException("elementHasText expects at least two arguments")
        }

        val selection = actual.select(cssSelector)
        if (selection.isEmpty()) {
            failWithElementNotFound(cssSelector)
            return this
        }

        strings.zip(selection).onEachIndexed { index, matchPair ->
            val elementText = matchPair.second.text()
            val expectedText = matchPair.first
            if (elementText != expectedText) {
                failWithActualExpectedAndMessage(
                    elementText,
                    expectedText,
                    "%nExpecting element at position" +
                            " %s " +
                            "in list for%n" +
                            "  <%s>%n" +
                            "to have text%n" +
                            "  <%s>%n" +
                            "but was%n" +
                            "  <%s>",
                    index,
                    cssSelector,
                    expectedText,
                    elementText
                )
                return this
            }
        }

        if (selection.size < strings.size) {
            val rest = strings.drop(selection.size)
            failWithMessage(
                "%nExpecting" +
                        " %s more element(s) for%n" +
                        "  %s%n" +
                        "to be%n" +
                        "%s%n" +
                        "in list%n" +
                        "%s",
                rest.size,
                cssSelector,
                maskSelection(rest),
                maskSelection(selection)
            )
        }
    }

    public fun elementContainsText(cssSelector: String, substring: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection == null) {
            failWithElementNotFound(cssSelector)
            return this
        }

        val text = selection.text()
        if (!text.contains(substring)) {
            failWithActualExpectedAndMessage(
                text,
                substring,
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to contain text%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                cssSelector,
                substring,
                text
            )
        }
    }

    public fun elementMatchesText(cssSelector: String, regex: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection == null) {
            failWithElementNotFound(cssSelector)
            return this
        }

        val text = selection.text()
        if (!text.contains(regex.toRegex())) {
            failWithActualExpectedAndMessage(
                text,
                regex,
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to match regex%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                cssSelector,
                regex,
                text
            )
        }
    }

    public fun elementAttributeHasText(cssSelector: String, attribute: String, text: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.select(cssSelector)
        if (selection.isEmpty()) {
            failWithElementNotFound(cssSelector)
            return this
        }

        if (!selection.hasAttr(attribute)) {
            failWithAttributeNotFound(attribute, cssSelector, selection)
            return this
        }

        val attrValue = selection.attr(attribute)
        if (attrValue != text) {
            failWithActualExpectedAndMessage(
                attrValue,
                text,
                "%nExpecting attribute%n" +
                        "  <%s>%n" +
                        "on element for%n" +
                        "  <%s>%n" +
                        "to be%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                attribute,
                cssSelector,
                text,
                attrValue
            )
        }
    }

    public fun elementAttributeHasText(
        cssSelector: String,
        attribute: String,
        vararg attrValues: String
    ): DocumentAssert = apply {
        isNotNull

        if (attrValues.isEmpty()) {
            throw IllegalArgumentException("elementAttributeHasText expects at least two arguments")
        }

        val selection = actual.select(cssSelector)
        if (selection.isEmpty()) {
            failWithElementNotFound(cssSelector)
            return this
        }

        attrValues.zip(selection).onEachIndexed { index, matchPair ->
            val element = matchPair.second

            // attribute not found
            if (!element.hasAttr(attribute)) {
                failWithAttributeNotFoundAtPosition(attribute, index, cssSelector, element, selection)
                return this
            }

            // attribute value does not match
            val attrValue = element.attr(attribute)
            val expectedAttrValue = matchPair.first
            if (attrValue != expectedAttrValue) {
                failWithAttributeValueAtPositionNotFound(attrValue, expectedAttrValue, index, cssSelector, selection)
                return this
            }
        }

        if (selection.size < attrValues.size) {
            val rest = attrValues.drop(selection.size)
            failWithRemainingElementsNotFound(rest, cssSelector, selection)
        }
    }

    public fun elementAttributeContainsText(
        cssSelector: String,
        attribute: String,
        substring: String
    ): DocumentAssert = apply {
        isNotNull

        val selection = actual.select(cssSelector)
        if (selection.isEmpty()) {
            failWithElementNotFound(cssSelector)
            return this
        }

        if (!selection.hasAttr(attribute)) {
            failWithAttributeNotFound(attribute, cssSelector, selection)
            return this
        }

        val attrValue = selection.attr(attribute)
        if (!attrValue.contains(substring)) {
            failWithActualExpectedAndMessage(
                attrValue,
                substring,
                "%nExpecting attribute%n" +
                        "  <%s>%n" +
                        "on element for%n" +
                        "  <%s>%n" +
                        "to contain%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                attribute,
                cssSelector,
                substring,
                attrValue
            )
        }
    }


    public fun elementHasClass(cssSelector: String, className: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)
        if (selection == null) {
            failWithElementNotFound(cssSelector)
            return this
        }

        if (!selection.hasAttr("class")) {
            failWithAttributeNotFound("class", cssSelector, selection)
            return this
        }

        if (!selection.hasClass(className)) {
            failWithMessage(
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to include class%n" +
                        "  <%s>%n" +
                        "but found%n" +
                        "%s",
                cssSelector,
                className,
                maskSelection(selection)
            )
        }
    }

    public fun elementNotHasClass(cssSelector: String, className: String): DocumentAssert = apply {
        isNotNull

        val selection = actual.selectFirst(cssSelector)

        if (selection == null) {
            failWithElementNotFound(cssSelector)
            return this
        }

        if (!selection.hasAttr("class")) {
            return this
        }

        if (selection.hasClass(className)) {
            failWithMessage(
                "%nExpecting element for%n" +
                        "  <%s>%n" +
                        "to not include class%n" +
                        "  <%s>%n" +
                        "but was%n" +
                        "  <%s>",
                cssSelector,
                className,
                selection
            )
        }
    }


    @Generated
    @Suppress("warnings")
    private inline fun failWithElementNotFound(cssSelector: String) =
        failWithMessage(
            "%nExpecting element for%n" +
                    "  <%s>%n" +
                    "but found nothing",
            cssSelector
        )

    @Generated
    @Suppress("warnings")
    private inline fun failWithAttributeNotFound(attribute: String, cssSelector: String, selections: Elements) =
        failWithMessage(
            "%nExpecting attribute%n" +
                    "  <%s>%n" +
                    "on elements for%n" +
                    "  <%s>%n" +
                    "but found%n" +
                    "%s",
            attribute,
            cssSelector,
            maskSelection(selections)
        )

    @Generated
    @Suppress("warnings")
    private inline fun failWithAttributeNotFound(attribute: String, cssSelector: String, selection: Element) =
        failWithMessage(
            "%nExpecting attribute%n" +
                    "  <%s>%n" +
                    "on element for%n" +
                    "  <%s>%n" +
                    "but found%n" +
                    "  <%s>",
            attribute,
            cssSelector,
            selection
        )

    @Generated
    @Suppress("warnings")
    private inline fun failWithAttributeNotFoundAtPosition(
        attribute: String,
        index: Int,
        cssSelector: String,
        element: Element,
        selection: Elements
    ) {
        failWithActualExpectedAndMessage(
            null,
            attribute,
            "%nExpecting element at position" +
                    " %s " +
                    "in list for%n" +
                    "  <%s>%n" +
                    "to have attribute%n" +
                    "  <%s>%n" +
                    "but was%n" +
                    "  <%s>%n" +
                    "in list%n" +
                    "%s",
            index,
            cssSelector,
            attribute,
            element,
            maskSelection(selection)
        )
    }

    @Generated
    @Suppress("warnings")
    private inline fun failWithAttributeValueAtPositionNotFound(
        attrValue: String,
        expectedAttrValue: String,
        index: Int,
        cssSelector: String,
        selection: Elements
    ) {
        failWithActualExpectedAndMessage(
            attrValue,
            expectedAttrValue,
            "%nExpecting element at position" +
                    " %s " +
                    "in list for%n" +
                    "  <%s>%n" +
                    "to have attribute value%n" +
                    "  <%s>%n" +
                    "but was%n" +
                    "  <%s>%n" +
                    "in list%n" +
                    "%s",
            index,
            cssSelector,
            expectedAttrValue,
            attrValue,
            maskSelection(selection)
        )
    }

    @Generated
    @Suppress("warnings")
    private inline fun failWithRemainingElementsNotFound(
        rest: List<String>,
        cssSelector: String,
        selection: Elements
    ) {
        failWithMessage(
            "%nExpecting" +
                    " %s remaining elements:%n" +
                    "  <%s>%n" +
                    "in list for%n" +
                    "  <%s>%n" +
                    "but was%n" +
                    "  <%s>",
            rest.size,
            cssSelector,
            rest,
            maskSelection(selection)
        )
    }


    private companion object {
        private fun maskSelection(selection: Elements) = selection.toString().prependIndent("  ")

        private fun maskSelection(selection: Element) = selection.toString().prependIndent("  ")

        private fun maskSelection(selection: List<String>) = selection.toString().prependIndent("  ")
    }
}
