package tmg.flashback.data.repo.mappers.app

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.data.persistence.RoomConstructorHistory
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.model.model


internal class ConstructorMapperTest {

    private val mockConstructorDataMapper: ConstructorDataMapper = mockk(relaxed = true)
    private val mockDriverDataMapper: DriverDataMapper = mockk(relaxed = true)

    private lateinit var underTest: ConstructorMapper

    @BeforeEach
    internal fun setUp() {
        underTest = ConstructorMapper(
            mockConstructorDataMapper,
            mockDriverDataMapper
        )

        every { mockDriverDataMapper.mapDriver(any()) } returns Driver.model()
        every { mockConstructorDataMapper.mapConstructorData(any()) } returns Constructor.model()
    }

    @Test
    fun `mapConstructor maps fields correctly`() {
        val input = RoomConstructorHistory.model()
        val expected = ConstructorHistory.model()

        assertEquals(expected, underTest.mapConstructor(input))
    }
}