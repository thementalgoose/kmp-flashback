import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        plugins.apply(libs.findPlugin("androidLibrary").get().get().pluginId)

        extensions.configure<LibraryExtension> {
            namespace = "tmg.flashback.$modulePackageName"

            compileSdk = libs.findVersion("android.compileSdk").get().requiredVersion.toInt()

            defaultConfig {
                minSdk = libs.findVersion("android.minSdk").get().requiredVersion.toInt()
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
            buildFeatures {
                buildConfig = true
            }
            buildTypes {
                getByName("release") {
                    buildConfigField("String", "VERSION_NAME", "\"${System.getenv("VERSION_NAME") ?: "1.0.0"}\"")
                    buildConfigField("String", "VERSION_CODE", "\"${System.getenv("VERSION_CODE") ?: "1"}\"")
                }
                getByName("debug") {
                    buildConfigField("String", "VERSION_NAME", "\"${System.getenv("VERSION_NAME") ?: "1.0.0"}\"")
                    buildConfigField("String", "VERSION_CODE", "\"${System.getenv("VERSION_CODE") ?: "1"}\"")
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}