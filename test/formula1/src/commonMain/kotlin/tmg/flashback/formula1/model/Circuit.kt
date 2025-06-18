package tmg.flashback.formula1.model

fun Circuit.Companion.model(
    id: String = "circuitId",
    name: String = "circuitName",
    wikiUrl: String? = "wikiUrl",
    city: String = "city",
    country: String = "country",
    countryISO: String = "countryISO",
    location: Location? = Location.model()
): Circuit = Circuit(
    id = id,
    name = name,
    wikiUrl = wikiUrl,
    city = city,
    country = country,
    countryISO = countryISO,
    location = location
)
