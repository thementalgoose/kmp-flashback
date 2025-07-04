import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.featureModule)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
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
            implementation(libs.bundles.common.ktor)
            implementation(libs.ktor.serialization.kotlinx.xml)
            implementation(compose.components.resources)

            implementation(projects.core.configuration)
            implementation(projects.core.device)
            implementation(projects.core.metrics.analytics)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.core.preferences)
            implementation(projects.core.webbrowser)

            implementation(projects.data.network.rss)

            implementation(projects.infrastructure)
            implementation(projects.presentation.ui)
            implementation(projects.presentation.style)
            implementation(projects.presentation.navigation)
            implementation(projects.presentation.localisation)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.turbine)
        }
        iosMain.dependencies {

        }
    }
}
