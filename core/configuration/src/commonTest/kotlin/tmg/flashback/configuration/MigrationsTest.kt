package tmg.flashback.configuration

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

internal class MigrationsTest {

    @Test
    fun `migration value must not be less than 1`() {
        assertTrue(
            Migrations.configurationSyncCount > 0,
            """
                If this value is less than 0 then the app will never mark configuration
                as synced and the user will stay in an infinite loop of requiring a
                config sync
            """.trimIndent()
        )
    }
}