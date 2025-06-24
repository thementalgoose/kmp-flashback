import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mokkery)
}

compose.resources {
    publicResClass = true
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
            api(compose.preview)
            api(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.flagkit)

            implementation(compose.components.resources)
            implementation(libs.bundles.compose.adaptive)
            implementation(libs.bundles.coil)

            implementation(projects.core.preferences)

            implementation(projects.presentation.style)
            implementation(projects.presentation.localisation)

            implementation(projects.infrastructure)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        desktopMain.dependencies {
            api(compose.desktop.currentOs)
            api(libs.kotlinx.coroutines.swing)
        }
    }
}
dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}
