package tmg.flashback.presentation.settings

import flashback.composeapp.generated.resources.Res
import flashback.composeapp.generated.resources.ic_settings_about
import flashback.composeapp.generated.resources.ic_settings_dark_mode
import flashback.composeapp.generated.resources.ic_settings_home
import flashback.composeapp.generated.resources.ic_settings_notifications_results
import flashback.composeapp.generated.resources.ic_settings_notifications_upcoming
import flashback.composeapp.generated.resources.ic_settings_privacy
import flashback.composeapp.generated.resources.ic_settings_rss_configure
import flashback.composeapp.generated.resources.ic_settings_theme
import flashback.composeapp.generated.resources.ic_settings_weather
import flashback.composeapp.generated.resources.ic_settings_web
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_build_version
import flashback.presentation.localisation.generated.resources.settings_pref_analytics_description
import flashback.presentation.localisation.generated.resources.settings_pref_analytics_title
import flashback.presentation.localisation.generated.resources.settings_pref_collapsed_list_description
import flashback.presentation.localisation.generated.resources.settings_pref_collapsed_list_title
import flashback.presentation.localisation.generated.resources.settings_pref_crash_reporting_description
import flashback.presentation.localisation.generated.resources.settings_pref_crash_reporting_title
import flashback.presentation.localisation.generated.resources.settings_pref_empty_week_description
import flashback.presentation.localisation.generated.resources.settings_pref_empty_week_title
import flashback.presentation.localisation.generated.resources.settings_pref_notification_notice_period_description
import flashback.presentation.localisation.generated.resources.settings_pref_notification_notice_period_title
import flashback.presentation.localisation.generated.resources.settings_pref_privacy_policy_description
import flashback.presentation.localisation.generated.resources.settings_pref_privacy_policy_title
import flashback.presentation.localisation.generated.resources.settings_pref_recent_highlights_description
import flashback.presentation.localisation.generated.resources.settings_pref_recent_highlights_title
import flashback.presentation.localisation.generated.resources.settings_pref_remember_season_change_description
import flashback.presentation.localisation.generated.resources.settings_pref_remember_season_change_title
import flashback.presentation.localisation.generated.resources.settings_pref_reset_description
import flashback.presentation.localisation.generated.resources.settings_pref_reset_title
import flashback.presentation.localisation.generated.resources.settings_pref_shake_to_report_description
import flashback.presentation.localisation.generated.resources.settings_pref_shake_to_report_title
import flashback.presentation.localisation.generated.resources.settings_pref_temperature_unit_description
import flashback.presentation.localisation.generated.resources.settings_pref_temperature_unit_title
import flashback.presentation.localisation.generated.resources.settings_pref_wind_speed_unit_description
import flashback.presentation.localisation.generated.resources.settings_pref_wind_speed_unit_title
import flashback.presentation.localisation.generated.resources.settings_section_about_description
import flashback.presentation.localisation.generated.resources.settings_section_about_title
import flashback.presentation.localisation.generated.resources.settings_section_dark_mode_description
import flashback.presentation.localisation.generated.resources.settings_section_dark_mode_title
import flashback.presentation.localisation.generated.resources.settings_section_home_description
import flashback.presentation.localisation.generated.resources.settings_section_home_title
import flashback.presentation.localisation.generated.resources.settings_section_notifications_results_description
import flashback.presentation.localisation.generated.resources.settings_section_notifications_results_title
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_description
import flashback.presentation.localisation.generated.resources.settings_section_notifications_upcoming_title
import flashback.presentation.localisation.generated.resources.settings_section_privacy_description
import flashback.presentation.localisation.generated.resources.settings_section_privacy_title
import flashback.presentation.localisation.generated.resources.settings_section_rss_configure_description
import flashback.presentation.localisation.generated.resources.settings_section_rss_configure_title
import flashback.presentation.localisation.generated.resources.settings_section_weather_description
import flashback.presentation.localisation.generated.resources.settings_section_weather_title
import flashback.presentation.localisation.generated.resources.settings_section_web_browser_description
import flashback.presentation.localisation.generated.resources.settings_section_web_browser_title
import flashback.presentation.localisation.generated.resources.settings_switch_about_this_app_description
import flashback.presentation.localisation.generated.resources.settings_switch_about_this_app_title
import flashback.presentation.localisation.generated.resources.settings_switch_enable_javascript_description
import flashback.presentation.localisation.generated.resources.settings_switch_enable_javascript_title
import flashback.presentation.localisation.generated.resources.settings_switch_enable_web_browser_description
import flashback.presentation.localisation.generated.resources.settings_switch_enable_web_browser_title
import flashback.presentation.localisation.generated.resources.settings_switch_review_description
import flashback.presentation.localisation.generated.resources.settings_switch_review_title
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_dark
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_follow_system
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_light
import flashback.presentation.localisation.generated.resources.settings_theme_theme_default
import flashback.presentation.localisation.generated.resources.settings_theme_theme_description
import flashback.presentation.localisation.generated.resources.settings_theme_theme_material_you
import flashback.presentation.localisation.generated.resources.settings_theme_theme_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

