group = "com.bettr.application"

dependencies {
    api(projects.bettrCore.domain)
    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.api)
    implementation(libs.kotlinx.coroutines.slf4j)

    testImplementation(libs.slf4j.simple)
}
