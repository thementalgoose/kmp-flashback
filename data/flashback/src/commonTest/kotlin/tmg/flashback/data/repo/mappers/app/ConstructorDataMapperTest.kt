package tmg.flashback.data.repo.mappers.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.data.persistence.RoomConstructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.model

internal class ConstructorDataMapperTest {

    private lateinit var underTest: ConstructorDataMapper

    @BeforeEach
    internal fun setUp() {
        underTest = ConstructorDataMapper()
    }

    @Test
    fun `mapConstructorData maps fields correctly`() {
        val input = RoomConstructor.model()
        val expected = Constructor.model(
            color = 0 /* .toColorInt() doesnt work from unit tests */
        )

        assertEquals(expected, underTest.mapConstructorData(input))
    }
}