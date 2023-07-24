import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    extra["detektVersion"] = "1.23.0"
}

plugins {
    val detektVersion: String by project

    kotlin("jvm") version "1.8.22"
    `java-library`

    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("pl.allegro.tech.build.axion-release") version "1.15.3"

    // code analysis
    id("io.gitlab.arturbosch.detekt") version detektVersion

    // API compatibility
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"

    // code coverage
    jacoco
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.16"
}

group = "io.github.ulfs"


repositories {
    mavenCentral()
    // required for io.gitlab.arturbosch.detekt:detekt-report-html
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = VERSION_1_8.toString()
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = VERSION_1_8.toString()
    }
}

java {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

kotlin {
    explicitApi()
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.assertj:assertj-core:3.24.2")
    implementation("org.jsoup:jsoup:1.15.3")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.opentest4j:opentest4j:1.3.0")
    val detektVersion: String by project
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:$detektVersion")
}

publishing {
    publications {
        create<MavenPublication>(rootProject.name) {
            from(components["java"])

            pom {
                name.set("$group:${rootProject.name}")
                description.set("AssertJ assertions for HTML")
                url.set("https://github.com/ulfsauer0815/assertj-jsoup")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/ulfsauer0815/assertj-jsoup/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        name.set("Ulf Sauer")
                        url.set("https://github.com/ulfsauer0815")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ulfsauer0815/assertj-jsoup.git")
                    developerConnection.set("scm:git:ssh://github.com:ulfsauer0815/assertj-jsoup.git")
                    url.set("https://github.com/ulfsauer0815/assertj-jsoup")
                }
            }
        }
    }
}

signing {
    isRequired = project.hasProperty("requiredSigning")
    sign(publishing.publications[rootProject.name])
}

scmVersion {
    localOnly.set(true)
    with(tag) {
        prefix.set("v")
    }
}

// must be below scmVersion config!
version = scmVersion.version

nexusPublishing {
    repositories {
        create("sonatype") {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(project.properties["sonatypeUsername"] as String?) // default
            password.set(project.properties["sonatypePassword"] as String?) // default
        }
    }
}

detekt {
    toolVersion = "1.22.0"
    source = files(DEFAULT_SRC_DIR_KOTLIN)
    buildUponDefaultConfig = true
    config = files("$projectDir/config/detekt/detekt.yml")
    baseline = file("$projectDir/config/detekt/baseline.xml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    reports {
        xml {
            required.set(true)
            outputLocation.set(file("$projectDir/build/test-results/detekt/detekt.xml"))
        }
        html {
            required.set(true)
            outputLocation.set(file("$projectDir/build/reports/detekt/detekt.html"))
        }
        txt {
            required.set(true)
            outputLocation.set(file("$projectDir/build/reports/detekt/detekt.txt"))
        }
    }
}

tasks {
    named("check") {
        // might be obsolete once detekt 2.x is released
        dependsOn("detektMain")
    }
    named("coverallsJacoco") {
        dependsOn("jacocoTestReport")
    }
}

apiValidation {
    validationDisabled = !hasProperty("checkApi")
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
