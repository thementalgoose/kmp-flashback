plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
            implementation(libs.okhttp.loggingInterceptor)
            implementation(libs.okhttp.dnsoverhttps)
        }
        commonMain.dependencies {
            implementation(projects.infrastructure)
            implementation(projects.core.configuration)
            implementation(libs.bundles.common.ktor)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.coroutines.core)
        }
        commonTest.dependencies {
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
            implementation(kotlin("test"))
        }
        desktopMain.dependencies {

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
