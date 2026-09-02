import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.flashback.composeMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
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
    // iosX64()
    iosArm64()
    iosSimulatorArm64()
    wasmJs {
        binaries.executable()
    }

    jvmToolchain(21)

    cocoapods {
        summary = "Flashback"
        version = "1.0"
        homepage = "https://flashback.pages.dev"
        ios.deploymentTarget = "26.0"

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

        pod("FirebaseInstallations") {
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
            implementation(libs.firebase.installations)

            implementation(libs.bundles.androidx.xr)
            compileOnly(libs.androidx.xr.extensions)
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
            implementation(projects.data.network.flashbackNews)
            implementation(projects.data.network.rss)
            implementation(projects.data.persistence.flashback)

            implementation(projects.domain.formula1)

            implementation(projects.eastereggs)

            implementation(projects.feature.about)
            implementation(projects.feature.circuits)
            implementation(projects.feature.constructors)
            implementation(projects.feature.drivers)
            implementation(projects.feature.glossary)
            implementation(projects.feature.highlights)
            implementation(projects.feature.lineup)
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
            implementation(projects.presentation.xr)

            implementation(libs.compose.resources)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(libs.navigation.compose)
            implementation(libs.cmptoast)
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

compose.resources {
    publicResClass = true
}

compose.desktop {
    application {
        mainClass = "tmg.flashback.composeApp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Flashback"
            packageVersion = versionNameProperty
            description = "Flashback by TheMentalGoose"
            copyright = "Copyright (c) 2025, TheMentalGoose"
            licenseFile.set(project.file("../LICENSE.txt"))

            macOS {
                bundleID = "tmg.flashback"
                dockName = "Flashback"
                entitlementsFile.set(project.file("default.entitlements"))
            }
        }

        version = versionNameProperty

        buildTypes.release {
            proguard {
                obfuscate.set(true)
                configurationFiles.from("proguard-rules.pro")
            }
        }
    }
}

tasks.named("generateComposeResClass") {
//    dependsOn("updatePlistVersion")
}

tasks.register("updatePlistVersion") {
    val plistFile = project.file("../iosApp/iosApp/Info.plist") // Path to your `Info.plist` file

    doLast {
        if (!plistFile.exists()) {
            throw GradleException("Info.plist not found at ${plistFile.absolutePath}")
        }

        val appVersion: String = versionNameProperty
        val buildVersion: String = versionCodeProperty.toString()

        var plistContent = plistFile.readText()

        plistContent = plistContent.replace(
            Regex("<key>CFBundleShortVersionString</key>\\s*<string>.*?</string>"),
            "<key>CFBundleShortVersionString</key>\n    <string>$appVersion</string>"
        )
        plistContent = plistContent.replace(
            Regex("<key>CFBundleVersion</key>\\s*<string>.*?</string>"),
            "<key>CFBundleVersion</key>\n    <string>$buildVersion</string>"
        )

        plistFile.writeText(plistContent)
    }
}