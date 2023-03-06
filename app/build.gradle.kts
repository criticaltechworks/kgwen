@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

val kgwenVersion: String by project
val kgwenMinSdk: String by project
val kgwenCompileSdk: String by project
val kgwenTargetSdk: String by project
val kgewnJvmTarget: String by project
val composeCompilerVersion: String by project
val composeVersion: String by project
val navVersion: String by project

android {
    namespace = "com.criticaltechworks.kgwen.demo"

    compileSdk = kgwenCompileSdk.toInt()

    defaultConfig {
        applicationId = "com.criticaltechworks.kgwen.demo"
        minSdk = kgwenMinSdk.toInt()
        targetSdk = kgwenTargetSdk.toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }

    buildFeatures {
        viewBinding = true
        compose = true
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

    packagingOptions {
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/AL2.0")
    }
}

//sourceSets {
//    androidTest {
//        java.srcDirs = ['src/androidTest/src']
//        resources.srcDirs = ['src/main/res']
//    }
//}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Compose
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("com.google.android.material:compose-theme-adapter:1.1.6")
    implementation("androidx.navigation:navigation-compose:2.4.2")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")

    // ViewPager Indicators
    implementation("com.tbuonomo:dotsindicator:4.2")

    // Unit testing
    testImplementation("junit:junit:4.13.2")

    // Instrumented testing
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation("com.adevinta.android:barista:4.2.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

    androidTestImplementation("com.criticaltechworks.kgwen:library:$kgwenVersion")
    androidTestImplementation("com.criticaltechworks.kgwen:annotation:$kgwenVersion")
    kaptAndroidTest("com.criticaltechworks.kgwen:processor:$kgwenVersion")
}