object Settings {
    val DarkModeCategory = Setting.Category(
        _id = "dark_mode",
        title = string.settings_section_dark_mode_title,
        subtitle = string.settings_section_dark_mode_description,
        icon = Res.drawable.ic_settings_dark_mode
    )
    object DarkMode {
        val DarkModeAuto = Setting.Link(
            _id = "dark_mode_auto",
            title = string.settings_theme_nightmode_follow_system,
            subtitle = string.settings_theme_nightmode_follow_system
        )
        val DarkModeLight = Setting.Link(
            _id = "dark_mode_light",
            title = string.settings_theme_nightmode_light,
            subtitle = string.settings_theme_nightmode_light
        )
        val DarkModeDark = Setting.Link(
            _id = "dark_mode_dark",
            title = string.settings_theme_nightmode_dark,
            subtitle = string.settings_theme_nightmode_dark
        )
    }

    val ThemeCategory = Setting.Category(
        _id = "theme",
        title = string.settings_theme_theme_title,
        subtitle = string.settings_theme_theme_description,
        icon = Res.drawable.ic_settings_theme
    )
    object Theme {
        val ThemeDefault = Setting.Link(
            _id = "theme_default",
            title = string.settings_theme_theme_default,
            subtitle = string.settings_theme_theme_default
        )
        val ThemeMaterialYou = Setting.Link(
            _id = "theme_material_you",
            title = string.settings_theme_theme_material_you,
            subtitle = string.settings_theme_theme_material_you
        )
    }

    val LayoutCategory = Setting.Category(
        _id = "layout",
        title = string.settings_section_home_title,
        subtitle = string.settings_section_home_description,
        icon = Res.drawable.ic_settings_home
    )
    object Layout {
        val RecentHighlights = Setting.Link(
            _id = "layout_recents",
            title = string.settings_pref_recent_highlights_title,
            subtitle = string.settings_pref_recent_highlights_description
        )
        val CollapsibleRaces = Setting.Link(
            _id = "layout_recents",
            title = string.settings_pref_collapsed_list_title,
            subtitle = string.settings_pref_collapsed_list_description
        )
        val EmptyWeeks = Setting.Link(
            _id = "layout_emptyweeks",
            title = string.settings_pref_empty_week_title,
            subtitle = string.settings_pref_empty_week_description
        )
        val KeepSeason = Setting.Link(
            _id = "layout_keepseason",
            title = string.settings_pref_remember_season_change_title,
            subtitle = string.settings_pref_remember_season_change_description
        )
    }

    val WeatherCategory = Setting.Category(
        _id = "weather",
        title = string.settings_section_weather_title,
        subtitle = string.settings_section_weather_description,
        icon = Res.drawable.ic_settings_weather
    )
    object Weather {
        val TemperatureUnit = Setting.Link(
            _id = "weather_temp",
            title = string.settings_pref_temperature_unit_title,
            subtitle = string.settings_pref_temperature_unit_description
        )
        val WindSpeed = Setting.Link(
            _id = "weather_wind",
            title = string.settings_pref_wind_speed_unit_title,
            subtitle = string.settings_pref_wind_speed_unit_description
        )
    }

