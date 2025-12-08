group = "com.bettr.domain"

dependencies {
    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.api)
    implementation(libs.valiktor.core)
    implementation(libs.valiktor.javatime)
    implementation(libs.valiktor.javamoney)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.slf4j.simple)
}
