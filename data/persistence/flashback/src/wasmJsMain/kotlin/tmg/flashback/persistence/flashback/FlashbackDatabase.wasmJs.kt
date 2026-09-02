package tmg.flashback.persistence.flashback

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class FlashbackDatabaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> =
        Room.inMemoryDatabaseBuilder()

    actual fun getSQLiteDriver(): SQLiteDriver = WebWorkerSQLiteDriver()

    actual fun getDispatcher(): CoroutineDispatcher? = null
}
