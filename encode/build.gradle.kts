plugins {
    application
    kotlin("jvm") version "2.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okio:okio:3.15.0")
    implementation("com.github.ajalt.clikt:clikt:5.0.3")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        // optIn.add("kotlin.RequiresOptIn")
    }
}

application {
    mainClass = "encode.MainKt"
}

