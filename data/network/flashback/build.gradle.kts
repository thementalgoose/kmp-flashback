plugins {
    alias(libs.plugins.flashback.dataModule)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
            implementation(libs.okhttp.loggingInterceptor)
        }
        commonMain.dependencies {
            implementation(libs.bundles.common.ktor)
            implementation(libs.coroutines.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
