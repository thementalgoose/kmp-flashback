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
import flashback.composeapp.generated.resources.ic_settings_widgets
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_15
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_30
import flashback.presentation.localisation.generated.resources.notification_reminder_mins_60
import flashback.presentation.localisation.generated.resources.settings_build_version
import flashback.presentation.localisation.generated.resources.settings_pref_analytics_description
import flashback.presentation.localisation.generated.resources.settings_pref_analytics_title
import flashback.presentation.localisation.generated.resources.settings_pref_collapsed_list_description
import flashback.presentation.localisation.generated.resources.settings_pref_collapsed_list_title
import flashback.presentation.localisation.generated.resources.settings_pref_crash_reporting_description
import flashback.presentation.localisation.generated.resources.settings_pref_crash_reporting_title
import flashback.presentation.localisation.generated.resources.settings_pref_empty_week_description
import flashback.presentation.localisation.generated.resources.settings_pref_empty_week_title
import flashback.presentation.localisation.generated.resources.settings_pref_notification_manage_description
import flashback.presentation.localisation.generated.resources.settings_pref_notification_manage_title
import flashback.presentation.localisation.generated.resources.settings_pref_notification_permission_description
import flashback.presentation.localisation.generated.resources.settings_pref_notification_permission_title
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
import flashback.presentation.localisation.generated.resources.settings_section_refresh_widget_description
import flashback.presentation.localisation.generated.resources.settings_section_refresh_widget_title
import flashback.presentation.localisation.generated.resources.settings_section_rss_configure_description
import flashback.presentation.localisation.generated.resources.settings_section_rss_configure_title
import flashback.presentation.localisation.generated.resources.settings_section_weather_description
import flashback.presentation.localisation.generated.resources.settings_section_weather_title
import flashback.presentation.localisation.generated.resources.settings_section_web_browser_description
import flashback.presentation.localisation.generated.resources.settings_section_web_browser_title
import flashback.presentation.localisation.generated.resources.settings_section_widgets_deeplink_event_description
import flashback.presentation.localisation.generated.resources.settings_section_widgets_deeplink_event_title
import flashback.presentation.localisation.generated.resources.settings_section_widgets_description
import flashback.presentation.localisation.generated.resources.settings_section_widgets_show_background_description
import flashback.presentation.localisation.generated.resources.settings_section_widgets_show_background_title
import flashback.presentation.localisation.generated.resources.settings_section_widgets_show_weather_description
import flashback.presentation.localisation.generated.resources.settings_section_widgets_show_weather_title
import flashback.presentation.localisation.generated.resources.settings_section_widgets_title
import flashback.presentation.localisation.generated.resources.settings_switch_about_this_app_description
import flashback.presentation.localisation.generated.resources.settings_switch_about_this_app_title
import flashback.presentation.localisation.generated.resources.settings_switch_enable_javascript_description
import flashback.presentation.localisation.generated.resources.settings_switch_enable_javascript_title
import flashback.presentation.localisation.generated.resources.settings_switch_enable_web_browser_description
import flashback.presentation.localisation.generated.resources.settings_switch_enable_web_browser_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_results_qualifying_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_results_race_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_results_sprint_qualifying_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_results_sprint_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_upcoming_fp_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_upcoming_qualifying_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_upcoming_race_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_upcoming_sprint_qualifying_title
import flashback.presentation.localisation.generated.resources.settings_switch_notification_upcoming_sprint_title
import flashback.presentation.localisation.generated.resources.settings_switch_review_description
import flashback.presentation.localisation.generated.resources.settings_switch_review_title
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_dark
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_follow_system
import flashback.presentation.localisation.generated.resources.settings_theme_nightmode_light
import flashback.presentation.localisation.generated.resources.settings_theme_theme_default
import flashback.presentation.localisation.generated.resources.settings_theme_theme_default_desc
import flashback.presentation.localisation.generated.resources.settings_theme_theme_description
import flashback.presentation.localisation.generated.resources.settings_theme_theme_material_you
import flashback.presentation.localisation.generated.resources.settings_theme_theme_material_you_desc
import flashback.presentation.localisation.generated.resources.settings_theme_theme_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import tmg.flashback.infrastructure.device.Device

