fun getVersionName() {
    return System.getenv("VERSION_NAME") as? String ?: "1.0.0"
}
fun getVersionCode() {
    return System.getenv("VERSION_CODE") as? Integer ?: 1
}

ext {
    compileSdk = 35
    targetSdkVersion = 35
    minSdkVersion = 26

    versionName = "${getVersionName()}. getVersionCode()
    versionCode = getVersionCode()

    baseUrl = "https://flashback.pages.dev"
    contactEmail = "thementalgoose@gmail.com"
}