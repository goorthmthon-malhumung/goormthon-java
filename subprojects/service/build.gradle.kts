plugins {
    kotlin("jvm")
}
dependencies {
    api(project(":domain"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}