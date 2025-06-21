plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(projects.data.persistence.flashback)
            implementation(kotlin("test"))
        }
        commonTest.dependencies {

        }
    }
}