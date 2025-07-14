package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.nav_constructors
import flashback.presentation.localisation.generated.resources.nav_drivers
import tmg.flashback.style.buttons.ButtonItem
import tmg.flashback.style.buttons.Segments

private val buttonItemDriver = ButtonItem(key = "drivers", string = string.nav_drivers)
private val buttonItemTeams = ButtonItem(key = "teams", string = string.nav_constructors)

@Composable
fun DriverTeamSwitcher(
    isDrivers: Boolean,
    driversClicked: () -> Unit,
    teamsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Segments(
        modifier = modifier,
        items = listOf(buttonItemDriver, buttonItemTeams),
        selected = if (isDrivers) buttonItemDriver else buttonItemTeams,
        segmentClicked = {
            when (it) {
                buttonItemDriver -> driversClicked()
                buttonItemTeams -> teamsClicked()
            }
        },
        showTick = true
    )
}