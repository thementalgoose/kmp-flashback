package tmg.flashback.feature.about.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tmg.flashback.analytics.usecases.LogEventUseCase
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.usecases.CopyToClipboardUseCase
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.infrastructure.device.Platform
import tmg.flashback.infrastructure.device.string
import tmg.flashback.notifications.repositories.NotificationRepository

class AboutViewModel(
    private val deviceRepository: DeviceRepository,
    private val notificationRepository: NotificationRepository,
    private val copyToClipboardUseCase: CopyToClipboardUseCase,
    private val openWebpageUseCase: OpenWebpageUseCase,
    private val openEmailUseCase: OpenEmailUseCase,
    private val logEventUseCase: LogEventUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<AboutUiState> = MutableStateFlow(AboutUiState(
        deviceUuid = deviceRepository.deviceUdid,
        installationId = deviceRepository.installationId,
        remoteTokenId = null,
        contactEmail = deviceRepository.contactEmail,
        buttons = listOfNotNull(
            AboutButtons.Play.takeIf { Device.platform == Platform.Android },
            AboutButtons.Apple.takeIf { Device.platform == Platform.IOS },
            AboutButtons.Api,
            AboutButtons.Github,
            AboutButtons.Email
        )
    ))
    val uiState: StateFlow<AboutUiState> = _uiState

    fun openDependency(dependency: AboutDependency) {
        logEventUseCase.logEvent("view_dependency", mapOf("dependency_name" to dependency.name))
        openWebpageUseCase.invoke(dependency.url, dependency.name)
    }

    fun idsClicked() {
        copyToClipboardUseCase("""
            ${deviceRepository.deviceUdid}
            ${deviceRepository.installationId}
        """.trimIndent())
    }

    fun openButton(aboutButtons: AboutButtons) {
        when (aboutButtons) {
            AboutButtons.Play -> {
                logEventUseCase.logEvent("view_google_store")
                openWebpageUseCase(deviceRepository.playStoreLink)
            }
            AboutButtons.Apple -> {
                logEventUseCase.logEvent("view_apple_store")
                openWebpageUseCase(deviceRepository.appleAppStoreLink)
            }
            AboutButtons.Email -> {
                logEventUseCase.logEvent("view_email")
                openEmailUseCase(
                    email = deviceRepository.contactEmail,
                    title = "Flashback",
                    contents = """
                        Hi 👋
                        
                        
                        
                        ------------------------------------------------------
                        Platform: ${Device.platform.string}
                        OS Version: ${Device.osVersion}
                        Brand: ${Device.brand}
                        Manufacturer: ${Device.manufacturer}
                        Hardware: ${Device.hardware}
                        Model: ${Device.model}
                        Product: ${Device.product}
                        Device: ${Device.device}
                        UDID: ${deviceRepository.deviceUdid}
                        IID: ${deviceRepository.installationId}
                        ------------------------------------------------------
                    """.trimIndent()
                )
            }
            AboutButtons.Github -> {
                logEventUseCase.logEvent("view_github")
                openWebpageUseCase(deviceRepository.githubReleaseLink)
            }
            AboutButtons.Api -> {
                logEventUseCase.logEvent("view_api")
                openWebpageUseCase(deviceRepository.websiteLink)
            }
        }
    }
}