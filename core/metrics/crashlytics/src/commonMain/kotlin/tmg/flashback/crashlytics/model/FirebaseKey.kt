package tmg.flashback.crashlytics.model

enum class FirebaseKey(
    internal val label: String
) {
    Debug("debug"),
    Emulator("emulator"),
    DeviceUuid("uuid"),
    Model("model"),
    Manufacturer("manufacturer"),
    Fingerprint("fingerprint"),
    Brand("brand"),
    Board("board"),
    Hardware("hardware"),
    Product("product"),
    Device("device"),
    AppFirstOpen("appFirstOpen"),
    AppOpenCount("appOpenCount"),
    WidgetCount("widgetCount"),
    InstallationId("installationId"),
}