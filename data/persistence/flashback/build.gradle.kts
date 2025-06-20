plugins {
    alias(libs.plugins.flashback.dataModule)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(projects.infrastructure)
        }
        iosMain.dependencies {
        }
    }
}


dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
