package tmg.flashback.flashbackapi.api.models.circuits

fun Circuit.Companion.model(
    id: String = "circuitId",
    name: String = "circuitName",
    wikiUrl: String? = "wikiUrl",
    location: Location? = Location.model(),
    city: String = "city",
    country: String = "country",
    countryISO: String = "countryISO"
): Circuit = Circuit(
    id = id,
    name = name,
    wikiUrl = wikiUrl,
    location = location,
    city = city,
    country = country,
    countryISO = countryISO,
)