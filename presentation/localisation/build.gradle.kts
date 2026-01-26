plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

compose.resources {
    publicResClass = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(compose.components.resources)
            implementation(compose.foundation)
        }
    }
}