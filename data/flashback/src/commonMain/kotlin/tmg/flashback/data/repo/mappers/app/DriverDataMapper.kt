package tmg.flashback.data.repo.mappers.app

import tmg.flashback.formula1.model.Driver

class DriverDataMapper() {

    fun mapDriver(driver: tmg.flashback.persistence.flashback.models.drivers.Driver): Driver {
        return Driver(
            id = driver.id,
            firstName = driver.firstName,
            lastName = driver.lastName,
            dateOfBirth = LocalDateUtils.requireFromDate(driver.dob),
            nationality = driver.nationality,
            nationalityISO = driver.nationalityISO,
            photoUrl = driver.photoUrl,
            wikiUrl = driver.wikiUrl,
            number = driver.number,
            code = driver.code
        )
    }
}