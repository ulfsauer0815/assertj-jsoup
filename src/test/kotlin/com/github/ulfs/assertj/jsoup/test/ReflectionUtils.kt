package com.github.ulfs.assertj.jsoup.test

import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


class ReflectionUtils {

    companion object {
        @Suppress("UNCHECKED_CAST")
        internal fun <T> callGetter(documentAssert: Any, name: String): T {
            val any = documentAssert.javaClass.kotlin
                .memberProperties
                .filter { it.name == name }
                .onEach { it.isAccessible = true }
                .map { it.getter.call(documentAssert) }
                .first()
            return any as T
        }
    }
}
