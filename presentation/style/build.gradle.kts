plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            api(libs.androidx.activity.compose)
            implementation(libs.bundles.androidx.xr)
        }
        commonMain.dependencies {
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