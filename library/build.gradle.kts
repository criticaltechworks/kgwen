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
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("LibraryPublication") {
                val kgwenGroupId: String by project
                groupId = kgwenGroupId
                artifactId = "library"
                artifact(tasks.getByName("bundleReleaseAar"))
                artifact(tasks.getByName("sourcesJar"))
            }
        }
    }
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
