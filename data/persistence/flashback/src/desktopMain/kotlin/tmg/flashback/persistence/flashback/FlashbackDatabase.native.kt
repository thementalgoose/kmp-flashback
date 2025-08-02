package tmg.flashback.persistence.flashback

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual class FlashbackDatabaseFactory() {
    actual fun createDatabase(): FlashbackDatabase {
        val migrationsArray = Migrations.entries
            .map { it.migration }
            .toTypedArray()

        return Room
            .databaseBuilder<FlashbackDatabase>(DB_NAME)
            .setDriver(BundledSQLiteDriver())
            .addMigrations(*migrationsArray)
            .build()
    }
}