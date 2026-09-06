package tmg.flashback.persistence.flashback

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual class FlashbackDatabaseFactory() {
    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> {

        return Room
            .databaseBuilder<FlashbackDatabase>(DB_NAME)
    }

    actual fun getSQLiteDriver(): SQLiteDriver = BundledSQLiteDriver()
}