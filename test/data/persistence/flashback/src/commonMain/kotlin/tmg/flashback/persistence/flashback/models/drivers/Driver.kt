package tmg.flashback.persistence.flashback.models.drivers

fun Driver.Companion.model(
    id: String = "driverId",
    firstName: String = "firstName",
    lastName: String = "lastName",
    dob: String = "1995-10-12",
    nationality: String = "nationality",
    nationalityISO: String = "nationalityISO",
    photoUrl: String? = "photoUrl",
    wikiUrl: String? = "wikiUrl",
    number: Int? = 23,
    code: String? = "code",
): Driver = Driver(
    id = id,
    firstName = firstName,
    lastName = lastName,
    dob = dob,
    nationality = nationality,
    nationalityISO = nationalityISO,
    photoUrl = photoUrl,
    wikiUrl = wikiUrl,
    number = number,
    code = code
)