package tmg.flashback.device.usecases

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import org.koin.java.KoinJavaComponent
import tmg.flashback.infrastructure.device.Device

actual class OpenSettingsUseCaseImpl actual constructor(): OpenSettingsUseCase {

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override fun openNotificationSettings() {
        val settingsIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        settingsIntent.putExtra("app_package", Device.applicationId);
        settingsIntent.putExtra(Settings.EXTRA_APP_PACKAGE, Device.applicationId)

        getApplicationContext().startActivity(settingsIntent)
    }

    actual override fun openSettings() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        settingsIntent.putExtra("app_package", Device.applicationId);
        settingsIntent.putExtra(Settings.EXTRA_APP_PACKAGE, Device.applicationId)

        getApplicationContext().startActivity(settingsIntent)
    }

    actual override fun openAlarmSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            getApplicationContext().startActivity(intent)
        } else {
            openNotificationSettings()
        }
    }
}