dependencies {
    api(project(":domain"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks {
    bootJar {
        enabled = false
    }

    jar {
        enabled = true
    }
}