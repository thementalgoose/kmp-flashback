package tmg.flashback

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform