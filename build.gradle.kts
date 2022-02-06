buildscript {
    val gradlePluginVersion by extra { "7.1.0" }
    val hiltVersion by extra { "2.40.5" }
    val kotlinVersion by extra { "1.6.10" }
    val objectboxVersion by extra { "3.1.1" }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$gradlePluginVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("io.objectbox:objectbox-gradle-plugin:$objectboxVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
