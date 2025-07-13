package tmg.flashback.widgets.upnext.presentation

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tmg.flashback.data.repo.repository.EventRepository
import tmg.flashback.data.repo.repository.OverviewRepository
import tmg.flashback.formula1.model.OverviewRace
import tmg.flashback.widgets.upnext.repositories.UpNextWidgetRepository

data class UpNextConfiguration(
    val scheduleData: OverviewRace?,
    val deeplinkToEvent: Boolean,
    val showBackground: Boolean,
) {
    constructor(
        scheduleData: OverviewRace?,
        showBackground: Boolean,
        deeplinkToEvent: Boolean,
        context: Context
    ): this(
        showBackground = showBackground,
        scheduleData = scheduleData,
        deeplinkToEvent = deeplinkToEvent,
    )
}

class UpNextConfigurationDataStore(
    private val context: Context,
    private val upNextWidgetRepository: UpNextWidgetRepository,
    private val overviewRepository: OverviewRepository,
): DataStore<UpNextConfiguration> {

    private suspend fun getConfig(): UpNextConfiguration {
        val nextRace = overviewRepository.getUpcomingOverviews()?.minByOrNull { it.date }
        return UpNextConfiguration(
            scheduleData = nextRace,
            showBackground = upNextWidgetRepository.showBackground,
            deeplinkToEvent = upNextWidgetRepository.deeplinkToEvent,
            context = context
        )
    }

    override val data: Flow<UpNextConfiguration>
        get() = flow {
            emit(getConfig())
        }

    override suspend fun updateData(
        transform: suspend (t: UpNextConfiguration) -> UpNextConfiguration
    ): UpNextConfiguration {
        return transform(getConfig())
    }
}