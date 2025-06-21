package tmg.flashback.crashlytics.usecases

import tmg.flashback.crashlytics.firebase.FirebaseCrashlyticsService
import tmg.flashback.crashlytics.model.FirebaseKey
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.log.logInfo

interface InitialiseCrashlyticsUseCase {
    fun initialise(deviceUuid: String, extraKeys: Map<FirebaseKey, String>)
}

internal class InitialiseCrashlyticsUseCaseImpl(
    private val firebaseCrashlyticsService: FirebaseCrashlyticsService,
): InitialiseCrashlyticsUseCase {
    override fun initialise(
        deviceUuid: String,
        extraKeys: Map<FirebaseKey, String>
    ) {
        firebaseCrashlyticsService.setCrashlyticsCollectionEnabled(true)
        logInfo("Crashlytics", "Enabling crashlytics")

        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Emulator.label, Device.isEmulator)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Debug.label, Device.isDebug)

        firebaseCrashlyticsService.setUserId(deviceUuid)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.DeviceUuid.label, deviceUuid)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Brand.label, Device.brand)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Hardware.label, Device.hardware)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Board.label, Device.board)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Fingerprint.label, Device.fingerprint)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Model.label, Device.model)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Manufacturer.label, Device.manufacturer)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Product.label, Device.product)
        firebaseCrashlyticsService.setCustomKey(FirebaseKey.Device.label, Device.device)

        extraKeys.forEach { (key, value) ->
            firebaseCrashlyticsService.setCustomKey(key.label, value)
        }
    }
}