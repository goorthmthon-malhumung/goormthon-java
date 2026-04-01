val queryDslVersion = "5.0.0"

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    runtimeOnly("com.h2database:h2")
    implementation("mysql:mysql-connector-java:8.0.33")
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