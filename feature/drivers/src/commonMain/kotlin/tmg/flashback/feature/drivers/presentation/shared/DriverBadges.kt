package tmg.flashback.feature.drivers.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.ic_driver_birthday
import flashback.domain.formula1.generated.resources.ic_driver_code
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_circle
import kotlinx.datetime.format
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.infrastructure.datetime.dateFormatDMMMYYYY
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.badge.Badge
import tmg.flashback.style.badge.BadgeView
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.flag.getFlagVector

@Composable
fun DriverBadges(
    driver: Driver,
    constructors: List<Constructor>
) {
    FlowRow(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {

        val flagVector = getFlagVector(driver.nationalityISO)
        NationalityBadge(
            label = driver.nationality,
            imageVector = flagVector,
        )

        val birthday = driver.dateOfBirth.format(dateFormatDMMMYYYY)
        BadgeView(model = Badge(label = birthday, icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_driver_birthday))

        if (driver.code != null && driver.number != null) {
            BadgeView(
                model = Badge(
                    label = "${driver.code} ${driver.number}",
                    icon = flashback.domain.formula1.generated.resources.Res.drawable.ic_driver_code
                )
            )
        }

        constructors.forEach {
            ConstructorBadge(it)
        }
    }
}

@Composable
internal fun ConstructorBadge(
    constructor: Constructor,
    modifier: Modifier = Modifier
) {
    BadgeView(
        modifier = modifier,
        model = Badge(
            label = constructor.name,
            icon = Res.drawable.ic_circle
        ),
        tintIcon = constructor.colour
    )
}

@Composable
private fun NationalityBadge(
    label: String,
    imageVector: ImageVector?,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
        .background(AppTheme.colors.surfaceContainer2)
        .border(
            1.dp,
            color = AppTheme.colors.outline.copy(alpha = 0.35f),
            shape = RoundedCornerShape(AppTheme.dimens.radiusSmall)
        )
        .padding(
            horizontal = AppTheme.dimens.small,
            vertical = AppTheme.dimens.xsmall
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageVector != null) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(6.dp))
        }
        TextBody2(
            text = label,
            bold = true
        )
    }
}

@Preview
@Composable
private fun PreviewAlt(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        Column {
            DriverBadges(
                driver = Driver.preview(),
                constructors = listOf(Constructor.preview())
            )
        }
    }
}
