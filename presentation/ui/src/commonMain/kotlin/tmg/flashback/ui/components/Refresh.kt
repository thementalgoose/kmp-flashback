package tmg.flashback.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_refresh
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_refresh
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun Refresh(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(Res.drawable.ic_refresh),
                contentDescription = stringResource(string.ab_refresh),
                tint = AppTheme.colors.onSurface
            )
        }
    )
}


@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Refresh(
            onClick = { }
        )
    }
}

