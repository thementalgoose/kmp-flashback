package tmg.flashback.data.repo.mappers.app

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.persistence.flashback.models.standings.ConstructorStandingWithDrivers
import tmg.flashback.persistence.flashback.models.standings.model
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.SeasonConstructorStandingSeason
import tmg.flashback.formula1.model.SeasonConstructorStandings
import tmg.flashback.formula1.model.model

internal class ConstructorStandingMapperTest {

    private val mockConstructorDataMapper: ConstructorDataMapper = mockk(relaxed = true)
    private val mockDriverDataMapper: DriverDataMapper = mockk(relaxed = true)

    private lateinit var underTest: ConstructorStandingMapper

    @BeforeEach
    internal fun setUp() {
        underTest = ConstructorStandingMapper(
            mockDriverDataMapper,
            mockConstructorDataMapper
        )

        every { mockDriverDataMapper.mapDriver(any()) } returns Driver.model()
        every { mockConstructorDataMapper.mapConstructorData(any()) } returns Constructor.model()
    }

    @Test
    fun `mapConstructorStanding model maps fields correctly`() {
        val input = ConstructorStandingWithDrivers.model()
        val expected = SeasonConstructorStandingSeason.model()

        assertEquals(expected, underTest.mapConstructorStanding(input))
    }

    @Test
    fun `mapConstructorStanding list maps fields correctly`() {
        val input = listOf(ConstructorStandingWithDrivers.model())
        val expected = SeasonConstructorStandings.model()

        assertEquals(expected, underTest.mapConstructorStanding(input))
    }

    @Test
    fun `mapConstructorStanding list returns null if list is empty`() {
        val input = emptyList<ConstructorStandingWithDrivers>()

        assertNull(underTest.mapConstructorStanding(input))
    }

}