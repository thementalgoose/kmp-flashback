package tmg.flashback.navigation

import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val saveStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(Screen::class) {
            subclass(Screen.Calendar::class, Screen.Calendar.serializer())
            subclass(Screen.DriverStandings::class, Screen.DriverStandings.serializer())
            subclass(Screen.TeamStandings::class, Screen.TeamStandings.serializer())
            subclass(Screen.Circuits::class, Screen.Circuits.serializer())

            subclass(Screen.Weekend::class, Screen.Weekend.serializer())
            subclass(Screen.Circuit::class, Screen.Circuit.serializer())
            subclass(Screen.Driver::class, Screen.Driver.serializer())
            subclass(Screen.Team::class, Screen.Team.serializer())
            subclass(Screen.DriverComparison::class, Screen.DriverComparison.serializer())

            subclass(Screen.Rss::class, Screen.Rss.serializer())
            subclass(Screen.Webpage::class, Screen.Webpage.serializer())

            subclass(Screen.ReactionGame::class, Screen.ReactionGame.serializer())

            subclass(Screen.Settings::class, Screen.Settings.serializer())
            subclass(Screen.SettingsDarkMode::class, Screen.SettingsDarkMode.serializer())
            subclass(Screen.SettingsTheme::class, Screen.SettingsTheme.serializer())
            subclass(Screen.SettingsLayout::class, Screen.SettingsLayout.serializer())
            subclass(Screen.SettingsWeather::class, Screen.SettingsWeather.serializer())
            subclass(Screen.SettingsRssConfigure::class, Screen.SettingsRssConfigure.serializer())
            subclass(Screen.SettingsInAppBrowser::class, Screen.SettingsInAppBrowser.serializer())
            subclass(Screen.SettingsNotificationResults::class, Screen.SettingsNotificationResults.serializer())
            subclass(Screen.SettingsNotificationUpcoming::class, Screen.SettingsNotificationUpcoming.serializer())
            subclass(Screen.SettingsWidgets::class, Screen.SettingsWidgets.serializer())
            subclass(Screen.SettingsPrivacy::class, Screen.SettingsPrivacy.serializer())
            subclass(Screen.PrivacyPolicy::class, Screen.PrivacyPolicy.serializer())

            subclass(Screen.About::class, Screen.About.serializer())
        }
    }
}