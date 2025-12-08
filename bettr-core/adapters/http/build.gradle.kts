plugins {
    kotlin("plugin.serialization") version "1.9.10"
}

group = "com.bettr.adapters"

configurations.all {
    exclude(module = "spring-boot-starter-logging")
}

dependencies {
    implementation(platform(libs.spring.boot.bom))

    api(projects.bettrCore.application)

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlinx.serialization.json)
}
