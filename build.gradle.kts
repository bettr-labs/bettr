import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.test.logger)
    alias(libs.plugins.jacoco.log)
    id("jacoco")
}

group = "com.bettr"
version = "0.0.1"

val Project.fullName: String get() = (parent?.fullName?.plus("-") ?: "") + name

subprojects {
    if (file("src/main/kotlin").isDirectory || file("src/main/resources").isDirectory) {
        apply {
            plugin("org.jetbrains.kotlin.jvm")
            plugin("org.jmailen.kotlinter")
            plugin("com.adarshr.test-logger")
            plugin("jacoco")
        }

        dependencies {
            implementation(platform(rootProject.libs.log4j.bom))

            implementation(rootProject.libs.kotlin.stdlib)
            implementation(rootProject.libs.kotlin.reflect)
            implementation(rootProject.libs.kotlinx.datetime)
            implementation(rootProject.libs.kotlinx.coroutines.core)
            implementation(rootProject.libs.kotlinx.collections.immutable)
            implementation(rootProject.libs.javax.money.api)

            runtimeOnly(rootProject.libs.javax.money.moneta)

            testImplementation(rootProject.libs.kotest.runner.junit5)
            testImplementation(rootProject.libs.kotest.assertions.core)
            testRuntimeOnly(rootProject.libs.javax.money.moneta)

            constraints {
                setOf(
                    rootProject.libs.netty.handler,
                    rootProject.libs.netty.http2,
                    rootProject.libs.json.smart
                ).forEach { dependency ->
                    api(dependency)
                    implementation(dependency)
                    runtimeOnly(dependency)
                }
            }
        }

        configurations.all {
            exclude(group = "org.mockito")
            exclude(group = "javax.validation")
            exclude(module = "hibernate-validator")
            exclude(module = "jakarta.validation-api")
        }

        tasks {
            jar {
                archiveBaseName.set(project.fullName.replaceFirst("${rootProject.name}-", ""))
                archiveVersion.set("")
            }

            compileKotlin {
                compilerOptions {
                    apiVersion.set(KotlinVersion.fromVersion(rootProject.libs.versions.compiler.kotlin.get()))
                    languageVersion.set(KotlinVersion.fromVersion(rootProject.libs.versions.compiler.kotlin.get()))
                    jvmTarget.set(JvmTarget.fromTarget(rootProject.libs.versions.compiler.java.get()))
                    freeCompilerArgs.addAll(
                        "-Xjdk-release=${rootProject.libs.versions.compiler.java.get()}",
                        "-java-parameters",
                        "-Xjsr305=strict",
                        "-Xjvm-default=all",
                        "-opt-in=kotlin.time.ExperimentalTime"
                    )
                }
            }

            compileTestKotlin {
                compilerOptions {
                    apiVersion.set(KotlinVersion.fromVersion(rootProject.libs.versions.compiler.kotlin.get()))
                    languageVersion.set(KotlinVersion.fromVersion(rootProject.libs.versions.compiler.kotlin.get()))
                    jvmTarget.set(JvmTarget.fromTarget(rootProject.libs.versions.compiler.java.get()))
                    freeCompilerArgs.addAll(
                        "-Xjdk-release=${rootProject.libs.versions.compiler.java.get()}",
                        "-java-parameters",
                        "-Xjsr305=strict",
                        "-Xjvm-default=all",
                        "-opt-in=kotlin.time.ExperimentalTime"
                    )
                }
            }

            test {
                useJUnitPlatform()
                jvmArgs(
                    "--add-opens=java.base/java.lang=ALL-UNNAMED",
                    "--add-opens=java.base/java.util=ALL-UNNAMED"
                )
            }

            lintKotlinMain {
                setSource("src/main/kotlin")
            }

            lintKotlinTest {
                setSource("src/test/kotlin")
            }

            jacocoTestReport {
                reports {
                    xml.required.set(true)
                    html.required.set(true)
                }
                classDirectories.setFrom(files(classDirectories.files.map {
                    fileTree(it).apply {
                        exclude("**/**\$beans$**")
                        exclude("**/**\$LOGGER$**")
                        exclude("**/**\$\$serializer**")
                    }
                }))
            }

            jacocoTestCoverageVerification {
                dependsOn(jacocoTestReport)

                classDirectories.setFrom(files(classDirectories.files.map {
                    fileTree(it).apply {
                        exclude("**/**\$beans$**")
                        exclude("**/**\$LOGGER$**")
                        exclude("**/**\$\$serializer**")
                    }
                }))
            }

            check {
                dependsOn(jacocoTestCoverageVerification)
            }
            register<DependencyReportTask>("allDeps")
        }
    }
}

val jacocoAggregatedReport = tasks.named<JacocoReport>("jacocoAggregatedReport") {
    group = "verification"
    description = "Gera relatório de cobertura XML agregado em jacocoAggregatedReport.xml na pasta reports/jacoco/jacocoAggregatedReport."

    reports {
        xml.required.set(true)
        html.required.set(false)
        xml.outputLocation.set(file("$rootDir/build/reports/jacoco/test/jacocoTestReport.xml"))
    }
}

val coberturaReport = tasks.register<Copy>("coberturaReport") {
    group = "verification"
    description = "Copia o relatório de cobertura XML agregado para /build/cobertura/cobertura.xml"
    val sourceFile = file("$rootDir/build/reports/jacoco/test/jacocoTestReport.xml")
    val destinationDir = file("$rootDir/build/reports/cobertura")

    from(sourceFile)
    into(destinationDir)
    rename { "cobertura.xml" }

    dependsOn("jacocoAggregatedReport")
}

tasks.named("build") {
    dependsOn(jacocoAggregatedReport)
}
tasks.named("test") {
    finalizedBy(jacocoAggregatedReport)
}

tasks.named("jacocoAggregatedReport") {
    finalizedBy(coberturaReport)
}

