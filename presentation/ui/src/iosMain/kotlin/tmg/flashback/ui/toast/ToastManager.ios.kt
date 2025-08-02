package tmg.flashback.ui.toast

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UILabel
import platform.UIKit.UIScreen
import platform.UIKit.UIView
import platform.UIKit.UIViewAnimationOptionCurveEaseOut

@OptIn(ExperimentalForeignApi::class)
actual class ToastManagerImpl actual constructor(): ToastManager {
    actual override fun showMessage(message: String, duration: Duration) {
        val toastDuration = when (duration) {
            Duration.Short -> 2.0
            Duration.Long -> 5.0
        }

        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        val toastLabel = UILabel(
            frame = CGRectMake(
                0.0,
                0.0,
                UIScreen.mainScreen.bounds.useContents { size.width } - 40,
                35.0
            )
        )

        toastLabel.center = CGPointMake(
            UIScreen.mainScreen.bounds.useContents { size.width } / 2,
            UIScreen.mainScreen.bounds.useContents { size.height } - 100.0
        )
        toastLabel.textAlignment = NSTextAlignmentCenter
        toastLabel.backgroundColor = UIColor.blackColor.colorWithAlphaComponent(0.6)
        toastLabel.textColor = UIColor.whiteColor
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 15.0
        toastLabel.clipsToBounds = true
        rootViewController?.view?.addSubview(toastLabel)

        UIView.animateWithDuration(
            duration = toastDuration,
            delay = 0.1,
            options = UIViewAnimationOptionCurveEaseOut,
            animations = {
                toastLabel.alpha = 0.0
            },
            completion = {
                if (it)
                    toastLabel.removeFromSuperview()
            })

    }
}