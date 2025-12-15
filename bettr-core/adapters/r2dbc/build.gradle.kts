group = "com.bettr.adapters.r2dbc"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    api(projects.bettrCore.domain)
    implementation(libs.kotlin.logging)
    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(libs.r2dbc.pool)
    implementation(libs.r2dbc.postgresql)
    implementation(libs.slf4j.api)
    implementation(libs.kotlinx.coroutines.slf4j)

    testImplementation(libs.slf4j.simple)
    testRuntimeOnly(libs.postgresql.jdbc.driver)
    testRuntimeOnly(libs.kotlinx.coroutines.reactor)
}
