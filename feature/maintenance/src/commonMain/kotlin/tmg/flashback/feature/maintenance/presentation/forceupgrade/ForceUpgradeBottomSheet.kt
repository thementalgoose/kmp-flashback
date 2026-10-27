package tmg.flashback.feature.maintenance.presentation.forceupgrade

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.window.core.layout.WindowSizeClass
import kotlinx.coroutines.launch
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForceUpgradeBottomSheet(
    hide: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val lockBottomSheetOnScreen = remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            if (it == SheetValue.Hidden) {
                !lockBottomSheetOnScreen.value
            } else {
                true
            }
        },
    )
    val coroutineScope = rememberCoroutineScope()
    ApplicationTheme {
        ModalBottomSheet(
            onDismissRequest = hide,
            sheetState = sheetState,
            containerColor = AppTheme.colors.surface,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = !lockBottomSheetOnScreen.value
            )
        ) {
            ForceUpgradeScreen(
                windowSizeClass = windowSizeClass,
                unlockAndDismiss = {
                    lockBottomSheetOnScreen.value = false
                    coroutineScope.launch {
                        sheetState.hide()
                        hide()
                    }
                },
            )
        }
    }
}
