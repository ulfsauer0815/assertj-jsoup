package com.github.ulfs.test.coverage

import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Used to ignore functions in coverage reports
 */
@Retention(RUNTIME)
internal annotation class Generated {
}
