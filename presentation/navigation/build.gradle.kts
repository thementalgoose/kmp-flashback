import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    android {
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt())
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3AdaptiveNavigationSuite)
        }
    }
}