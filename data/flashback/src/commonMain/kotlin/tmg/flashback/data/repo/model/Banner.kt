package tmg.flashback.data.repo.model

data class Banner(
    val message: String,
    val url: String? = null,
    val highlight: Boolean,
    val season: Int?
)