package tmg.flashback.repositories

import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode.Companion.exactly
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class DeviceRepositoryTest {
    private lateinit var underTest: DeviceRepositoryImpl

    private val mockPreferenceManager: PreferenceManager = mock(autoUnit)

    private fun initUnderTest() {
        underTest = DeviceRepositoryImpl(
            preferenceManager = mockPreferenceManager
        )
    }


    //region App first opened

    @Test
    fun `device udid when value is null saves a random guid to shared preferences and returns random guid`() {
        every { mockPreferenceManager.getString(expectedKeyDeviceUdid, any()) } returns ""
        initUnderTest()

        assertNotNull(underTest.deviceUdid)
        verify {
            mockPreferenceManager.getString(expectedKeyDeviceUdid, "")
            mockPreferenceManager.save(expectedKeyDeviceUdid, any<String>())
        }
    }

    @Test
    fun `device udid when value is not null returns value`() {
        val testGuid = "test-guid"

        every { mockPreferenceManager.getString(expectedKeyDeviceUdid, any()) } returns testGuid
        initUnderTest()

        assertEquals(testGuid, underTest.deviceUdid)
        verify(exactly(0)) {
            mockPreferenceManager.save(expectedKeyDeviceUdid, any<String>())
        }
        verify {
            mockPreferenceManager.getString(expectedKeyDeviceUdid, "")
        }
    }

    @Test
    fun `device udid update saves value to shared prefs repository`() {
        val testGuid = "test-guid"

        initUnderTest()

        underTest.deviceUdid = testGuid
        verify {
            mockPreferenceManager.save(expectedKeyDeviceUdid, testGuid)
        }
    }

    //endregion

    //region Installation ID

    @Test
    fun `installation id gets installation id from shared prefs repository`() {
        val value = "installation-id"
        every { mockPreferenceManager.getString(expectedKeyInstallationId, "") } returns value

        initUnderTest()

        assertEquals(value, underTest.installationId)
        verify {
            mockPreferenceManager.getString(expectedKeyInstallationId, "")
        }
    }

    //endregion

    companion object {

        private const val expectedKeyDeviceUdid: String = "UDID"
        private const val expectedKeyInstallationId: String = "INSTALLATION_ID"
    }
}