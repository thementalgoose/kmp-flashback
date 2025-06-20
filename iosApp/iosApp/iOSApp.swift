import SwiftUI
import ComposeApp
import Firebase

@main
struct iOSApp: App {
    
    init() {
        KoinSetupKt.doInitKoin()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
