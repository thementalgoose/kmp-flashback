plugins {
    alias(libs.plugins.flashback.dataModule)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(projects.infrastructure)
            implementation(projects.domain.formula1)
            implementation(projects.data.network.flashback)
            implementation(projects.data.persistence.flashback)
        }
        iosMain.dependencies {
        }
    }
}

dependencies {

}
