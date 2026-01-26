package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.ic_championship_order
import flashback.domain.formula1.generated.resources.ic_driver
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_circle
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextTitle

@Composable
fun DriverTeam(
    year: Int,
    constructors: List<Constructor>,
    yearClicked: (Int) -> Unit,
    standing: Int? = null,
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
            text = year.toString(),
            bold = true,
        )
        if (standing == 1) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimens.xsmall)
                    .size(12.dp),
                painter = painterResource(flashback.domain.formula1.generated.resources.Res.drawable.ic_driver),
                contentDescription = null,
                tint = AppTheme.colors.f1Championship
            )
        }
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.weight(2f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            constructors.forEach { constructor ->
                BadgeView(
                    modifier = modifier,
                    model = Badge(
                        label = constructor.name,
                        icon = Res.drawable.ic_circle
                    ),
                    tintIcon = constructor.colour
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
        DriverTeam(
            year = 2020,
            constructors = listOf(Constructor.preview()),
            yearClicked = { }
        )
    }
}
