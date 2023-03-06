plugins {
    id("java-library")
    id("kotlin")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("AnnotationPublication") {
            val kgwenGroupId: String by project
            groupId = kgwenGroupId
            artifactId = project.name
            from(components.findByName("java"))
        }
    }
}
