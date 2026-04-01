pluginManagement {
    plugins {
        kotlin("jvm") version "2.3.20"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "hackathon"

include(
    "domain",
    "service",
    "ui:api"
)

makeProjectDir(rootProject, "subprojects")

fun makeProjectDir(project: ProjectDescriptor, group: String) {
    project.children.forEach {
        it.projectDir = file("$group/${it.name}")
        makeProjectDir(it, "$group/${it.name}")
    }
}
