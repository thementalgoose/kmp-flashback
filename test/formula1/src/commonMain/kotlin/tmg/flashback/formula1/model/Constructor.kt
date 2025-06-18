package tmg.flashback.formula1.model

fun Constructor.Companion.model(
    id: String = "constructorId",
    name: String = "name",
    photoUrl: String? = "photoUrl",
    wikiUrl: String? = "wikiUrl",
    nationality: String = "nationality",
    nationalityISO: String = "nationalityISO",
    color: Int = 0x8899EF,
): Constructor = Constructor(
    id = id,
    name = name,
    photoUrl = photoUrl,
    wikiUrl = wikiUrl,
    nationality = nationality,
    nationalityISO = nationalityISO,
    color = color
)