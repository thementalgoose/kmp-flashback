package tmg.flashback.data.repo.mappers.app

import tmg.flashback.data.repo.fakes.fakeConstructorDataMapper
import tmg.flashback.data.repo.fakes.fakeDriverDataMapper
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.persistence.flashback.RoomConstructorHistory
import tmg.flashback.formula1.model.ConstructorHistory
import tmg.flashback.formula1.model.model
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ConstructorMapperTest {

    private lateinit var underTest: ConstructorMapper

    internal fun initUnderTest() {
        underTest = ConstructorMapper(
            constructorDataMapper = fakeConstructorDataMapper(),
            driverDataMapper = fakeDriverDataMapper()
        )
    }

    @Test
    fun `mapConstructor maps fields correctly`() {
        initUnderTest()

        val input = RoomConstructorHistory.model()
        val expected = ConstructorHistory.model()

        assertEquals(expected, underTest.mapConstructor(input))
    }
}