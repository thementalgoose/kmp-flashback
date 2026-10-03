package tmg.flashback.data.repo.mappers.network

import tmg.flashback.flashbackapi.api.models.lineup.Lineup
import tmg.flashback.flashbackapi.api.models.lineup.model
import tmg.flashback.persistence.flashback.models.lineup.model
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class NetworkLineupMapperTest {

    private lateinit var underTest: NetworkLineupMapper

    internal fun initUnderTest() {
        underTest = NetworkLineupMapper()
    }

    @Test
    fun `mapWinterTesting maps fields correctly`() {
        initUnderTest()

        val input = Lineup.model()
        val expected = tmg.flashback.persistence.flashback.models.lineup.Lineup.model()

        assertEquals(listOf(expected), underTest.mapLineup(input.lineup))
    }
}