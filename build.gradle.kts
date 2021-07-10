import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    `java-library`

    // publishing
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("pl.allegro.tech.build.axion-release") version "1.11.0"

    // code analysis
    id("io.gitlab.arturbosch.detekt") version "1.14.2"

    // API compatibility
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.6.0"

    // code coverage
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.13"
}

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

group = "com.github.ulfs"


repositories {
    jcenter()
    mavenCentral()
    google()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

kotlin {
    explicitApi()
}

java {
    withSourcesJar()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.assertj:assertj-core:3.18.1")
    implementation("org.jsoup:jsoup:1.13.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
}

publishing {
    publications {
        create<MavenPublication>(rootProject.name) {
            from(components["java"])
        }
    }
}

scmVersion {
    localOnly = true
    with(tag) {
        prefix = "v"
        versionSeparator = ""
    }
}

// must be below scmVersion config!
version = scmVersion.version

// must be below scmVersion config!
bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    publish = false

    with(pkg) {
        repo = project.name
        name = project.name
        setLicenses("MIT")
        vcsUrl = "https://github.com/ulfsauer0815/assertj-jsoup.git"
        issueTrackerUrl = "https://github.com/ulfsauer0815/assertj-jsoup/issues"

        with(version) {
            name = project.version as String
            vcsTag = "v${project.version}"
        }

        githubRepo = "ulfsauer0815/assertj-jsoup"
    }

    setPublications(project.name)
}

detekt {
    toolVersion = "1.14.2"
    input = files(io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN)
    config = files("$projectDir/config/detekt/detekt.yml")
    reports {
        xml {
            enabled = true
            destination = file("$projectDir/build/test-results/detekt/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$projectDir/build/reports/detekt/detekt.html")
        }
        txt {
            enabled = true
            destination = file("$projectDir/build/reports/detekt/detekt.txt")
        }
    }
    baseline = file("$projectDir/config/detekt/baseline.xml")
}

apiValidation {
    validationDisabled = !hasProperty("checkApi")
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}
