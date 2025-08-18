import SwiftUI
import SwiftData
import ComposeApp
import UserNotifications
import FirebaseCore
import FirebaseMessaging

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init() {
        if FirebaseApp.app() == nil {
            FirebaseApp.configure()
        }

        KoinSetupKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        application.registerForRemoteNotifications()
        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self
        return true
    }
    
    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().setAPNSToken(deviceToken, type: .unknown)
    }
    
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("Notifications - Firebase registration token: \(String(describing: fcmToken))")
        SubscribeNotificationsKt.doSubscribe()
    }
}
