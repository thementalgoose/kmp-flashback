package tmg.flashback.formula1.model

import kotlin.test.Test
import kotlin.test.assertEquals

internal class DriverTest {

    @Test
    fun `name combines first name and last name`() {
        val driver = Driver.model(
            firstName = "John",
            lastName = "Smith"
        )

        assertEquals("John Smith", driver.name)
    }
}