plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("ProcessorPublication") {
            val kgwenGroupId: String by project
            groupId = kgwenGroupId
            artifactId = project.name
            from(components.findByName("java"))
        }
    }
}

dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation(project(":annotation"))

    implementation("com.squareup:kotlinpoet:1.12.0-SNAPSHOT")

    //noinspection AnnotationProcessorOnCompilePath
    implementation("com.google.auto.service:auto-service:1.0.1")

    kapt("com.google.auto.service:auto-service:1.0.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn",)
    }
}
