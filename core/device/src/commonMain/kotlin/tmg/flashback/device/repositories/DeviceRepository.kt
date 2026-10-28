package tmg.flashback.device.repositories

import tmg.flashback.configuration.manager.ConfigManager
import tmg.flashback.device.GITHUB_LINK
import tmg.flashback.device.PLAY_STORE_LINK
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface DeviceRepository {
    var deviceUdid: String
    var installationId: String
    val contactEmail: String
    val playStoreLink: String
    val appleAppStoreLink: String
    val githubLink: String
    val githubReleaseLink: String
    val websiteLink: String
}

@OptIn(ExperimentalUuidApi::class)
internal class DeviceRepositoryImpl(
    private val preferenceManager: PreferenceManager,
    private val configManager: ConfigManager
): DeviceRepository {

    /**
     * Randomly generated device UDID to uniquely identify app install session
     */
    override var deviceUdid: String
        set(value) = preferenceManager.save(keyDeviceUDID, value)
        get() {
            var key = preferenceManager.getString(keyDeviceUDID, "")
            if (key.isNullOrEmpty()) {
                val newKey = Uuid.random().toHexString()
                deviceUdid = newKey
                key = newKey
            }
            return key
        }

    override var installationId: String
        set(value) {
            preferenceManager.save(keyInstallationId, value)
        }
        get() = preferenceManager.getString(keyInstallationId, "") ?: ""

    override val contactEmail: String
        get() = configManager.getString(keyContactEmail) ?: "flashback@thementalgoose.com"

    override val playStoreLink: String
        get() = configManager.getString(keyLinkGooglePlayStore) ?: PLAY_STORE_LINK

    override val appleAppStoreLink: String
        get() = configManager.getString(keyLinkAppleAppStore) ?: APPLE_STORE_LINK

    override val githubLink: String
        get() = configManager.getString(keyLinkGithub) ?: GITHUB_LINK

    override val githubReleaseLink: String
        get() = "${githubLink}/releases"

    override val websiteLink: String
        get() = configManager.getString(keyLinkWebsite) ?: WEBSITE_LINK

    companion object {

        private const val keyContactEmail: String = "email"
        private const val keyLinkAppleAppStore: String = "link_apple_appstore"
        private const val keyLinkGooglePlayStore: String = "link_google_playstore"
        private const val keyLinkGithub: String = "link_github"
        private const val keyLinkWebsite: String = "config_url"

        private const val keyDeviceUDID: String = "UDID"
        private const val keyInstallationId: String = "INSTALLATION_ID"


        private const val GITHUB_LINK = "https://github.com/thementalgoose/kmp-flashback"
        private const val APPLE_STORE_LINK = "https://apps.apple.com/us/app/flashback-formula-results/id6748612648"
        private const val PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=tmg.flashback"
        private const val WEBSITE_LINK = "https://flashback.pages.dev"
    }
}