# AssertJ assertions for HTML
![build](https://github.com/ulfsauer0815/assertj-jsoup/workflows/build/badge.svg)
[![Maven](https://img.shields.io/maven-central/v/io.github.ulfs/assertj-jsoup?color=blue)](https://mvnrepository.com/artifact/io.github.ulfs/assertj-jsoup)
[![JitPack](https://jitpack.io/v/ulfsauer0815/assertj-jsoup.svg)](https://jitpack.io/#ulfsauer0815/assertj-jsoup)
[![coveralls](https://coveralls.io/repos/github/ulfsauer0815/assertj-jsoup/badge.svg)](https://coveralls.io/github/ulfsauer0815/assertj-jsoup)
[![codecov](https://codecov.io/gh/ulfsauer0815/assertj-jsoup/branch/main/graph/badge.svg)](https://codecov.io/gh/ulfsauer0815/assertj-jsoup)

Provides assertions for HTML content including a Kotlin DSL.
Uses [jsoup](https://jsoup.org/) for parsing.

## Example

### Kotlin DSL
```kotlin
assertThatSpec(document) {

    node("h1") { exists() }
    
    node(".example-code") { containsText("Kotlin") }
    
    node(".github-link") {
        hasText("Fork me on GitHub")
        attribute("href") {
            containsText("github")
            containsText("assertj-jsoup")
        }
        attribute("target") { hasText("_blank") }
        attribute("disabled") { notExists() }
    }
}
```

### Java / Kotlin
```kotlin
assertThat(document)
    .elementExists("h1")
    
    .elementContainsText(".example-code", "Kotlin")

    .elementHasText(".github-link", "Fork me on GitHub")
    .elementAttributeContainsText(".github-link", "href", "github")
    .elementAttributeContainsText(".github-link", "href", "assertj-jsoup")
    .elementAttributeHasText(".github-link", "target", "_blank")
    .elementAttributeNotExists(".github-link", "disabled");
```

## Build configuration

### Kotlin

*`build.gradle.kts`*
```kotlin
repositories {
    mavenCentral()
}

testImplementation("io.github.ulfs:assertj-jsoup:0.1.4")
```

### Gradle

*`build.gradle`*
```groovy
repositories {
    mavenCentral()
}

testImplementation "io.github.ulfs:assertj-jsoup:0.1.4"
```


## Development

see [DEVELOPMENT.md](DEVELOPMENT.md)
