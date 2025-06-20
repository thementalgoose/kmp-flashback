import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.androidLibrary)
}

kotlin {
    sourceSets {
        androidTarget {
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    buildFeatures {
        buildConfig = true
    }
}