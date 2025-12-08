subprojects {
    group = "com.bettr.deployments"

    tasks {
        withType<Jar> {
            archiveBaseName.set(project.name)
        }
    }
}
