import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-Xstring-concat=inline")
        }
    }
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.remoteconfig)
            implementation(libs.firebase.messaging)
        }
        commonMain.dependencies {
            implementation(libs.bundles.compose)
            implementation(libs.bundles.kotlin)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.core.preferences)
            implementation(projects.infrastructure)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
