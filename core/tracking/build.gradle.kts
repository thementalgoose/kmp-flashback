import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
//    alias(libs.plugins.kotlinCocoapods)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(projects.infrastructure)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {
        }
    }
}
