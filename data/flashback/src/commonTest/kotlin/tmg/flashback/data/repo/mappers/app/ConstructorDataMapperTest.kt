package tmg.flashback.data.repo.mappers.app

import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.model
import tmg.flashback.persistence.flashback.RoomConstructor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class ConstructorDataMapperTest {

    private lateinit var underTest: ConstructorDataMapper

    internal fun initUnderTest() {
        underTest = ConstructorDataMapper()
    }

    @Test
    fun `mapConstructorData maps fields correctly`() {
        initUnderTest()

        val input = RoomConstructor.model()
        val expected = Constructor.model()

        assertEquals(expected, underTest.mapConstructorData(input))
    }
}