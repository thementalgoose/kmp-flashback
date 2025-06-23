package tmg.flashback.configuration

internal object Migrations {
    /**
     * Number of builds that have required a remote config synchronisation
     * Increment this number by 1 when a new build comes out that requires
     *   a remote config fetch before loading the app
     */
    const val configurationSyncCount: Int = 1
}