package tmg.flashback.feature.constructors.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_circle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle

@Composable
fun ConstructorSeason(
    year: Int,
    drivers: List<Driver>,
    yearClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.small,
                vertical = AppTheme.dimens.xsmall
            )
            .clickable(onClick = { yearClicked(year) })
            .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
            .background(AppTheme.colors.surfaceContainer3)
            .padding(AppTheme.dimens.small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextTitle(
            modifier = Modifier.weight(1f),
            text = year.toString(),
            bold = true,
        )
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            drivers.forEach { driver ->
                TextBody1(
                    modifier = modifier,
                    text = driver.name
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        ConstructorSeason(
            year = 2020,
            drivers = listOf(Driver.preview()),
            yearClicked = { }
        )
    }
}
