plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
}

group = "com.bettr.adapters.nosql"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    api(projects.bettrCore.domain)

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.slf4j.simple)
}
