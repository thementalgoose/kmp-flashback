package tmg.flashback.feature.weekend.presentation.data.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.domain.formula1.generated.resources.weather_indicator_rain
import flashback.domain.formula1.generated.resources.weather_indicator_temp
import flashback.domain.formula1.generated.resources.weather_indicator_wind
import flashback.domain.formula1.generated.resources.weather_unknown
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_change_of_rain
import flashback.presentation.localisation.generated.resources.ab_notifications_enabled
import flashback.presentation.localisation.generated.resources.ab_schedule_date_card
import flashback.presentation.localisation.generated.resources.ab_schedule_date_card_notifications_enabled
import flashback.presentation.localisation.generated.resources.empty
import flashback.presentation.localisation.generated.resources.weather_rain_percent
import flashback.presentation.localisation.generated.resources.weather_temp_degrees_c
import flashback.presentation.localisation.generated.resources.weather_temp_degrees_f
import flashback.presentation.localisation.generated.resources.weather_wind_kph
import flashback.presentation.localisation.generated.resources.weather_wind_mph
import flashback.presentation.localisation.generated.resources.weekend_race_round
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.ic_notification_indicator_bell
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.model.Schedule
import tmg.flashback.formula1.model.ScheduleWeather
import tmg.flashback.infrastructure.datetime.dateFormatDMMM
import tmg.flashback.infrastructure.datetime.dateFormatEEEEDMMM
import tmg.flashback.infrastructure.datetime.now
import tmg.flashback.infrastructure.datetime.timeFormatHHmm
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.edgeFade
import tmg.flashback.ui.components.flag.Flag
import kotlin.math.roundToInt

private val trackSize: Dp = 200.dp
private val weatherIconSize: Dp = 48.dp
private val weatherMetadataIconSize: Dp = 20.dp

@Composable
internal fun RaceDetails(
    model: InfoModel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            TextBody1(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = AppTheme.dimens.xsmall,
                        bottom = AppTheme.dimens.xsmall
                    ),
                text = model.circuit.name
            )
            TextBody1(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppTheme.dimens.xsmall),
                text = model.circuit.country
            )
            TextBody2(
                bold = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppTheme.dimens.xsmall),
                text = model.date.format(dateFormatDMMM)
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Flag(
                iso = model.circuit.countryISO,
                nationality = model.circuit.country,
                modifier = Modifier.size(48.dp),
            )
            TextBody2(
                text = stringResource(resource = string.weekend_race_round, model.round),
                bold = true,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}


