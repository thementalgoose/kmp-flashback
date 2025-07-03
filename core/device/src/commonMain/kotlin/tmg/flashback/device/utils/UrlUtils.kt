package tmg.flashback.device.utils

object UrlUtils {

    fun String.isYoutube(): Boolean {
        return this.startsWith("https://youtube.com") || this.startsWith("https://www.youtube.com") ||
                this.startsWith("https://youtu.be") || this.startsWith(("https://www.youtu.be"))
    }

    fun String.isLocation(): Boolean {
        return this.startsWith("https://google.com/maps/search/") ||
                this.startsWith("geo:0,0")
    }
}