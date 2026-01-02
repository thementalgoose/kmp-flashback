import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mokkery)
}

kotlin {
    android {
        compileSdk {
            version = release(libs.versions.android.compileSdk.get().toInt())
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)
            implementation(libs.bundles.androidx.xr)
        }
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(compose.materialIconsExtended)
            @OptIn(ExperimentalComposeLibrary::class)
            api(compose.uiTest)

            implementation(projects.infrastructure)
            implementation(projects.presentation.localisation)
            implementation(projects.core.preferences)
            implementation(projects.presentation.xr)

            implementation(libs.bundles.koin.compose)
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
    "androidRuntimeClasspath"(libs.androidx.ui.tooling)
}
