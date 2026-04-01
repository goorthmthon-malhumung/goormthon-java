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