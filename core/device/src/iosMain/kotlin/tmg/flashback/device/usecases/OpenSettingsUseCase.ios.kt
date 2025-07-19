package tmg.flashback.device.usecases

import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIApplicationOpenNotificationSettingsURLString
import platform.Foundation.NSURL

actual class OpenSettingsUseCaseImpl actual constructor(): OpenSettingsUseCase {
    actual override fun openNotificationSettings() {
        val url = NSURL(string = UIApplicationOpenNotificationSettingsURLString)
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

            })
        }
    }

    actual override fun openSettings() {
        val url = NSURL(string = UIApplicationOpenSettingsURLString)
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

            })
        }
    }

    actual override fun openAlarmSettings() {
        val url = NSURL(string = UIApplicationOpenNotificationSettingsURLString)
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication().openURL(url, options = emptyMap<Any?, Any>(), completionHandler = {

            })
        }
    }
}