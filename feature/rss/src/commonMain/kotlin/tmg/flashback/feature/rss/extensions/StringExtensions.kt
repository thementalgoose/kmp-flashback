package tmg.flashback.feature.rss.extensions

internal fun String.stripHTTP(): String {
    return this
        .replace("https://www.", "")
        .replace("http://www.", "")
        .replace("https://", "")
        .replace("http://", "")
        .stripWWW()

}

internal fun String.stripWWW(): String {
    return when (this.startsWith("www.")) {
        true -> this.substring(4, this.length)
        false -> this
    }
}