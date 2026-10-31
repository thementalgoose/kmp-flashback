package tmg.flashback.analytics.usecases

import org.koin.core.annotation.Single
import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.analytics.model.UserProperty

interface SetUserPropertyUseCase {
    fun setProperty(property: UserProperty, value: String)
}

@Single
internal class SetUserPropertyUseCaseImpl(
    private val firebaseAnalyticsService: FirebaseAnalyticsService
): SetUserPropertyUseCase {
    override fun setProperty(
        property: UserProperty,
        value: String
    ) {
        firebaseAnalyticsService.setProperty(property.key, value)
    }
}