package tmg.flashback.feature.about.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.MockMode.autofill
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.runTest
import tmg.flashback.analytics.usecases.LogEventUseCase
import tmg.flashback.device.API_LINK
import tmg.flashback.device.APPLE_STORE_LINK
import tmg.flashback.device.GITHUB_LINK
import tmg.flashback.device.PLAY_STORE_LINK
import tmg.flashback.device.repositories.DeviceRepository
import tmg.flashback.device.usecases.CopyToClipboardUseCase
import tmg.flashback.device.usecases.OpenEmailUseCase
import tmg.flashback.device.usecases.OpenWebpageUseCase
import tmg.flashback.notifications.repositories.NotificationRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AboutViewModelTest {

    private lateinit var underTest: AboutViewModel

    private val mockDeviceRepository: DeviceRepository = mock(autofill)
    private val mockNotificationRepository: NotificationRepository = mock(autofill)
    private val mockCopyToClipboardUseCase: CopyToClipboardUseCase = mock(autoUnit)
    private val mockOpenWebpageUseCase: OpenWebpageUseCase = mock(autoUnit)
    private val mockOpenEmailUseCase: OpenEmailUseCase = mock(autoUnit)
    private val mockLogEventUseCase: LogEventUseCase = mock(autoUnit)

    private fun initUnderTest(
        udid: String = "test-udid",
        installationId: String = "test-iid",
        email: String = "test@example.com"
    ) {
        every { mockDeviceRepository.deviceUdid } returns udid
        every { mockDeviceRepository.installationId } returns installationId
        every { mockDeviceRepository.contactEmail } returns email

        underTest = AboutViewModel(
            deviceRepository = mockDeviceRepository,
            notificationRepository = mockNotificationRepository,
            copyToClipboardUseCase = mockCopyToClipboardUseCase,
            openWebpageUseCase = mockOpenWebpageUseCase,
            openEmailUseCase = mockOpenEmailUseCase,
            logEventUseCase = mockLogEventUseCase
        )
    }

    @Test
    fun `initial uiState contains repository values`() = runTest {
        initUnderTest("udid-123", "iid-456", "support@test.com")

        underTest.uiState.test {
            val state = awaitItem()
            assertEquals("udid-123", state.deviceUuid)
            assertEquals("iid-456", state.installationId)
            assertEquals("support@test.com", state.contactEmail)
            assertTrue(state.buttons.isNotEmpty())
        }
    }

    @Test
    fun `openDependency logs analytics and opens webpage`() {
        initUnderTest()
        val dependency = AboutDependency.Ergast

        underTest.openDependency(dependency)

        verify {
            mockLogEventUseCase.logEvent("view_dependency", mapOf("dependency_name" to dependency.name))
            mockOpenWebpageUseCase.invoke(dependency.url, dependency.name)
        }
    }

    @Test
    fun `idsClicked copies udid and iid to clipboard`() {
        initUnderTest("udid-123", "iid-456")

        underTest.idsClicked()

        val expectedText = """
            udid-123
            iid-456
        """.trimIndent()

        verify {
            mockCopyToClipboardUseCase.invoke(expectedText)
        }
    }

    @Test
    fun `openButton Play logs event and opens store link`() {
        initUnderTest()

        underTest.openButton(AboutButtons.Play)

        verify {
            mockLogEventUseCase.logEvent("view_google_store")
            mockOpenWebpageUseCase.invoke(PLAY_STORE_LINK)
        }
    }

    @Test
    fun `openButton Apple logs event and opens store link`() {
        initUnderTest()

        underTest.openButton(AboutButtons.Apple)

        verify {
            mockLogEventUseCase.logEvent("view_apple_store")
            mockOpenWebpageUseCase.invoke(APPLE_STORE_LINK)
        }
    }

    @Test
    fun `openButton Email logs event and opens email`() {
        initUnderTest(email = "test@example.com")

        underTest.openButton(AboutButtons.Email)

        verify {
            mockLogEventUseCase.logEvent("view_email")
        }
    }

    @Test
    fun `openButton Github logs event and opens github link`() {
        initUnderTest()

        underTest.openButton(AboutButtons.Github)

        verify {
            mockLogEventUseCase.logEvent("view_github")
            mockOpenWebpageUseCase.invoke(GITHUB_LINK)
        }
    }

    @Test
    fun `openButton Api logs event and opens api link`() {
        initUnderTest()

        underTest.openButton(AboutButtons.Api)

        verify {
            mockLogEventUseCase.logEvent("view_api")
            mockOpenWebpageUseCase.invoke(API_LINK)
        }
    }
}