    val RssCategory = Setting.Category(
        _id = "theme",
        title = string.settings_section_rss_configure_title,
        subtitle = string.settings_section_rss_configure_description,
        icon = Res.drawable.ic_settings_rss_configure
    )
    object Rss {

    }

    val WebBrowserCategory = Setting.Category(
        _id = "webbrowser",
        title = string.settings_section_web_browser_title,
        subtitle = string.settings_section_web_browser_description,
        icon = Res.drawable.ic_settings_web
    )
    object WebBrowser {
        val Enabled = Setting.Link(
            _id = "webbrowser_enabled",
            title = string.settings_switch_enable_web_browser_title,
            subtitle = string.settings_switch_enable_web_browser_description
        )
        val EnableJavascript = Setting.Link(
            _id = "webbrowser_javascript",
            title = string.settings_switch_enable_javascript_title,
            subtitle = string.settings_switch_enable_javascript_description
        )
    }

    val NotificationsRaceCategory = Setting.Category(
        _id = "notifications_race",
        title = string.settings_section_notifications_results_title,
        subtitle = string.settings_section_notifications_results_description,
        icon = Res.drawable.ic_settings_notifications_results
    )

    val NotificationsUpcomingCategory = Setting.Category(
        _id = "notifications_upcoming",
        title = string.settings_section_notifications_upcoming_title,
        subtitle = string.settings_section_notifications_upcoming_description,
        icon = Res.drawable.ic_settings_notifications_upcoming
    )

    val NotificationsUpcomingNotice = Setting.Category(
        _id = "notifications_upcomingnotice",
        title = string.settings_pref_notification_notice_period_title,
        subtitle = string.settings_pref_notification_notice_period_description,
    )

    val PrivacyCategory = Setting.Category(
        _id = "privacy",
        title = string.settings_section_privacy_title,
        subtitle = string.settings_section_privacy_description,
        icon = Res.drawable.ic_settings_privacy
    )
    object Privacy {
        val PrivacyPolicy = Setting.Link(
            _id = "privacy_policy",
            title = string.settings_pref_privacy_policy_title,
            subtitle = string.settings_pref_privacy_policy_description
        )
        val CrashReporting = Setting.Link(
            _id = "privacy_policy",
            title = string.settings_pref_crash_reporting_title,
            subtitle = string.settings_pref_crash_reporting_description
        )
        val Analytics = Setting.Link(
            _id = "privacy_policy",
            title = string.settings_pref_analytics_title,
            subtitle = string.settings_pref_analytics_description
        )
    }

    val AboutCategory = Setting.Category(
        _id = "about",
        title = string.settings_section_about_title,
        subtitle = string.settings_section_about_description,
        icon = Res.drawable.ic_settings_about
    )
    object About {
        val AboutThisApp = Setting.Link(
            _id = "about_aboutthisapp",
            title = string.settings_switch_about_this_app_title,
            subtitle = string.settings_switch_about_this_app_description
        )
        val Review = Setting.Link(
            _id = "about_review",
            title = string.settings_switch_review_title,
            subtitle = string.settings_switch_review_description
        )
        val ShakeToReport = Setting.Link(
            _id = "about_shake",
            title = string.settings_pref_shake_to_report_title,
            subtitle = string.settings_pref_shake_to_report_description
        )
        val BuildVersion = Setting.Link(
            _id = "about_build",
            title = string.settings_build_version,
            subtitle = string.settings_build_version
        )
        val FirstTimeSync = Setting.Link(
            _id = "about_reset",
            title = string.settings_pref_reset_title,
            subtitle = string.settings_pref_reset_description
        )
    }
}

sealed class Setting(
    val id: String
) {
    data class Category(
        private val _id: String,
        val title: StringResource,
        val subtitle: StringResource,
        val icon: DrawableResource? = null,
        val isBeta: Boolean = false,
        val isEnabled: Boolean = true
    ): Setting(_id)

    data class Link(
        private val _id: String,
        val title: StringResource,
        val subtitle: StringResource? = null,
        val isBeta: Boolean = false,
        val isEnabled: Boolean = true
    ): Setting(_id)
}