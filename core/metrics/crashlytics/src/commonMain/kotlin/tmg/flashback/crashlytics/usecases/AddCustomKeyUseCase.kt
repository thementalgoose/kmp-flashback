package tmg.flashback.crashlytics.usecases

import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import tmg.flashback.crashlytics.model.FirebaseKey

interface AddCustomKeyUseCase {
    fun invoke(key: FirebaseKey, value: String)
    fun invoke(key: FirebaseKey, value: Boolean)
}

internal class AddCustomKeyUseCaseImpl(
    private val firebaseCrashlyticsService: FirebaseCrashlyticsService
): AddCustomKeyUseCase {
    override fun invoke(key: FirebaseKey, value: String) {
        firebaseCrashlyticsService.setCustomKey(key.label, value)
    }

    override fun invoke(key: FirebaseKey, value: Boolean) {
        firebaseCrashlyticsService.setCustomKey(key.label, value)
    }

}