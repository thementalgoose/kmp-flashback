package tmg.flashback.data.repo.mappers.app

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.data.persistence.RoomDriverHistory
import tmg.flashback.persistence.flashback.models.drivers.model
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.DriverHistory
import tmg.flashback.formula1.model.RaceInfo
import tmg.flashback.formula1.model.model

internal class DriverMapperTest {

    private val mockDriverDataMapper: DriverDataMapper = mockk(relaxed = true)
    private val mockConstructorDataMapper: ConstructorDataMapper = mockk(relaxed = true)
    private val mockRaceMapper: RaceMapper = mockk(relaxed = true)

    private lateinit var sut: DriverMapper

    @BeforeEach
    internal fun setUp() {
        sut = DriverMapper(
            mockDriverDataMapper,
            mockConstructorDataMapper,
            mockRaceMapper
        )

        every { mockDriverDataMapper.mapDriver(any()) } returns Driver.model()
        every { mockConstructorDataMapper.mapConstructorData(any()) } returns Constructor.model()
        every { mockRaceMapper.mapRaceInfoWithCircuit(any()) } returns RaceInfo.model()
    }

    @Test
    fun `mapDriver maps fields correctly`() {
        val input = RoomDriverHistory.model()
        val expected = DriverHistory.model()

        assertEquals(expected, sut.mapDriver(input))
    }

    @Test
    fun `mapDriver returns null if input is null`() {
        assertNull(sut.mapDriver(null))
    }
}