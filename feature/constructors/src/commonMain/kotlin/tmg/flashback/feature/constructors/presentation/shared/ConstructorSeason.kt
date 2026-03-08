package tmg.flashback.feature.constructors.presentation.shared

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
import flashback.domain.formula1.generated.resources.ic_driver
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_circle
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle

@Composable
fun ConstructorSeason(
    year: Int,
    drivers: Map<Int?, Driver>,
    standing: Int?,
    yearClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.small
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
            drivers.forEach { (standing, driver) ->
                Row {
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
                    TextBody1(
                        text = driver.name
                    )
                }
            }
        }
    }
}


@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        ConstructorSeason(
            year = 2020,
            drivers = mapOf(1 to Driver.preview()),
            standing = 1,
            yearClicked = { }
        )
    }
}
