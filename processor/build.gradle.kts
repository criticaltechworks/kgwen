plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("maven-publish")
}

//def artifact_id = 'processor'
//def group_id = 'com.criticaltechworks.kgwen'
//
//apply plugin: 'maven-publish'
//
//publishing {
//    publications {
//        bar(MavenPublication) {
//            groupId group_id // Replace with group ID
//            artifactId artifact_id
//            version kgwenVersion
//            from(components.java)
//        }
//    }
//
//    repositories {
//        maven {
//            name = "GitHubPackages"
//            url = uri("https://maven.pkg.github.com/criticaltechworks/kgwen")
//            credentials {
//                username = System.getenv("GPR_USER")
//                password = System.getenv("GPR_API_KEY")
//            }
//        }
//    }
//}

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
