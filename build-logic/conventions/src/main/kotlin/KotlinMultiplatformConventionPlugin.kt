import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("androidKotlinMultiplatformLibrary").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {

            this.extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                compileSdk {
                    version = release(libs.findVersion("android.compileSdk").get().toString().toInt())
                }
                minSdk {
                    version = release(libs.findVersion("android.minSdk").get().toString().toInt())
                }
                androidResources {
                    enable = true
                }
                namespace = "tmg.flashback.$modulePackageName"
                compilerOptions {
                    freeCompilerArgs.add("-Xstring-concat=inline")
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }

            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach { iosTarget ->
                iosTarget.binaries.framework {
                    baseName = moduleName
                    isStatic = true
                }
            }

            jvm("desktop")

            sourceSets.configureEach {
                when (name) {
                    "commonMain" -> dependencies {
                        implementation(libs.findLibrary("koin.core").get().get())
                    }
                }
                languageSettings {
                    optIn("kotlin.time.ExperimentalTime")
                }
            }
        }
    }
}