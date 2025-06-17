import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

val versionCodeProperty = 1
val versionNameProperty = "1.0.0"

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }
        commonMain.dependencies {

//            implementation(projects.core.configuration)
//            implementation(projects.core.device)
//            implementation(projects.core.metrics)
//            implementation(projects.core.notifications)
//            implementation(projects.core.preferences)
//            implementation(projects.core.webbrowser)

//            implementation(projects.data.network.flashback)
//            implementation(projects.data.network.flashbackNews)
//            implementation(projects.data.network.rss)
//            implementation(projects.data.persistence)

            implementation(projects.domain.formula1)

//            implementation(projects.feature.circuits)
//            implementation(projects.feature.constructors)
//            implementation(projects.feature.drivers)
//            implementation(projects.feature.maintenance)
//            implementation(projects.feature.privacypolicy)
//            implementation(projects.feature.rss)
//            implementation(projects.feature.sandbox)
//            implementation(projects.feature.search)
//            implementation(projects.feature.season)
//            implementation(projects.feature.weekend)

            implementation(projects.presentation.localisation)
//            implementation(projects.presentation.navigation)
            implementation(projects.presentation.style)
//            implementation(projects.presentation.ui)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.core)
            implementation(libs.bundles.koin.compose)
        }
        desktopMain.dependencies {

        }
    }
}

android {
    namespace = "tmg.flashback"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "tmg.flashback"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeProperty
        versionName = versionNameProperty
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "tmg.flashback.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Flashback"
            packageVersion = versionNameProperty
            description = "Flashback by TheMentalGoose"
            copyright = "Copyright (c) 2025, TheMentalGoose"
            licenseFile.set(project.file("../LICENSE.txt"))

            macOS {
                dockName = "Flashback"
                entitlementsFile.set(project.file("default.entitlements"))
            }
        }

        buildTypes.release {
            proguard {
                obfuscate.set(true)
                configurationFiles.from("proguard-rules.pro")
            }
        }
    }
}