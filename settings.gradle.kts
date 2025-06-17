rootProject.name = "Flashback"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

includeBuild("build-logic")

include(":composeApp")

//include(":core:configuration")
//include(":core:device")
//include(":core:metrics")
//include(":core:notifications")
//include(":core:preferences")
//include(":core:webbrowser")

//include(":data:persistence")
//include(":data:network:flashback")
//include(":data:network:flashback-news")
//include(":data:network:rss")

include(":domain:formula1")

//include(":feature:circuits")
//include(":feature:constructors")
//include(":feature:drivers")
//include(":feature:maintenance")
//include(":feature:privacypolicy")
//include(":feature:rss")
//include(":feature:sandbox")
//include(":feature:search")
//include(":feature:season")
//include(":feature:weekend")

//include(":presentation:navigation")
include(":presentation:localisation")
include(":presentation:style")
//include(":presentation:ui")