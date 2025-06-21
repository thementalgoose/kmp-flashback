package tmg.flashback.data.repo.mappers.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tmg.flashback.flashbackapi.NetworkConstructor
import tmg.flashback.persistence.flashback.models.constructors.Constructor
import tmg.flashback.persistence.flashback.models.constructors.model
import tmg.flashback.flashbackapi.api.models.constructors.model

internal class NetworkConstructorDataMapperTest {

    private lateinit var sut: NetworkConstructorDataMapper

    @BeforeEach
    internal fun setUp() {
        sut = NetworkConstructorDataMapper()
    }

    @Test
    fun `mapConstructorData maps fields correctly`() {
        val input = NetworkConstructor.model()
        val expected = Constructor.model()

        assertEquals(expected, sut.mapConstructorData(input))
    }
}