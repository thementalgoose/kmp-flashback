plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.mokkery)
}

compose.resources {
    publicResClass = true
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            api(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.flagkit)

            implementation(libs.compose.resources)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.koin.compose)
            implementation(libs.bundles.coil)

            implementation(projects.core.preferences)

            implementation(projects.presentation.style)
            implementation(projects.presentation.localisation)

            implementation(projects.infrastructure)

            implementation(libs.cmptoast)
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
    "androidRuntimeClasspath"(libs.androidx.ui.toolingpreview)
}