@Composable
internal fun Schedule(
    model: InfoModel,
    modifier: Modifier = Modifier
) {
    if (model.days.isNotEmpty()) {

        var targetIndex = model.days
            .indexOfFirst { it.first == LocalDate.now() }
        if (targetIndex == -1) targetIndex = 0
        val scrollState = rememberLazyListState(
            initialFirstVisibleItemIndex = targetIndex.coerceIn(0, model.days.size - 1)
        )

        LazyRow(
            modifier = modifier
                .padding(bottom = AppTheme.dimens.small)
                .edgeFade(),
            state = scrollState,
            content = {
                items(model.days) { (date, list) ->
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                top = AppTheme.dimens.xsmall,
                                start = AppTheme.dimens.medium,
                                end = AppTheme.dimens.medium
                            )
                    ) {
                        Title(date)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
                        ) {
                            list.forEach { (schedule, isNotificationSet) ->
                                EventItem(
                                    item = schedule,
                                    temperatureMetric = model.temperatureMetric,
                                    windspeedMetric = model.windspeedMetric,
                                    showNotificationBell = isNotificationSet
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun Title(
    date: LocalDate,
    modifier: Modifier = Modifier
) {
    TextBody1(
        text = date.format(dateFormatEEEEDMMM),
        bold = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.small
            )
    )
}

@Composable
private fun EventItem(
    item: Schedule,
    temperatureMetric: Boolean,
    windspeedMetric: Boolean,
    showNotificationBell: Boolean,
    modifier: Modifier = Modifier
) {
    val timestamp = item.timestamp.deviceLocalDateTime.time.format(timeFormatHHmm)
    Column(modifier = modifier
        .padding(vertical = AppTheme.dimens.xsmall)
    ) {
        val contentDescription = when (showNotificationBell) {
            true -> stringResource(resource = string.ab_schedule_date_card_notifications_enabled, item.label, timestamp)
            false -> stringResource(resource = string.ab_schedule_date_card, item.label, timestamp)
        }
        Column(
            modifier = Modifier
                .semantics(mergeDescendants = true) { }
                .clearAndSetSemantics {
                    this.contentDescription = contentDescription
                }
                .clip(RoundedCornerShape(AppTheme.dimens.radiusSmall))
                .background(AppTheme.colors.surfaceContainer3)
                .padding(
                    vertical = AppTheme.dimens.small,
                    horizontal = AppTheme.dimens.nsmall
                )
        ) {
            Row {
                TextBody1(
                    text = item.label
                )
                if (showNotificationBell) {
                    Spacer(Modifier.width(AppTheme.dimens.small))
                    Icon(
                        painter = painterResource(resource = Res.drawable.ic_notification_indicator_bell),
                        contentDescription = stringResource(resource = string.ab_notifications_enabled),
                        tint = AppTheme.colors.onSurface,
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            TextTitle(
                modifier = Modifier.padding(top = AppTheme.dimens.small),
                text = timestamp,
                bold = true
            )
        }

        Spacer(Modifier.height(AppTheme.dimens.xsmall))
        item.weather?.let { weather ->
            Column(
                modifier = Modifier.padding(
                    start = AppTheme.dimens.xsmall,
                    end = AppTheme.dimens.xsmall,
                    bottom = AppTheme.dimens.xsmall
                ),
                horizontalAlignment = Alignment.Start
            ) {
                val summary = weather.summary.firstOrNull()
                Image(
                    painter = painterResource(resource = summary?.icon ?: flashback.domain.formula1.generated.resources.Res.drawable.weather_unknown),
                    contentDescription = stringResource(resource = summary?.label ?: string.empty),
                    modifier = Modifier.size(weatherIconSize)
                )

                // Rain Percentage
                val rainPercent = (weather.rainPercent * 100).roundToInt().coerceIn(0, 100)
                Row {
                    Image(
                        modifier = Modifier.size(weatherMetadataIconSize),
                        painter = painterResource(resource = flashback.domain.formula1.generated.resources.Res.drawable.weather_indicator_rain),
                        contentDescription = stringResource(resource = string.ab_change_of_rain)
                    )
                    TextBody2(
                        textColor = AppTheme.colors.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 2.dp),
                        text = stringResource(resource = string.weather_rain_percent, rainPercent.toString())
                    )
                }

                // Temp
                Row(Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.size(weatherMetadataIconSize),
                        painter = painterResource(resource = flashback.domain.formula1.generated.resources.Res.drawable.weather_indicator_temp),
                        contentDescription = null
                    )
                    TextBody2(
                        textColor = AppTheme.colors.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 2.dp),
                        text = weather.getAverageTemp(metric = temperatureMetric)
                    )
                }

                // Wind
                val windSpeed = weather.windMph.toFloat()
                Row(Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.size(weatherMetadataIconSize),
                        painter = painterResource(resource = flashback.domain.formula1.generated.resources.Res.drawable.weather_indicator_wind),
                        contentDescription = null
                    )
                    TextBody2(
                        textColor = AppTheme.colors.onSurfaceVariant,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 2.dp),
                        text = weather.getWindspeed(metric = windspeedMetric)
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleWeather.getAverageTemp(metric: Boolean): String = when (metric) {
    true -> stringResource(resource = string.weather_temp_degrees_c, this.tempAverageC.toFloat().roundToInt().toString())
    false -> stringResource(resource = string.weather_temp_degrees_f, this.tempAverageF.toFloat().roundToInt().toString())
}

@Composable
private fun ScheduleWeather.getWindspeed(metric: Boolean): String = when (metric) {
    true -> stringResource(resource = string.weather_wind_kph, this.windKph.toFloat().roundToInt())
    false -> stringResource(resource = string.weather_wind_mph, this.windMph.toFloat().roundToInt())
}


@Preview
@Composable
private fun PreviewSchedule(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            Schedule(
                model = InfoModel.preview()
            )
        }
    }
}


@Preview
@Composable
private fun PreviewDetails(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        Column {
            RaceDetails(
                model = InfoModel.preview()
            )
        }
    }
}