package io.github.ulfs.assertj.jsoup.test

import org.assertj.core.api.AbstractThrowableAssert
import org.assertj.core.api.SoftAssertionError

fun AbstractThrowableAssert<*, *>.hasOneError(): AbstractThrowableAssert<*, *> =
    matches({
        val errors = (it as SoftAssertionError).errors
        if (errors.size != 1) {
            return@matches false
        }
        return@matches true
    }, "has exactly one element")

fun AbstractThrowableAssert<*, *>.hasErrorWithMessage(message: String): AbstractThrowableAssert<*, *> =
    matches({
        val errors = (it as SoftAssertionError).errors
        val errorMessage = errors.get(0)
        return@matches errorMessage.startsWith(message)
    }, "has error message starting with message")
