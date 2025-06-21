package tmg.flashback.persistence.flashback.models.circuit

fun Circuit.Companion.model(
    id: String = "circuitId",
    name: String = "circuitName",
    wikiUrl: String? = "wikiUrl",
    locationLat: Double? = 51.101,
    locationLng: Double? = -1.101,
    city: String = "city",
    country: String = "country",
    countryISO: String = "countryISO",
): Circuit = Circuit(
    id = id,
    name = name,
    wikiUrl = wikiUrl,
    locationLat = locationLat,
    locationLng = locationLng,
    city = city,
    country = country,
    countryISO = countryISO,
)