plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

compose.resources {
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.compose.resources)

            implementation(projects.infrastructure)
            implementation(projects.presentation.localisation)
        }
        commonTest.dependencies {
            implementation(projects.test.formula1)
            implementation(kotlin("test"))
        }
    }
}