import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room3)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlin)
            implementation(libs.room3.runtime)
            implementation(libs.sqlite.bundled)
            implementation(projects.infrastructure)
        }
        iosMain.dependencies {
        }
    }
}


dependencies {
    add("kspAndroid", libs.room3.compiler)
    add("kspDesktop", libs.room3.compiler)
    add("kspIosSimulatorArm64", libs.room3.compiler)
    // add("kspIosX64", libs.room3.compiler)
    add("kspIosArm64", libs.room3.compiler)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}
