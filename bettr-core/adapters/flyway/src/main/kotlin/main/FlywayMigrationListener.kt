package main

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan
import org.flywaydb.core.Flyway

@AutoScan
object FlywayMigrationListener : ProjectListener {
    override suspend fun beforeProject() {
        Flyway.configure()
            .dataSource(
                "jdbc:postgresql://localhost:5434/bettr",
                "bettr",
                "bettr",
            )
            .locations("db/migration/sql")
            .load()
            .migrate()
    }
}
