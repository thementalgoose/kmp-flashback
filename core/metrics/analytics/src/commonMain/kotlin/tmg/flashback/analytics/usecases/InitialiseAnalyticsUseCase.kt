package tmg.flashback.analytics.usecases

import tmg.flashback.analytics.firebase.FirebaseAnalyticsService
import tmg.flashback.analytics.model.UserProperty
import tmg.flashback.analytics.model.UserProperty.APP_VERSION
import tmg.flashback.analytics.model.UserProperty.DEVICE_BOARD
import tmg.flashback.analytics.model.UserProperty.DEVICE_BRAND
import tmg.flashback.analytics.model.UserProperty.DEVICE_MANUFACTURER
import tmg.flashback.analytics.model.UserProperty.DEVICE_MODEL
import tmg.flashback.analytics.model.UserProperty.OS_VERSION
import tmg.flashback.analytics.model.UserProperty.PLATFORM
import tmg.flashback.analytics.repositories.AnalyticsRepository
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform

interface InitialiseAnalyticsUseCase {
    fun initialise(userId: String)
}

internal class InitialiseAnalyticsUseCaseImpl(
    private val firebaseAnalyticsService: FirebaseAnalyticsService,
    private val analyticsRepository: AnalyticsRepository
): InitialiseAnalyticsUseCase {
    override fun initialise(userId: String) {
        firebaseAnalyticsService.setUserId(userId)
        firebaseAnalyticsService.setAnalyticsCollectionEnabled(analyticsRepository.analyticsEnabled)
        firebaseAnalyticsService.setProperty(APP_VERSION.key, Device.versionName)
        firebaseAnalyticsService.setProperty(OS_VERSION.key, Device.osVersion)
        firebaseAnalyticsService.setProperty(PLATFORM.key, when (Device.platform) {
            Platform.Android -> "Android"
            Platform.IOS -> "iOS"
            Platform.Other -> "Other"
        })
        firebaseAnalyticsService.setProperty(DEVICE_BOARD.key, Device.board)
        firebaseAnalyticsService.setProperty(DEVICE_MODEL.key, Device.model)
        firebaseAnalyticsService.setProperty(DEVICE_BRAND.key, Device.brand)
        firebaseAnalyticsService.setProperty(DEVICE_MANUFACTURER.key, Device.manufacturer)
    }
}