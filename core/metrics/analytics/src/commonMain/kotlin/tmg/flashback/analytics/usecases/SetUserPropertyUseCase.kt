package tmg.flashback.analytics.usecases

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.analytics.model.UserProperty

interface SetUserPropertyUseCase {
    fun setProperty(property: UserProperty, value: String)
}

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