package tmg.flashback.feature.privacypolicy.repository

import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import tmg.flashback.configuration.manager.ConfigManager

interface PrivacyRepository {
    val privacyPolicyUrl: String?
}

@Single(binds = [PrivacyRepository::class])
internal class PrivacyRepositoryImpl(
    @Provided private val configManager: ConfigManager
): PrivacyRepository {
    
    override val privacyPolicyUrl: String?
        get() = configManager.getString(keyPrivacyPolicyUrl)

    companion object {
        private const val keyPrivacyPolicyUrl = "privacy_policy_url"
    }
}