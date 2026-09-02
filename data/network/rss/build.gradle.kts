plugins {
    alias(libs.plugins.flashback.kotlinMultiplatform)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
            implementation(libs.okhttp.loggingInterceptor)
            implementation(libs.okhttp.dnsoverhttps)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        commonMain.dependencies {
            implementation(projects.infrastructure)
            implementation(projects.core.configuration)
            implementation(libs.bundles.common.ktor)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.serialization.kotlinx.xml)
            implementation(libs.coroutines.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
