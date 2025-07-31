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
            freeCompilerArgs.add("-Xstring-concat=inline")
        }
    }
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.bundles.compose)

            implementation(compose.components.resources)

            implementation(projects.core.configuration)
            implementation(projects.core.device)
            implementation(projects.core.metrics.analytics)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.core.notifications)
            implementation(projects.core.preferences)

            implementation(projects.domain.formula1)

            implementation(projects.data.flashback)

            implementation(projects.infrastructure)

            implementation(projects.presentation.navigation)
            implementation(projects.presentation.localisation)
            implementation(projects.presentation.style)
            implementation(projects.presentation.ui)
        }
        commonTest.dependencies {
            implementation(projects.test.formula1)
            implementation(kotlin("test"))
            implementation(libs.turbine)
        }
        iosMain.dependencies {

        }
    }
}
