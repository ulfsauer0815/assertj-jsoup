import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"

    `java-library`
    `maven-publish`

    id("io.gitlab.arturbosch.detekt") version "1.14.2"
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.4"

}

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

group = "com.github.ulfs"
version = "0.0.1-SNAPSHOT"


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

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}
