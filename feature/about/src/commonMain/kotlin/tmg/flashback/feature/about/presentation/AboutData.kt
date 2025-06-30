package tmg.flashback.feature.about.presentation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class AboutButtons(
    val icon: DrawableResource,
    val label: StringResource,
    val key: String
) {

}

enum class AboutDependency(
    val dependencyName: String,
    val author: String,
    val imageUrl: String,
    val url: String
) {
    Ergast(
        dependencyName = "Ergast API",
        author = "Ergast",
        url = "https://ergast.com/mrd/",
        imageUrl = "https://i.imgur.com/mWzkikf.png"
    ),
    KotlinMultiplatform(
        dependencyName = "Kotlin Multiplatform",
        author = "Kotlin",
        url = "http://kotlinlang.org/docs/multiplatform.html",
        imageUrl = "https://i.imgur.com/p93EtbY.png"
    ),
    ComposeMultiplatform(
        dependencyName = "Compose Multiplatform",
        author = "Jetbrains",
        url = "https://www.jetbrains.com/compose-multiplatform/",
        imageUrl = "https://i.imgur.com/X2CEYWQ.png"
    ),
    Firebase(
        dependencyName = "Firebase",
        author = "Google",
        url = "https://firebase.google.com/",
        imageUrl = "https://avatars2.githubusercontent.com/u/1335026"
    ),
    Ktor(
        dependencyName = "Ktor",
        author = "ktorio",
        url = "https://ktor.io/",
        imageUrl = "https://avatars.githubusercontent.com/u/28214161?s=200&v=4"
    ),
    OKHttp(
        dependencyName = "OkHttp",
        author = "Square",
        url = "https://square.github.io/okhttp/",
        imageUrl = "https://avatars.githubusercontent.com/u/82592"
    ),
    Coil(
        dependencyName = "Coil",
        author = "Coil",
        url = "https://github.com/coil-kt/coil",
        imageUrl = "https://avatars.githubusercontent.com/u/52722434"
    ),
    FlagKit(
        dependencyName = "FlagKit",
        author = "Anders Carlsen",
        url = "https://github.com/acarlsen/kmp-flagkit",
        imageUrl = "https://avatars.githubusercontent.com/u/6698402?v=4"
    )
}
