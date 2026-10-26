package tmg.flashback.composeApp.presentation.navigation.scene

import androidx.navigation3.runtime.NavEntry
import androidx.window.core.layout.WindowSizeClass
import tmg.flashback.ui.navigation.NavigationType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SplitPaneSceneTest {

    @Test
    fun navigationType_isSides_returnsTrueOnlyForStartNavAndEndNav() {
        assertTrue(NavigationType.StartNav.isSides)
        assertTrue(NavigationType.EndNav.isSides)
        assertFalse(NavigationType.BottomNav.isSides)
        assertFalse(NavigationType.NavRail.isSides)
        assertFalse(NavigationType.NavOrbiter.isSides)
    }

    @Test
    fun splitPaneScene_metadata_keysMatch() {
        assertEquals(mapOf("SplitPaneScene-List" to true), SplitPaneScene.listPane())
        assertEquals(mapOf("SplitPaneScene-Detail" to true), SplitPaneScene.detailPane())
    }

    @Test
    fun listDetailSceneStrategy_returnsNullWhenWidthIsCompact() {
        val strategy = ListDetailSceneStrategy<String>(
            windowSizeClass = WindowSizeClass(widthDp = 400f, heightDp = 800f),
            navigationType = NavigationType.BottomNav
        )
        // strategy requires width expanded (>= 840dp)
        assertFalse(strategy.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND))
    }

    @Test
    fun listDetailSceneStrategy_expandedWidthCheck() {
        val strategy = ListDetailSceneStrategy<String>(
            windowSizeClass = WindowSizeClass(widthDp = 1000f, heightDp = 800f),
            navigationType = NavigationType.NavRail
        )
        assertTrue(strategy.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND))
    }
}
