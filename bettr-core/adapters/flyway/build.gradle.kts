group = "com.bettr.adapters.flyway"

plugins {
    id("org.flywaydb.flyway") version libs.versions.flyway
}

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))
    implementation(libs.flyway.core)
    implementation(libs.kotest.framework.api)
    runtimeOnly(libs.postgresql.jdbc.driver)
}

flyway {
    val dbUrl = System.getenv("DATABASE_URL")?.let { "jdbc:${it.replace("postgres://", "postgresql://")}" }
        ?: "jdbc:postgresql://localhost:5432/bettr"
    val dbUser = System.getenv("PGUSER") ?: "bettr"
    val dbPassword = System.getenv("PGPASSWORD") ?: "bettr"
    
    url = dbUrl
    user = dbUser
    password = dbPassword
    schemas = arrayOf("bettr")
    initSql = "CREATE SCHEMA IF NOT EXISTS bettr"
}
