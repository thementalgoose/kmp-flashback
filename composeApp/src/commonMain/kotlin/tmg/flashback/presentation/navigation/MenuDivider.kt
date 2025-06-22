package tmg.flashback.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tmg.flashback.style.AppTheme

@Composable
fun MenuDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.xsmall
        ),
        color = AppTheme.colors.surfaceContainer5
    )
}