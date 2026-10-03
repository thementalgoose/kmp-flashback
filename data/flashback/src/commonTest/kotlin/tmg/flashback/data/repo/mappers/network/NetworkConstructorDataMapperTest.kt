package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.NetworkConstructor
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.flashbackapi.api.models.constructors.model
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class NetworkConstructorDataMapperTest {

    private lateinit var sut: NetworkConstructorDataMapper

    internal fun initUnderTest() {
        sut = NetworkConstructorDataMapper()
    }

    @Test
    fun `mapConstructorData maps fields correctly`() {
        initUnderTest()

        val input = NetworkConstructor.model()
        val expected = Constructor.model()

        assertEquals(expected, sut.mapConstructorData(input))
    }
}