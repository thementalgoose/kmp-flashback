package tmg.flashback.feature.circuits.presentation.all

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import tmg.flashback.feature.circuits.presentation.circuit.CircuitScreen
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane

data class CircuitNavigation(
    val circuitId: String,
    val circuitName: String,
)

@Composable
fun CircuitsGraph(
    paddingValues: PaddingValues,
    navigator: MasterDetailPaneState<CircuitNavigation>,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    MasterDetailsPane(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        master = {
            AllCircuitsScreen(
                paddingValues = paddingValues,
                actionUpClicked = actionUpClicked,
                windowSizeClass = windowSizeClass,
                circuitClicked = {
                    navigator.navigateTo(CircuitNavigation(
                        circuitId = it.id,
                        circuitName = it.name
                    ))
                }
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            CircuitScreen(
                circuitNavigation = model,
                paddingValues = paddingValues,
                actionUpClicked = actionUpClicked,
                showBack = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.EXPANDED,
                windowSizeClass = windowSizeClass
            )
        }
    )
}