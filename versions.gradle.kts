fun getVersionName() {
    return System.getenv("VERSION_NAME") as? String ?: "1.0.0"
}
fun getVersionCode() {
    return System.getenv("VERSION_CODE") as? String ?: "1"
}