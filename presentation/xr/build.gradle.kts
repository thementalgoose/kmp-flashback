import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.bundles.androidx.xr)
            compileOnly(libs.androidx.xr.extensions)
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(compose.components.resources)
            implementation(compose.foundation)
            implementation(compose.material3AdaptiveNavigationSuite)
        }
    }
}