package io.github.ulfs.assertj.jsoup.test

import org.assertj.core.api.AbstractThrowableAssert
import org.assertj.core.error.AssertJMultipleFailuresError

fun AbstractThrowableAssert<*, *>.hasOneError(): AbstractThrowableAssert<*, *> =
    matches({
        val errors = (it as AssertJMultipleFailuresError).failures
        if (errors.size != 1) {
            return@matches false
        }
        return@matches true
    }, "has exactly one element")

fun AbstractThrowableAssert<*, *>.hasErrorWithMessage(message: String): AbstractThrowableAssert<*, *> =
    matches({
        val errors = (it as AssertJMultipleFailuresError).failures
        val errorMessage = errors[0].message
        return@matches errorMessage?.startsWith(message) ?: false
    }, "has error message starting with message")
