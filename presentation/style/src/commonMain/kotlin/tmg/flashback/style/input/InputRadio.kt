package tmg.flashback.style.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme

@Composable
fun InputRadio(
    isChecked: Boolean,
    modifier: Modifier = Modifier
) {
    RadioButton(
        modifier = modifier,
        colors = RadioButtonDefaults.colors(),
        selected = isChecked,
        onClick = null
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                InputRadio(isChecked = true)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                InputRadio(isChecked = false)
            }
        }
    }
}