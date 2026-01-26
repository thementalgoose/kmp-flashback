plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
            implementation(libs.okhttp.loggingInterceptor)
            implementation(libs.okhttp.dnsoverhttps)
        }
        commonMain.dependencies {
            implementation(libs.bundles.common.ktor)
            implementation(libs.bundles.kotlin)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}