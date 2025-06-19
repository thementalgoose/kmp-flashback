package tmg.flashback.persistence.flashback

import android.content.Context
import androidx.room.Room

actual class FlashbackDatabaseFactory(
    private val applicationContext: Context
) {
    actual fun createDatabase(): FlashbackDatabase {
        val migrationsArray = Migrations.entries
            .map { it.migration }
            .toTypedArray()

        return Room
            .databaseBuilder(applicationContext, FlashbackDatabase::class.java, DB_NAME)
            .addMigrations(*migrationsArray)
            .build()
    }
}