plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(projects.domain.formula1)
            implementation(kotlin("test"))
        }
        commonTest.dependencies {

        }
    }
}