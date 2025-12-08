plugins {
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
}

group = "com.bettr.deployments"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))
    implementation(projects.bettrCore.adapters.http)
    implementation(projects.bettrCore.adapters.inmemory)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.micrometer.core)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.core)
    implementation(libs.spring.boot.starter.log4j2)
    implementation(libs.spring.boot.starter.webflux)

    runtimeOnly(libs.disruptor)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotest.extensions.spring)
}

tasks {
    jar {
        archiveBaseName.set(project.name)
    }

    bootJar {
        archiveVersion.set("")
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }
}

configurations.all {
    exclude(module = "spring-boot-starter-logging")
}
