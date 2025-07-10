package tmg.flashback.feature.about.presentation

import flashback.feature.about.generated.resources.Res
import flashback.feature.about.generated.resources.ic_util_icon_apple
import flashback.feature.about.generated.resources.ic_util_icon_email
import flashback.feature.about.generated.resources.ic_util_icon_github
import flashback.feature.about.generated.resources.ic_util_icon_play
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.button_apple_app
import flashback.presentation.localisation.generated.resources.button_email
import flashback.presentation.localisation.generated.resources.button_github
import flashback.presentation.localisation.generated.resources.button_play
import flashback.presentation.localisation.generated.resources.settings_header_appearance
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class AboutUiState(
    val deviceUuid: String,
    val installationId: String,
    val contactEmail: String,
)

enum class AboutButtons(
    val icon: DrawableResource,
    val label: StringResource,
    val key: String
) {
    Play(
        key = "play",
        icon = Res.drawable.ic_util_icon_play,
        label = string.button_play,
    ),
    Apple(
        key = "apple",
        icon = Res.drawable.ic_util_icon_apple,
        label = string.button_apple_app,
    ),
    Email(
        key = "email",
        icon = Res.drawable.ic_util_icon_email,
        label = string.button_email,
    ),
    Github(
        key = "github",
        icon = Res.drawable.ic_util_icon_github,
        label = string.button_github,
    )
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
    Room(
        dependencyName = "AndroidX Room",
        author = "Google",
        url = "https://developer.android.com/jetpack/androidx/releases/room",
        imageUrl = "https://avatars.githubusercontent.com/u/32689599?s=200"
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
    ),
    FlagKit_Android(
        dependencyName = "FlagKit",
        author = "murgupluoglu",
        url = "https://github.com/murgupluoglu/flagkit-android",
        imageUrl = "https://avatars.githubusercontent.com/u/11410147?v=4"
    ),
}
