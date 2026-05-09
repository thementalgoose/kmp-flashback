package tmg.flashback.feature.season.presentation.calendar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import flashback.feature.season.generated.resources.Res
import flashback.feature.season.generated.resources.ic_collapsible_icon_bottom
import flashback.feature.season.generated.resources.ic_collapsible_icon_top
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_collapsed_section
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.feature.season.presentation.calendar.CalendarItem
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.flag.Flag

private val expandIcon = 20.dp

@Composable
internal fun CollapsableList(
    model: CalendarItem.GroupedCompletedRaces,
    itemClicked: (CalendarItem.GroupedCompletedRaces) -> Unit,
    modifier: Modifier = Modifier
) {
    val contentDescription = stringResource(resource = string.ab_collapsed_section,
        model.first.raceName,
        model.first.round,
        model.last?.raceName ?: model.first.raceName,
        model.last?.round ?: model.first.round
    )
    Row(modifier = modifier
        .height(IntrinsicSize.Min)
        .clickable { itemClicked(model) }
        .semantics(mergeDescendants = true) { }
        .clearAndSetSemantics { this.stateDescription = contentDescription }
        .padding(
            start = AppTheme.dimens.small,
            end = AppTheme.dimens.small,
            top = AppTheme.dimens.xsmall,
            bottom = AppTheme.dimens.xsmall
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Expand()

        Row(
            modifier = modifier
                .weight(1f)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .padding(
                    start = AppTheme.dimens.xsmall,
                    end = AppTheme.dimens.small,
                    top = AppTheme.dimens.small,
                    bottom = AppTheme.dimens.small,
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Flag(
                        iso = model.first.countryISO,
                        nationality = null,
                        modifier = Modifier.size(20.dp)
                    )
                    TextBody1(
                        modifier = Modifier
                            .padding(horizontal = AppTheme.dimens.small)
                            .weight(1f),
                        bold = true,
                        text = model.first.raceName
                    )
                    Round(model.first.round)
                }
                if (model.last != null) {
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Flag(
                            iso = model.last.countryISO,
                            nationality = null,
                            modifier = Modifier.size(20.dp)
                        )
                        TextBody1(
                            modifier = Modifier
                                .padding(horizontal = AppTheme.dimens.small)
                                .weight(1f),
                            bold = true,
                            text = model.last.raceName
                        )
                        Round(model.last.round)
                    }
                }
            }
        }

        Expand()
    }
}

@Composable
private fun Expand(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxHeight()
        .padding(vertical = AppTheme.dimens.xsmall)
    ) {
        Icon(
            painter = painterResource(resource = Res.drawable.ic_collapsible_icon_top),
            contentDescription = null,
            modifier = Modifier.size(expandIcon),
            tint = AppTheme.colors.onSurfaceVariant
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(resource = Res.drawable.ic_collapsible_icon_bottom),
            contentDescription = null,
            modifier = Modifier.size(expandIcon),
            tint = AppTheme.colors.onSurfaceVariant
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        CollapsableList(
            model = CalendarItem.GroupedCompletedRaces(
                first = OverviewRace.preview(),
                last = OverviewRace.preview()
            ),
            itemClicked = { }
        )
    }
}