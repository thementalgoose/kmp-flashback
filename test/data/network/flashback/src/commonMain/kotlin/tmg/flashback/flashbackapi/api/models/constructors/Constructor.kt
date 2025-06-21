package tmg.flashback.flashbackapi.api.models.constructors

fun Constructor.Companion.model(
    id: String = "constructorId",
    colour: String = "#00FFFF",
    name: String = "name",
    photoUrl: String? = "photoUrl",
    nationality: String = "nationality",
    nationalityISO: String = "nationalityISO",
    wikiUrl: String? = "wikiUrl"
): Constructor = Constructor(
    id = id,
    colour = colour,
    name = name,
    photoUrl = photoUrl,
    nationality = nationality,
    nationalityISO = nationalityISO,
    wikiUrl = wikiUrl
)