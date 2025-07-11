import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kotlinCocoapods)
}

val versionCodeProperty: Int = try {
    System.getenv("VERSION_CODE").toInt()
} catch (e: Exception) {
    1
}
val versionNameProperty: String = try {
    System.getenv("VERSION_NAME")
} catch (e: Exception) {
    "1.0.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Flashback"
        version = "1.0"
        homepage = "https://flashback.pages.dev"
        ios.deploymentTarget = "18.2"

        framework {
            baseName = "ComposeApp"
            isStatic = true

            // Dependency export
            // Uncomment and specify another project module if you have one:
            // export(project(":<your other KMP module>"))
            // transitiveExport = false // This is default.
        }

        pod("FirebaseCore") {
            version = "~> 11.13"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("FirebaseCrashlytics") {
            version = "~> 11.13"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("FirebaseRemoteConfig") {
            version = "~> 11.13"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("FirebaseMessaging") {
            version = "~> 11.13"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("FirebaseAnalytics") {
            version = "~> 11.13"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        podfile = project.file("../iosApp/Podfile")

        xcodeConfigurationToNativeBuildType["Sandbox Debug"]=NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["Sandbox Release"]=NativeBuildType.RELEASE
        xcodeConfigurationToNativeBuildType["Production Debug"]=NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["Production Release"]=NativeBuildType.RELEASE
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.remoteconfig)
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.analytics)
            implementation(libs.androidx.splashscreen)
            implementation(libs.firebase.messaging)
            implementation(libs.firebase.messagingktx)
        }
        commonMain.dependencies {

            implementation(projects.core.configuration)
            implementation(projects.core.device)
            implementation(projects.core.metrics.analytics)
            implementation(projects.core.metrics.crashlytics)
            implementation(projects.core.notifications)
            implementation(projects.core.preferences)
            implementation(projects.core.webbrowser)

            implementation(projects.data.flashback)
            implementation(projects.data.network.flashback)
//            implementation(projects.data.network.flashbackNews)
            implementation(projects.data.network.rss)
            implementation(projects.data.persistence.flashback)

            implementation(projects.domain.formula1)

            implementation(projects.eastereggs)

            implementation(projects.feature.about)
            implementation(projects.feature.circuits)
            implementation(projects.feature.constructors)
            implementation(projects.feature.drivers)
            implementation(projects.feature.maintenance)
            implementation(projects.feature.notifications)
            implementation(projects.feature.privacypolicy)
            implementation(projects.feature.reactiongame)
            implementation(projects.feature.rss)
            implementation(projects.feature.sandbox)
            implementation(projects.feature.search)
            implementation(projects.feature.season)
            implementation(projects.feature.weekend)
            implementation(projects.feature.widgetUpnext)

            implementation(projects.infrastructure)

            implementation(projects.presentation.localisation)
            implementation(projects.presentation.navigation)
            implementation(projects.presentation.style)
            implementation(projects.presentation.ui)

            implementation(compose.components.resources)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(libs.navigation.compose)
            implementation(libs.bundles.compose)
            implementation(libs.bundles.kotlin)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.koin.core)
            implementation(libs.bundles.koin.compose)
            implementation(libs.ksoup)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.turbine)
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
        versionName = "${versionNameProperty}.${versionCodeProperty}"
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


    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE") ?: "flashback.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEYSTORE_ALIAS")
            keyPassword = System.getenv("KEYSTORE_PASSWORD")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    flavorDimensions.add("variant")

    productFlavors {
        create("sandbox") {
            dimension = "variant"
            isDefault = true
            applicationIdSuffix = ".sandbox"
            resValue("string", "app_name", "Flashback Sandbox")
        }

        create("production") {
            dimension = "variant"
            resValue("string", "app_name", "Flashback")
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.resources {
    publicResClass = true
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