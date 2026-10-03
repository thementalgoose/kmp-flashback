package tmg.flashback.composeApp.presentation.navigation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.composeApp.repositories.NavRepository
import tmg.flashback.composeApp.repositories.model.NavLink
import tmg.flashback.composeApp.usecases.RequiresSyncUseCase
import tmg.flashback.device.usecases.OpenStorePageUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.eastereggs.usecases.IsMenuIconEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSnowEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsSummerEnabledUseCase
import tmg.flashback.eastereggs.usecases.IsUkraineEnabledUseCase
import tmg.flashback.feature.maintenance.repository.MaintenanceRepository
import tmg.flashback.feature.rss.usecases.IsRssEnabledUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

internal class AppNavigationViewModelTest {

    private lateinit var underTest: AppNavigationViewModel

    private val mockIsRssEnabledUseCase: IsRssEnabledUseCase = mock(autofill)
    private val mockIsMenuIconEnabledUseCase: IsMenuIconEnabledUseCase = mock(autofill)
    private val mockIsSnowEnabledUseCase: IsSnowEnabledUseCase = mock(autofill)
    private val mockIsSummerEnabledUseCase: IsSummerEnabledUseCase = mock(autofill)
    private val mockIsUkraineEnabledUseCase: IsUkraineEnabledUseCase = mock(autofill)
    private val mockRequiresSyncUseCase: RequiresSyncUseCase = mock(autofill)
    private val mockMaintenanceRepository: MaintenanceRepository = mock(autofill)
    private val mockNavRepository: NavRepository = mock(autofill)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)
    private val mockOpenStorePageUseCase: OpenStorePageUseCase = mock(autoUnit)

    private fun initUnderTest(
        isRss: Boolean = true,
        menuIcon: MenuIcons? = null,
        snow: Boolean = false,
        summer: Boolean = false,
        ukraine: Boolean = false,
        sync: Boolean = false,
        softUpgrade: Boolean = false,
        navLinks: List<NavLink> = emptyList()
    ) {
        every { mockIsRssEnabledUseCase.invoke() } returns isRss
        every { mockIsMenuIconEnabledUseCase.invoke() } returns menuIcon
        every { mockIsSnowEnabledUseCase.invoke() } returns snow
        every { mockIsSummerEnabledUseCase.invoke() } returns summer
        every { mockIsUkraineEnabledUseCase.invoke() } returns ukraine
        every { mockRequiresSyncUseCase.invoke() } returns sync
        every { mockMaintenanceRepository.softUpgrade } returns softUpgrade
        every { mockNavRepository.navLinks } returns navLinks

        underTest = AppNavigationViewModel(
            isRssEnabledUseCase = mockIsRssEnabledUseCase,
            isMenuIconEnabledUseCase = mockIsMenuIconEnabledUseCase,
            isSnowEnabledUseCase = mockIsSnowEnabledUseCase,
            isSummerEnabledUseCase = mockIsSummerEnabledUseCase,
            isUkraineEnabledUseCase = mockIsUkraineEnabledUseCase,
            requiresSyncUseCase = mockRequiresSyncUseCase,
            maintenanceRepository = mockMaintenanceRepository,
            navRepository = mockNavRepository,
            openWebpageUseCase = mockOpenWebpageUseCase,
            openStorePageUseCase = mockOpenStorePageUseCase
        )
    }

    @Test
    fun `initial uiState populates from dependencies`() = runTest {
        initUnderTest(
            isRss = true,
            menuIcon = null,
            snow = false,
            summer = true,
            ukraine = false,
            sync = true,
            softUpgrade = false,
            navLinks = emptyList()
        )

        underTest.uiState.test {
            val state = awaitItem()
            assertTrue(state.showRss)
            assertTrue(state.promptContentSync)
            assertFalse(state.promptSoftUpgrade)
            assertNull(state.screen)
            assertFalse(state.intoSubNavigation)
            assertNull(state.easterEggs.menuIcon)
            assertEquals(false, state.easterEggs.snow)
            assertEquals(true, state.easterEggs.summer)
            assertEquals(false, state.easterEggs.ukraine)
        }
    }

    @Test
    fun `destinationUpdated with null screen updates state`() = runTest {
        initUnderTest()

        underTest.destinationUpdated(null)

        underTest.uiState.test {
            val state = awaitItem()
            assertNull(state.screen)
            assertTrue(state.intoSubNavigation)
        }
    }

    @Test
    fun `openStore sets promptSoftUpgrade to false and calls use case`() = runTest {
        initUnderTest(softUpgrade = true)

        underTest.openStore()

        underTest.uiState.test {
            val state = awaitItem()
            assertFalse(state.promptSoftUpgrade)
        }
        verify {
            mockOpenStorePageUseCase.invoke()
        }
    }

    @Test
    fun `dismissSoftUpgrade sets promptSoftUpgrade to false`() = runTest {
        initUnderTest(softUpgrade = true)

        underTest.dismissSoftUpgrade()

        underTest.uiState.test {
            val state = awaitItem()
            assertFalse(state.promptSoftUpgrade)
        }
    }

    @Test
    fun `openWebpage invokes openWebpageUseCase`() {
        initUnderTest()

        underTest.openWebpage("https://example.com")

        verify {
            mockOpenWebpageUseCase.invoke("https://example.com")
        }
    }
}
