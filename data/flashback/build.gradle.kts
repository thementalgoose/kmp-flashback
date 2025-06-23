plugins {
    alias(libs.plugins.flashback.dataModule)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(libs.room.runtime)
            implementation(libs.bundles.kotlin)
            implementation(projects.infrastructure)
            implementation(projects.domain.formula1)
            implementation(projects.data.network.flashback)
            implementation(projects.data.persistence.flashback)
            implementation(projects.core.configuration)
        }
        commonTest.dependencies {
            implementation(projects.test.data.persistence.flashback)
            implementation(projects.test.data.network.flashback)
            implementation(projects.test.formula1)
            implementation(kotlin("test"))
        }
        iosMain.dependencies {
        }
    }
}

dependencies {

}