object Settings {
    val DarkModeCategory = Setting.Category(
        _id = "dark_mode",
        title = string.settings_section_dark_mode_title,
        subtitle = string.settings_section_dark_mode_description,
        icon = Res.drawable.ic_settings_dark_mode
    )
    object DarkMode {
        val DarkModeAuto = Setting.Pref(
            _id = "dark_mode_auto",
            title = string.settings_theme_nightmode_follow_system,
            subtitle = string.settings_theme_nightmode_follow_system
        )
        val DarkModeLight = Setting.Pref(
            _id = "dark_mode_light",
            title = string.settings_theme_nightmode_light,
            subtitle = string.settings_theme_nightmode_light
        )
        val DarkModeDark = Setting.Pref(
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
        val ThemeDefault = Setting.Pref(
            _id = "theme_default",
            title = string.settings_theme_theme_default,
            subtitle = string.settings_theme_theme_default_desc
        )
        val ThemeMaterialYou = Setting.Pref(
            _id = "theme_material_you",
            title = string.settings_theme_theme_material_you,
            subtitle = string.settings_theme_theme_material_you_desc
        )
    }

    val LayoutCategory = Setting.Category(
        _id = "layout",
        title = string.settings_section_home_title,
        subtitle = string.settings_section_home_description,
        icon = Res.drawable.ic_settings_home
    )
    object Layout {
        val RecentHighlights = Setting.Pref(
            _id = "layout_recents",
            title = string.settings_pref_recent_highlights_title,
            subtitle = string.settings_pref_recent_highlights_description,
            isEnabled = false
        )
        val CollapsibleRaces = Setting.Pref(
            _id = "layout_collapsible_races",
            title = string.settings_pref_collapsed_list_title,
            subtitle = string.settings_pref_collapsed_list_description
        )
        val EmptyWeeks = Setting.Pref(
            _id = "layout_emptyweeks",
            title = string.settings_pref_empty_week_title,
            subtitle = string.settings_pref_empty_week_description
        )
        val KeepSeason = Setting.Pref(
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
        val TemperatureUnit = Setting.Pref(
            _id = "weather_temp",
            title = string.settings_pref_temperature_unit_title,
            subtitle = string.settings_pref_temperature_unit_description
        )
        val WindSpeed = Setting.Pref(
            _id = "weather_wind",
            title = string.settings_pref_wind_speed_unit_title,
            subtitle = string.settings_pref_wind_speed_unit_description
        )
    }

    val RssCategory = Setting.Category(
        _id = "rss",
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
        val Enabled = Setting.Pref(
            _id = "webbrowser_enabled",
            title = string.settings_switch_enable_web_browser_title,
            subtitle = string.settings_switch_enable_web_browser_description
        )
        val EnableJavascript = Setting.Pref(
            _id = "webbrowser_javascript",
            title = string.settings_switch_enable_javascript_title,
            subtitle = string.settings_switch_enable_javascript_description
        )
    }

    val NotificationsResultCategory = Setting.Category(
        _id = "notifications_race",
        title = string.settings_section_notifications_results_title,
        subtitle = string.settings_section_notifications_results_description,
        icon = Res.drawable.ic_settings_notifications_results
    )
    object NotificationsResults {
        val PermissionEnable = Setting.Pref(
            _id = "notifications_permissions",
            title = string.settings_pref_notification_permission_title,
            subtitle = string.settings_pref_notification_permission_description
        )
        val PermissionManage = Setting.Pref(
            _id = "notifications_manage",
            title = string.settings_pref_notification_manage_title,
            subtitle = string.settings_pref_notification_manage_description
        )
        val SprintQualifying = Setting.Pref(
            _id = "results_sprint_qualifying",
            title = string.settings_switch_notification_results_sprint_qualifying_title
        )
        val SprintRace = Setting.Pref(
            _id = "results_sprint_race",
            title = string.settings_switch_notification_results_sprint_title
        )
        val Qualifying = Setting.Pref(
            _id = "results_qualifying",
            title = string.settings_switch_notification_results_qualifying_title
        )
        val Race = Setting.Pref(
            _id = "results_race",
            title = string.settings_switch_notification_results_race_title
        )
    }

