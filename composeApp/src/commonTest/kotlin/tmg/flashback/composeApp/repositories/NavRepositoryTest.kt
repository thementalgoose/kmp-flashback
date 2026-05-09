package tmg.flashback.composeApp.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.repositories.model.NavLinkJson
import tmg.flashback.composeApp.repositories.model.NavLinksJson
import tmg.flashback.configuration.manager.ConfigManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class NavRepositoryTest {

    private lateinit var underTest: NavRepositoryImpl

    private val mockConfigManager: ConfigManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = NavRepositoryImpl(
            configManager = mockConfigManager
        )
    }

    @Test
    fun `getting nav links returns empty list when config returns null`() {
        every { mockConfigManager.getJson(expectedKeyNav, NavLinksJson.serializer()) } returns null
        initUnderTest()
        assertEquals(emptyList(), underTest.navLinks)
    }

    @Test
    fun `getting nav links returns converted nav links from config`() {
        val navLinkJson = NavLinkJson(
            type = "url",
            name = "Test Link",
            url = "https://example.com"
        )
        val navLinksJson = NavLinksJson(nav = listOf(navLinkJson))
        every { mockConfigManager.getJson(expectedKeyNav, NavLinksJson.serializer()) } returns navLinksJson
        initUnderTest()
        val links = underTest.navLinks
        assertEquals(1, links.size)
        assertTrue(links.first() is NavLink.Url)
        val urlLink = links.first() as NavLink.Url
        assertEquals("Test Link", urlLink.name)
        assertEquals("https://example.com", urlLink.url)
    }

    @Test
    fun `getting nav links filters out invalid entries`() {
        val validNavLinkJson = NavLinkJson(
            type = "url",
            name = "Valid Link",
            url = "https://example.com"
        )
        val invalidNavLinkJson = NavLinkJson(
            type = "url",
            name = null,
            url = "https://invalid.com"
        )
        val navLinksJson = NavLinksJson(nav = listOf(validNavLinkJson, invalidNavLinkJson))
        every { mockConfigManager.getJson(expectedKeyNav, NavLinksJson.serializer()) } returns navLinksJson
        initUnderTest()
        val links = underTest.navLinks
        assertEquals(1, links.size)
    }

    companion object {
        private const val expectedKeyNav = "nav"
    }
}