plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

//val artifactId = "annotation"
//val kgwenGroupId: String by project
//
//apply(plugin = "maven-publish")
//
//publishing {
//    publications {
//        bar(MavenPublication) {
//            groupId group_id
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
//
//dependencies {
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//}