    val NotificationsUpcomingCategory = Setting.Category(
        _id = "notifications_upcoming",
        title = string.settings_section_notifications_upcoming_title,
        subtitle = string.settings_section_notifications_upcoming_description,
        icon = Res.drawable.ic_settings_notifications_upcoming
    )
    object NotificationsUpcoming {
        val PermissionEnable = Setting.Pref(
            _id = "notifications_permissions",
            title = string.settings_pref_notification_permission_title,
            subtitle = string.settings_pref_notification_permission_description
        )
        val PermissionManage = Setting.Pref(
            _id = "notifications_manage",
            title = string.settings_pref_notification_manage_title,
            subtitle = string.settings_pref_notification_manage_description
        )
        val FreePractice = Setting.Pref(
            _id = "upcoming_fp",
            title = string.settings_switch_notification_upcoming_fp_title
        )
        val SprintQualifying = Setting.Pref(
            _id = "upcoming_sprint_qualifying",
            title = string.settings_switch_notification_upcoming_sprint_qualifying_title
        )
        val SprintRace = Setting.Pref(
            _id = "upcoming_sprint_race",
            title = string.settings_switch_notification_upcoming_sprint_title
        )
        val Qualifying = Setting.Pref(
            _id = "upcoming_qualifying",
            title = string.settings_switch_notification_upcoming_qualifying_title
        )
        val Race = Setting.Pref(
            _id = "upcoming_race",
            title = string.settings_switch_notification_upcoming_race_title
        )
    }
    object NotificationsNotice {
        val Minutes15 = Setting.Pref(
            _id = "notification_notice_15",
            title = string.notification_reminder_mins_15
        )
        val Minutes30 = Setting.Pref(
            _id = "notification_notice_30",
            title = string.notification_reminder_mins_30
        )
        val Minutes60 = Setting.Pref(
            _id = "notification_notice_60",
            title = string.notification_reminder_mins_60
        )
    }

    val WidgetCategory = Setting.Category(
        _id = "widgets",
        title = string.settings_section_widgets_title,
        subtitle = string.settings_section_widgets_description,
        icon = Res.drawable.ic_settings_widgets
    )
    object Widgets {
        val RefreshWidgets = Setting.Pref(
            _id = "widgets_refresh",
            title = string.settings_section_refresh_widget_title,
            subtitle = string.settings_section_refresh_widget_description,
        )
        val ShowBackground = Setting.Pref(
            _id = "widgets_show_background",
            title = string.settings_section_widgets_show_background_title,
            subtitle = string.settings_section_widgets_show_background_description,
        )
        val DeeplinkToEvent = Setting.Pref(
            _id = "widgets_deeplink_to_event",
            title = string.settings_section_widgets_deeplink_event_title,
            subtitle = string.settings_section_widgets_deeplink_event_description,
            isEnabled = false,
        )
        val ShowWeather = Setting.Pref(
            _id = "widgets_weather",
            title = string.settings_section_widgets_show_weather_title,
            subtitle = string.settings_section_widgets_show_weather_description,
            isEnabled = false,
        )
    }

    val PrivacyCategory = Setting.Category(
        _id = "privacy",
        title = string.settings_section_privacy_title,
        subtitle = string.settings_section_privacy_description,
        icon = Res.drawable.ic_settings_privacy
    )
    object Privacy {
        val CrashReporting = Setting.Pref(
            _id = "crash_reporting",
            title = string.settings_pref_crash_reporting_title,
            subtitle = string.settings_pref_crash_reporting_description
        )
        val Analytics = Setting.Pref(
            _id = "analytics",
            title = string.settings_pref_analytics_title,
            subtitle = string.settings_pref_analytics_description
        )
    }
    val PrivacyPolicy = Setting.Category(
        _id = "privacy_policy",
        title = string.settings_pref_privacy_policy_title,
        subtitle = string.settings_pref_privacy_policy_description
    )

    val AboutCategory = Setting.Category(
        _id = "about",
        title = string.settings_section_about_title,
        subtitle = string.settings_section_about_description,
        icon = Res.drawable.ic_settings_about
    )
    object About {
        val AboutThisApp = Setting.Pref(
            _id = "about_aboutthisapp",
            title = string.settings_switch_about_this_app_title,
            subtitle = string.settings_switch_about_this_app_description
        )
        val Review = Setting.Pref(
            _id = "about_review",
            title = string.settings_switch_review_title,
            subtitle = string.settings_switch_review_description
        )
        val ShakeToReport = Setting.Pref(
            _id = "about_shake",
            title = string.settings_pref_shake_to_report_title,
            subtitle = string.settings_pref_shake_to_report_description
        )
        val BuildVersion = Setting.Pref(
            _id = "about_build",
            title = string.settings_build_version,
            subtitleString = Device.versionName
        )
        val FirstTimeSync = Setting.Pref(
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

    data class Pref(
        private val _id: String,
        val title: StringResource,
        val subtitle: StringResource? = null,
        val subtitleString: String? = null,
        val isBeta: Boolean = false,
        val isEnabled: Boolean = true
    ): Setting(_id)
}