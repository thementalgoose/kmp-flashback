plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
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

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE") ?: "flashback.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEYSTORE_ALIAS")
            keyPassword = System.getenv("KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
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

    sourceSets {
        getByName("sandbox") {
            res.srcDirs("src/sandbox/res")
        }
        getByName("production") {
            res.srcDirs("src/production/res")
        }
    }
}

dependencies {
    implementation(projects.composeApp)

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
    implementation(projects.feature.highlights)
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

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.remoteconfig)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.installations)

    // Android UI
    implementation(libs.androidx.splashscreen)

    // XR Support
    implementation(libs.bundles.androidx.xr)
    compileOnly(libs.androidx.xr.extensions)

    // CMPToast
    implementation(libs.cmptoast)

    // Koin
    implementation(libs.koin.core)

    // Debug tools
    debugImplementation(compose.uiTooling)
}
