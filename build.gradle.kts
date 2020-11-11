
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.71"

    `java-library`
}

java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
