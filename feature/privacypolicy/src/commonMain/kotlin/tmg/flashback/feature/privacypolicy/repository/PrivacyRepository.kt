package tmg.flashback.feature.privacypolicy.repository

import tmg.flashback.configuration.manager.ConfigManager

interface PrivacyRepository {
    val privacyPolicyUrl: String?
}

internal class PrivacyRepositoryImpl(
    private val configManager: ConfigManager
): PrivacyRepository {
    
    override val privacyPolicyUrl: String?
        get() = configManager.getString(keyPrivacyPolicyUrl)

    companion object {
        private const val keyPrivacyPolicyUrl = "privacy_policy_url"
    }
}