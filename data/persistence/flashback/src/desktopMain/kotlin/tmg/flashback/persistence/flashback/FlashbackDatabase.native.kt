package tmg.flashback.persistence.flashback

import androidx.room.Room

actual class FlashbackDatabaseFactory() {
    actual fun createDatabase(): FlashbackDatabase {
        val migrationsArray = Migrations.entries
            .map { it.migration }
            .toTypedArray()

        return Room
            .databaseBuilder<FlashbackDatabase>(DB_NAME)
            .addMigrations(*migrationsArray)
            .build()
    }
}