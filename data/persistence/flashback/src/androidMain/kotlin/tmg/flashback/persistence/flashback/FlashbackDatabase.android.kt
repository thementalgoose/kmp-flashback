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
            .databaseBuilder(
                context = applicationContext,
                klass = FlashbackDatabase::class.java,
                name = DB_NAME
            )
            .addMigrations(*migrationsArray)
            .build()
    }
}