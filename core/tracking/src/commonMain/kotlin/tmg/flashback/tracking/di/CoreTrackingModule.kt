package tmg.flashback.tracking.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tmg.flashback.tracking.manager.TrackingManager
import tmg.flashback.tracking.manager.TrackingManagerImpl

val coreTrackingModule = listOf(module(), platformModule())

internal fun module() = module {
    singleOf<TrackingManager>(::TrackingManagerImpl)
}