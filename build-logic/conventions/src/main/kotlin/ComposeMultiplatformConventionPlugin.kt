import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.configureEach {
                when (name) {
                    "commonMain" -> dependencies {
                        implementation(libs.findLibrary("compose.runtime").get().get())
                        implementation(libs.findLibrary("compose.foundation").get().get())
                        implementation(libs.findLibrary("compose.material").get().get())
                        implementation(libs.findLibrary("compose.material3").get().get())
                        implementation(libs.findLibrary("compose.ui").get().get())
                        implementation(libs.findLibrary("compose.resources").get().get())
                        implementation(libs.findLibrary("compose.materialIconsExtended").get().get())
                        implementation(libs.findLibrary("compose.uiTest").get().get())
                        implementation(libs.findLibrary("androidx.ui.toolingpreview").get().get())

                        implementation(libs.findLibrary("compose.nav3").get().get())
                        implementation(libs.findLibrary("compose.nav3Adaptive").get().get())
                        implementation(libs.findLibrary("compose.nav3ViewModel").get().get())
                    }
                }
            }
        }
    }
}