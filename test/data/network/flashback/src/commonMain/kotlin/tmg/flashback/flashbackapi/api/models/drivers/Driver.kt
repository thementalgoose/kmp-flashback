package tmg.flashback.flashbackapi.api.models.drivers

fun Driver.Companion.model(
    id: String = "driverId",
    firstName: String = "firstName",
    lastName: String = "lastName",
    dob: String = "1995-10-12",
    nationality: String = "nationality",
    nationalityISO: String = "nationalityISO",
    photoUrl: String? = "photoUrl",
    wikiUrl: String? = "wikiUrl",
    code: String? = "code",
    permanentNumber: String? = "23"
): Driver = Driver(
    id = id,
    firstName = firstName,
    lastName = lastName,
    dob = dob,
    nationality = nationality,
    nationalityISO = nationalityISO,
    photoUrl = photoUrl,
    wikiUrl = wikiUrl,
    code = code,
    permanentNumber = permanentNumber
)