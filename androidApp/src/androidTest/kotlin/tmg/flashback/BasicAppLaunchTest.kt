package tmg.flashback

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicAppLaunchTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun appLaunch_dismissOnboarding_homepageVisible()  {
        composeTestRule.waitUntil(timeoutMillis = 30_000) {
            composeTestRule
                .onAllNodesWithText("Continue")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("Continue")
            .performClick()

        composeTestRule.waitForIdle()

        println(composeTestRule
            .onNodeWithTag("App", useUnmergedTree = true)
            .printToString())
        composeTestRule
            .onNodeWithTag("App", useUnmergedTree = true)
            .printToLog("UITest")

        composeTestRule.waitUntil(timeoutMillis = 15_000) {
            composeTestRule
                .onAllNodesWithText(
                    text = "Australian Grand Prix",
                    substring = false,
                    ignoreCase = false,
                    useUnmergedTree = true
                )
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule
            .onNodeWithText(
                text = "Australian Grand Prix",
                substring = false,
                ignoreCase = false,
                useUnmergedTree = true
            )
            .assertExists()
    }
}
