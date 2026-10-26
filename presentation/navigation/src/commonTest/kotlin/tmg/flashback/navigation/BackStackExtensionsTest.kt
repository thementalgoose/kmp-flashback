package tmg.flashback.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BackStackExtensionsTest {

    private fun createBackStack(vararg initial: NavKey): NavBackStack<NavKey> {
        val stack = NavBackStack<NavKey>()
        stack.addAll(initial)
        return stack
    }

    @Test
    fun userStoryWalkthroughScenario() {
        // Step 1: Open app, tabHistory = Calendar
        val backStack = createBackStack(NavCalendar)
        assertEquals(listOf<NavKey>(NavCalendar), backStack.toList())

        // Step 2: Click "Drivers", tabHistory = Calendar, Drivers
        backStack.appendMaster(NavDriverStandings)
        assertEquals(listOf<NavKey>(NavCalendar, NavDriverStandings), backStack.toList())

        // Step 3: Click "Teams", tabHistory = Calendar, Drivers, Teams
        backStack.appendMaster(NavTeamStandings)
        assertEquals(listOf<NavKey>(NavCalendar, NavDriverStandings, NavTeamStandings), backStack.toList())

        // Step 4: Rotate device, click "Search" on navigation rail, tabHistory = Calendar, Drivers, Teams, Paddock, Search
        backStack.appendMaster(NavSearch())
        assertEquals(
            listOf<NavKey>(NavCalendar, NavDriverStandings, NavTeamStandings, NavPaddock, NavSearch()),
            backStack.toList()
        )

        // Step 5: Click back -> tabHistory = Calendar, Drivers, Teams, Paddock
        assertTrue(backStack.pop())
        assertEquals(
            listOf<NavKey>(NavCalendar, NavDriverStandings, NavTeamStandings, NavPaddock),
            backStack.toList()
        )

        // Step 6: Click Drivers -> tabHistory = Calendar, Teams, Paddock, Drivers
        backStack.appendMaster(NavDriverStandings)
        assertEquals(
            listOf<NavKey>(NavCalendar, NavTeamStandings, NavPaddock, NavDriverStandings),
            backStack.toList()
        )

        // Step 7: Click Teams -> tabHistory = Calendar, Paddock, Drivers, Teams
        backStack.appendMaster(NavTeamStandings)
        assertEquals(
            listOf<NavKey>(NavCalendar, NavPaddock, NavDriverStandings, NavTeamStandings),
            backStack.toList()
        )

        // Step 8: Click back -> tabHistory = Calendar, Paddock, Drivers
        assertTrue(backStack.pop())
        assertEquals(
            listOf<NavKey>(NavCalendar, NavPaddock, NavDriverStandings),
            backStack.toList()
        )

        // Step 9: Click back -> tabHistory = Calendar, Paddock
        assertTrue(backStack.pop())
        assertEquals(
            listOf<NavKey>(NavCalendar, NavPaddock),
            backStack.toList()
        )

        // Step 10: Click Teams -> tabHistory = Calendar, Paddock, Teams
        backStack.appendMaster(NavTeamStandings)
        assertEquals(
            listOf<NavKey>(NavCalendar, NavPaddock, NavTeamStandings),
            backStack.toList()
        )

        // Step 11: Click Calendar -> tabHistory = Calendar
        backStack.appendMaster(NavCalendar)
        assertEquals(listOf<NavKey>(NavCalendar), backStack.toList())
    }

    @Test
    fun addOrMoveToTopList_reselectingTopListScreen_removesDetailAndDoesNotDuplicate() {
        val backStack = createBackStack(NavCalendar, NavDriverStandings)
        backStack.replaceDetail(NavDriver(season = 2024, id = "verstappen", name = "Max Verstappen"))
        assertEquals(3, backStack.size)

        // Reselecting current active tab
        backStack.appendMaster(NavDriverStandings)
        assertEquals(listOf<NavKey>(NavCalendar, NavDriverStandings), backStack.toList())
    }

    @Test
    fun addOrMoveToTopList_withRequiresSettings_includesNavSettings() {
        val backStack = createBackStack(NavCalendar)
        backStack.appendMaster(NavAbout)
        assertEquals(listOf<NavKey>(NavCalendar, NavSettings, NavAbout), backStack.toList())
    }

    @Test
    fun addOrMoveToTopList_withRequiresPaddock_movesExistingPaddockBeforeTarget() {
        val backStack = createBackStack(NavCalendar, NavPaddock, NavDriverStandings)
        backStack.appendMaster(NavSearch())
        assertEquals(listOf<NavKey>(NavCalendar, NavDriverStandings, NavPaddock, NavSearch()), backStack.toList())
    }

    @Test
    fun replaceList_clearsStackAndSetsTarget() {
        val backStack = createBackStack(NavCalendar, NavDriverStandings)
        backStack.replaceMaster(NavTeamStandings)
        assertEquals(listOf<NavKey>(NavTeamStandings), backStack.toList())
    }

    @Test
    fun addList_appendsListScreensOntoStack() {
        val backStack = createBackStack(NavCalendar)
        backStack.addMaster(NavDriverStandings)
        assertEquals(listOf<NavKey>(NavCalendar, NavDriverStandings), backStack.toList())
    }

    @Test
    fun pop_whenOnlyOneScreen_returnsFalseAndDoesNotPop() {
        val backStack = createBackStack(NavCalendar)
        val result = backStack.pop()
        assertFalse(result)
        assertEquals(listOf<NavKey>(NavCalendar), backStack.toList())
    }

    @Test
    fun pop_whenDetailIsPresent_removesDetailOnly() {
        val backStack = createBackStack(NavCalendar)
        backStack.replaceDetail(NavWeekend(season = 2024, round = 1, raceName = "Bahrain"))
        assertEquals(2, backStack.size)

        val result = backStack.pop()
        assertTrue(result)
        assertEquals(listOf<NavKey>(NavCalendar), backStack.toList())
    }

    @Test
    fun popList_removesDetailAndPopsTopListScreen() {
        val backStack = createBackStack(NavCalendar, NavDriverStandings)
        backStack.replaceDetail(NavDriver(season = 2024, id = "norris", name = "Lando Norris"))
        assertEquals(3, backStack.size)

        val result = backStack.popMaster()
        assertTrue(result)
        assertEquals(listOf<NavKey>(NavCalendar), backStack.toList())
    }
}
