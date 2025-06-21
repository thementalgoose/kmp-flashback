package tmg.flashback.data.repo.mappers.app

import tmg.flashback.formula1.model.Driver
import tmg.flashback.infrastructure.datetime.requireFromDate

class DriverDataMapper() {

    fun mapDriver(driver: tmg.flashback.persistence.flashback.models.drivers.Driver): Driver {
        return Driver(
            id = driver.id,
            firstName = driver.firstName,
            lastName = driver.lastName,
            dateOfBirth = requireFromDate(driver.dob),
            nationality = driver.nationality,
            nationalityISO = driver.nationalityISO,
            photoUrl = driver.photoUrl,
            wikiUrl = driver.wikiUrl,
            number = driver.number,
            code = driver.code
        )
    }
}