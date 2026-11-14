package tmg.flashback.feature.weekend.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.dashboard_up_next_starts_in
import flashback.presentation.localisation.generated.resources.weekend_starts_in
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.plusMinutes
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.timer.RelativeTimer
import tmg.flashback.ui.components.timer.Timer

@Composable
fun DataPending(
    eventDate: LocalDate,
    eventTime: LocalTime,
    tabName: String,
    modifier: Modifier = Modifier,
) {
    Timer(
        targetDate = eventDate,
        targetTime = eventTime,
        countdownContent = { d, h, m, s ->
            Column(
                modifier = modifier
                    .padding(
                        horizontal = AppTheme.dimens.medium,
                        vertical = AppTheme.dimens.small
                    ),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
            ) {
                TextBody1(
                    text = stringResource(string.weekend_starts_in, tabName)
                )
                RelativeTimer(
                    days = d,
                    hours = h,
                    minutes = m,
                    seconds = s
                )
            }
        },
        onFinishedContent = {

        }
    )
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        DataPending(
            tabName = "Qualifying",
            eventDate = LocalDate.now(),
            eventTime = LocalTime.now().plusMinutes(5)
        )
    }
}