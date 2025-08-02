plugins {
    alias(libs.plugins.flashback.dataModule)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.ktor.client.okHttp)
            implementation(libs.okhttp.loggingInterceptor)
        }
        commonMain.dependencies {
            implementation(projects.infrastructure)
            implementation(projects.core.configuration)
            implementation(libs.bundles.common.ktor)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.coroutines.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        desktopMain.dependencies {

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
