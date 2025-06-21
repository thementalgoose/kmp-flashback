package tmg.flashback.data.repo.mappers.network

import tmg.flashback.persistence.flashback.models.drivers.Driver

class NetworkDriverDataMapper {

    @Throws(RuntimeException::class)
    fun mapDriverData(driver: tmg.flashback.flashbackapi.api.models.drivers.Driver): Driver {
        return Driver(
            id = driver.id,
            firstName = driver.firstName,
            lastName = driver.lastName,
            dob = driver.dob,
            nationality = driver.nationality,
            nationalityISO = driver.nationalityISO,
            photoUrl = driver.photoUrl,
            wikiUrl = driver.wikiUrl,
            number = driver.permanentNumber?.toIntOrNull(),
            code = driver.code
        )
    }
}