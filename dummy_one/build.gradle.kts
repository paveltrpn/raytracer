plugins {
    application
    kotlin("jvm") version "2.2.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":modules"))
}

tasks.test {
    useJUnitPlatform()
}

//kotlin {
//    compilerOptions {
//        jvmTarget = JvmTarget.fromTarget("17")
//        languageVersion = KotlinVersion.fromVersion("2.1")
//        apiVersion = KotlinVersion.fromVersion("2.1")
//    }
//}

application {
    mainClass = "test_one.MainKt"
}
