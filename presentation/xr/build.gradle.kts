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
            implementation(libs.compose.resources)
        }
    }
}