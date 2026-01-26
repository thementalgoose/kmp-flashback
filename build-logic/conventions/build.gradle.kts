plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = libs.plugins.flashback.kotlinMultiplatform.get().pluginId
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("featureModule") {
            id = libs.plugins.flashback.featureModule.get().pluginId
            implementationClass = "FeatureModuleConventionPlugin"
        }

        register("composeMultiplatform") {
            id = libs.plugins.flashback.composeMultiplatform.get().pluginId
            implementationClass = "ComposeMultiplatformConventionPlugin"
        }
    }
}