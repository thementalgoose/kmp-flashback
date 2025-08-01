import org.gradle.kotlin.dsl.provider.KotlinDslPluginSupport.kotlinCompilerArgs

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.googleServices) version libs.versions.google.services apply false
    alias(libs.plugins.crashlytics) version libs.versions.crashlytics apply false
    alias(libs.plugins.kotlinCocoapods) apply false
}