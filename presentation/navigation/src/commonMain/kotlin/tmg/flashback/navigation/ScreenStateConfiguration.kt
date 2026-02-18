package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val saveStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(NavCalendar::class, NavCalendar.serializer())
            subclass(NavDriverStandings::class, NavDriverStandings.serializer())
            subclass(NavTeamStandings::class, NavTeamStandings.serializer())
            subclass(NavCircuits::class, NavCircuits.serializer())

            subclass(NavWeekend::class, NavWeekend.serializer())
            subclass(NavCircuit::class, NavCircuit.serializer())
            subclass(NavDriver::class, NavDriver.serializer())
            subclass(NavTeam::class, NavTeam.serializer())
            subclass(NavDriverComparison::class, NavDriverComparison.serializer())

            subclass(NavRss::class, NavRss.serializer())
            subclass(NavWebpage::class, NavWebpage.serializer())

            subclass(NavReactionGame::class, NavReactionGame.serializer())

            subclass(NavSettings::class, NavSettings.serializer())
            subclass(NavSettingsDarkMode::class, NavSettingsDarkMode.serializer())
            subclass(NavSettingsTheme::class, NavSettingsTheme.serializer())
            subclass(NavSettingsLayout::class, NavSettingsLayout.serializer())
            subclass(NavSettingsWeather::class, NavSettingsWeather.serializer())
            subclass(NavSettingsRssConfigure::class, NavSettingsRssConfigure.serializer())
            subclass(NavSettingsInAppBrowser::class, NavSettingsInAppBrowser.serializer())
            subclass(NavSettingsNotificationResults::class, NavSettingsNotificationResults.serializer())
            subclass(NavSettingsNotificationUpcoming::class, NavSettingsNotificationUpcoming.serializer())
            subclass(NavSettingsWidgets::class, NavSettingsWidgets.serializer())
            subclass(NavSettingsPrivacy::class, NavSettingsPrivacy.serializer())
            subclass(NavPrivacyPolicy::class, NavPrivacyPolicy.serializer())

            subclass(NavAbout::class, NavAbout.serializer())
        }
    }
}