package tmg.flashback.configuration

import kotlin.test.Test
import kotlin.test.assertTrue

internal class MigrationsTest {

    @Test
    fun `migration value must not be less than 1`() {
        assertTrue(
            actual = Migrations.configurationSyncCount > 0,
            message = """
                If this value is less than 0 then the app will never mark configuration
                as synced and the user will stay in an infinite loop of requiring a
                config sync
            """.trimIndent()
        )
    }
}