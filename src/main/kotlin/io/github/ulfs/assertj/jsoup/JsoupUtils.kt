package io.github.ulfs.assertj.jsoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

internal object JsoupUtils {

    internal fun parse(string: String): Document = Jsoup.parse(string)

}
