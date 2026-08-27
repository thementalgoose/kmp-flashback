import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.featureModule)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.coil)

            implementation(libs.compose.resources)

            implementation(projects.domain.formula1)
            implementation(projects.core.configuration)
            implementation(projects.core.device)
            implementation(projects.core.notifications)
            implementation(projects.core.metrics.analytics)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.infrastructure)
            implementation(projects.presentation.ui)
            implementation(projects.presentation.style)
            implementation(projects.presentation.navigation)
            implementation(projects.presentation.localisation)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
