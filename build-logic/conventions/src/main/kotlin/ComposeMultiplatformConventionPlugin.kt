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
                        implementation(libs.findLibrary("androidx.ui.toolingpreview").get().get())
                    }
                }
            }
        }
    }
}