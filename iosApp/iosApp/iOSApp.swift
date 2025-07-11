import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseMessaging

@main
struct iOSApp: App {

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
