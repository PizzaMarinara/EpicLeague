val accompanistVersion by extra { "0.24.1-alpha" }
val composeCompilerVersion by extra { "1.2.0-alpha02" }
val composeVersion by extra { "1.2.0-alpha02" }
val coreTestingVersion by extra { "2.1.0" }
val coroutinesTestVersion by extra { "1.6.0" }
val gradlePluginVersion: String by rootProject.extra
val hiltComposeVersion by extra { "1.0.0-rc01" }
val hiltVersion: String by rootProject.extra
val junitVersion by extra { "4.13.2" }
val junitAndroidxVersion by extra { "1.1.3" }
val kotlinVersion: String by rootProject.extra
val materialVersion by extra { "1.0.0-alpha02" }
val maximumWeightedMatching by extra { "0.1.5" }
val moshiVersion by extra { "1.13.0" }
val navigationVersion by extra { "2.4.0" }
val objectboxVersion: String by rootProject.extra
val retrofitVersion by extra { "2.9.0" }

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {

    defaultConfig {
        applicationId = "dev.efantini.epicleague"
        compileSdk = 31
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.core:core-splashscreen:1.0.0-beta01")

    debugImplementation("io.objectbox:objectbox-android-objectbrowser:$objectboxVersion")
    releaseImplementation("io.objectbox:objectbox-android:$objectboxVersion")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeVersion")

    // Maximum Weight Matching library
    implementation("com.github.PizzaMarinara:MaximumWeightedMatching:$maximumWeightedMatching")

    // Tests
    testImplementation("junit:junit:$junitVersion")
    implementation("androidx.test.ext:junit-ktx:$junitAndroidxVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitAndroidxVersion")
    testImplementation("androidx.arch.core:core-testing:$coreTestingVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    testImplementation("io.objectbox:objectbox-windows:$objectboxVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTestVersion")
}

apply(plugin = "io.objectbox")
