rootProject.name = "bettr"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "bettr-core:domain",
    "bettr-core:adapters:http",
    "bettr-core:adapters:inmemory",
    "bettr-core:adapters:r2dbc",
    "bettr-core:adapters:flyway",
    "bettr-core:application",
    "bettr-deployments:bettr-api"
)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
