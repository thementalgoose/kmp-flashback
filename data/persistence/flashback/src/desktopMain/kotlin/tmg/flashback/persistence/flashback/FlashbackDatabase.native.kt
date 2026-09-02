package tmg.flashback.persistence.flashback

import androidx.room3.Room
import androidx.room3.RoomDatabase

actual class FlashbackDatabaseFactory() {
    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> {

        return Room
            .databaseBuilder<FlashbackDatabase>(DB_NAME)
    }
}