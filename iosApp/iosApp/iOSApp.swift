import SwiftUI
import ComposeApp
import Firebase
import FirebaseCore
import FirebaseMessaging

// class AppDelegate: NSObject, UIApplicationDelegate {
//     func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
//         Messaging.messaging().apnsToken = deviceToken
//     }
// }


@main
struct iOSApp: App {

//     @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        KoinSetupKt.doInitKoin()

        FirebaseApp.configure()
//         NotifierManager.shared.initialize(
//             configuration: NotificationPlatformConfigurationIos(
//                 showPushNotification: true,
//                 askNotificationPermissionOnStart: false,
//                 notificationSoundName: nil
//             )
//         )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
