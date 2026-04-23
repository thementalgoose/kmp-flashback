package tmg.flashback.widgets.upnext.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.Action
import androidx.glance.action.action
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.Column
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.unit.ColorProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.presentation.layouts.NoRace
import tmg.flashback.widgets.upnext.presentation.layouts.RaceIcon
import tmg.flashback.widgets.upnext.presentation.layouts.RaceName
import tmg.flashback.widgets.upnext.presentation.layouts.RaceOverview
import tmg.flashback.widgets.upnext.presentation.layouts.RaceSchedule
import tmg.flashback.widgets.upnext.presentation.layouts.raceNameHeight
import tmg.flashback.widgets.upnext.presentation.layouts.raceNameWidth
import tmg.flashback.widgets.upnext.presentation.layouts.raceOverviewHeight
import tmg.flashback.widgets.upnext.presentation.layouts.raceOverviewWidth
import tmg.flashback.widgets.upnext.presentation.layouts.raceScheduleHeight
import tmg.flashback.widgets.upnext.presentation.layouts.raceScheduleWidth
import tmg.flashback.widgets.upnext.presentation.preview.fakeOverviewRace
import tmg.flashback.widgets.upnext.presentation.preview.fakeSprintWeekend
import tmg.flashback.widgets.upnext.presentation.style.WidgetTheme
import tmg.flashback.widgets.upnext.presentation.style.WidgetThemePreview
import tmg.flashback.widgets.upnext.presentation.style.modifiers.surface
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewAllSizes
import tmg.flashback.widgets.upnext.presentation.style.preview.PreviewPixel
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository
import java.io.File

class UpNextWidget : GlanceAppWidget(), KoinComponent {

    override val sizeMode = SizeMode.Exact

    private val upNextWidgetRepository by inject<UpNextWidgetRepository>()
    private val overviewRepository by inject<OverviewRepository>()

    override val stateDefinition: GlanceStateDefinition<UpNextConfiguration>
        get() = object : GlanceStateDefinition<UpNextConfiguration> {
            override suspend fun getDataStore(
                context: Context,
                fileKey: String
            ): DataStore<UpNextConfiguration> {
                return UpNextConfigurationDataStore(
                    context = context,
                    upNextWidgetRepository = upNextWidgetRepository,
                    overviewRepository = overviewRepository,
                )
            }

            override fun getLocation(context: Context, fileKey: String): File {
                throw NotImplementedError("Not implemented")
            }
        }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        Log.d("UpNextWidget", "provideGlance $id")
        provideContent {
            WidgetTheme {
                Log.d("UpNextWidget", "provideGlance -> provideContent $id")
                val upNextConfiguration: UpNextConfiguration = currentState()
                Content(upNextConfiguration)
            }
        }
    }

    @Composable
    private fun Content(
        upNextConfiguration: UpNextConfiguration
    ) {
        val context = LocalContext.current
        if (upNextConfiguration.scheduleData != null) {
            WidgetContent(
                overviewRace = upNextConfiguration.scheduleData,
                shouldDeeplink = upNextConfiguration.deeplinkToEvent,
                showBackground = upNextConfiguration.showBackground,
                clickAction = actionStartActivity(context.getHomeIntent())
            )
        } else {
            Log.i("UpNextWidget", "No race found, showing fallback")
//            NoRace(
//                context = context,
//                modifier = GlanceModifier.clickable(actionRunCallback<UpNextWidgetRefreshWidget>()),
//            )
        }

        Log.i("UpNextWidget", "provideFlance finished")
    }
}

private fun Context.getHomeIntent(): Intent {
    return Intent(this, Class.forName("tmg.flashback.MainActivity")).apply {
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
}

@Composable
private fun WidgetContent(
    overviewRace: OverviewRace,
    shouldDeeplink: Boolean,
    showBackground: Boolean,
    clickAction: Action
) {
    val modifier = when (shouldDeeplink) {
        true -> GlanceModifier
            .surface(if (showBackground) GlanceTheme.colors.widgetBackground else ColorProvider(Color.Transparent))
            .clickable(clickAction)
        false -> GlanceModifier
            .surface(if (showBackground) GlanceTheme.colors.widgetBackground else ColorProvider(Color.Transparent))
            .clickable(clickAction)
    }
    val size = LocalSize.current
    Column(modifier = modifier) {
        when {
            size.width >= raceOverviewWidth.dp && size.height >= raceOverviewHeight.dp ->
                RaceOverview(
                    overviewRace = overviewRace,
                    localSize = size,
                )
            size.width >= raceScheduleWidth.dp && size.height >= raceScheduleHeight.dp ->
                RaceSchedule(
                    overviewRace = overviewRace,
                )
            size.width >= raceNameWidth.dp && size.height >= raceNameHeight.dp ->
                RaceName(
                    overviewRace = overviewRace,
                    localSize = size,
                )
            else -> {
                RaceIcon(
                    overviewRace = overviewRace,
                    localSize = size,
                )
            }
        }
    }
}



@Composable
@PreviewPixel
private fun PreviewPixelOverview() {
    WidgetThemePreview {
        WidgetContent(
            overviewRace = fakeOverviewRace,
            shouldDeeplink = false,
            showBackground = true,
            clickAction = action { }
        )
    }
}

@Composable
@PreviewPixel
private fun PreviewPixelSprint() {
    WidgetThemePreview {
        WidgetContent(
            overviewRace = fakeSprintWeekend,
            shouldDeeplink = false,
            showBackground = true,
            clickAction = action { }
        )
    }
}

@Composable
@PreviewPixel
private fun PreviewPixelNoRace() {
    WidgetThemePreview {
        NoRace(
            titleCompact = "N/A",
            title = "Nothing coming up",
            subtitle = "We will get this information as soon as it\\'s available. Click here to check for updates"
        )
    }
}

@Composable
@PreviewAllSizes
private fun PreviewMinMaxOverview() {
    WidgetThemePreview {
        WidgetContent(
            overviewRace = fakeOverviewRace,
            shouldDeeplink = false,
            showBackground = true,
            clickAction = action { }
        )
    }
}

@Composable
@PreviewAllSizes
private fun PreviewMinMaxSprint() {
    WidgetThemePreview {
        WidgetContent(
            overviewRace = fakeSprintWeekend,
            shouldDeeplink = false,
            showBackground = true,
            clickAction = action { }
        )
    }
}

@Composable
@PreviewAllSizes
private fun PreviewMinMaxNoRace() {
    WidgetThemePreview {
        NoRace(
            titleCompact = "N/A",
            title = "Nothing coming up",
            subtitle = "We will get this information as soon as it\\'s available. Click here to check for updates"
        )
    }
}