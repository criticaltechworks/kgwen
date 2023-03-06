@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

tasks.create<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
}

val kgwenGroupId: String by project
val kgwenVersion: String by project
val kgwenMinSdk: String by project
val kgwenCompileSdk: String by project
val kgwenTargetSdk: String by project
val kgewnJvmTarget: String by project
val kotlinVersion: String by project
val composeCompilerVersion: String by project
val composeVersion: String by project
val navVersion: String by project

publishing {
    publications {
        create<MavenPublication>("KgwenPublication") {
            groupId = kgwenGroupId
            artifactId = "library"
            artifact("$buildDir/outputs/aar/$artifactId-release.aar")
            artifact(tasks.findByName("sourcesJar"))
        }
    }

//    publications {
//        bar(MavenPublication) {
//            groupId group_id // Replace with group ID
//            artifactId artifact_id
//            version kgwen_version
//            artifact(sourceJar)
//            artifact("$buildDir/outputs/aar/$artifact_id-release.aar")
//        }
//    }
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
}

android {
    namespace = kgwenGroupId

    compileSdk = kgwenCompileSdk.toInt()

    defaultConfig {
        minSdk = kgwenMinSdk.toInt()
        targetSdk = kgwenTargetSdk.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = kgewnJvmTarget
    }

//    publishing {
//        singleVariant("release") {
//            withSourcesJar()
//        }
//    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn",)
    }
}

dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.5.0")

    implementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
