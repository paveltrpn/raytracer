plugins {
    application
    kotlin("jvm") version "2.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    // implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk21")
    testImplementation(kotlin("test"))
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

