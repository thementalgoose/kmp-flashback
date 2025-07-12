import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("flashback.kotlinMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("flashback.androidLibrary").get().get().pluginId)
        plugins.apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("composeCompiler").get().get().pluginId)
        plugins.apply(libs.findPlugin("kotlinSerialization").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.add("-Xstring-concat=inline")
                }
            }

            sourceSets.configureEach {
                when (name) {
                    "commonMain" -> dependencies {
                        implementation(libs.findLibrary("kotlinx.serialization").get().get())
                        libs.findBundle("koin.compose").get().get().forEach {
                            implementation(it)
                        }
                    }
                }
            }
        }
    }
}