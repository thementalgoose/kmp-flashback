import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mokkery)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.add("-Xstring-concat=inline")
        }
    }
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.infrastructure)
            implementation(projects.core.preferences)
            implementation(projects.core.configuration)
            implementation(projects.presentation.localisation)
            implementation(libs.bundles.koin.compose)
            implementation(libs.bundles.compose)
            implementation(compose.components.resources)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
