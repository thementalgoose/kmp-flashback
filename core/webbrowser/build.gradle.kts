import org.jetbrains.compose.compose
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
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-Xstring-concat=inline")
        }
    }
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core)
            implementation(libs.androidx.browser)
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.koin.compose)

            implementation(compose.components.resources)

            implementation(projects.core.preferences)
            implementation(projects.infrastructure)
            implementation(projects.presentation.style)
            implementation(projects.presentation.localisation)
        }
        commonTest.dependencies {
            implementation(libs.turbine)
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
