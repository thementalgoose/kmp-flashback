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
            implementation(libs.bundles.androidx.glance)
            implementation(projects.domain.formula1)
            implementation(projects.presentation.localisation)
            implementation(libs.android.flagkit)
        }
        commonMain.dependencies {
            implementation(libs.bundles.compose)
            implementation(compose.components.resources)
            implementation(libs.bundles.kotlin)
            implementation(projects.data.flashback)
            implementation(projects.infrastructure)
            implementation(projects.core.preferences)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {

        }
    }
}
