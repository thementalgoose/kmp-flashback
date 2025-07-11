import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.featureModule)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.koin.compose)

            implementation(projects.core.configuration)
            implementation(projects.core.metrics.analytics)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.core.device)
            implementation(projects.infrastructure)
            implementation(projects.presentation.ui)
            implementation(projects.presentation.style)
            implementation(projects.presentation.navigation)
            implementation(projects.presentation.localisation)
            implementation(libs.ksoup)
        }
        commonTest.dependencies {
            implementation(projects.test.formula1)
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
