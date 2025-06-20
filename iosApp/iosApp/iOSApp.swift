import SwiftUI

@main
struct iOSApp: App {
    
    init() {
        KoinSetupKt.initKoin()
        FirebaseApp.configure()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
