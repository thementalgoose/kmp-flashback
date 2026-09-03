package tmg.flashback.persistence.flashback

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual class FlashbackDatabaseFactory(
    private val applicationContext: Context
) {
    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> {
        return Room
            .databaseBuilder(
                context = applicationContext,
                klass = FlashbackDatabase::class.java,
                name = DB_NAME
            )
    }

    actual fun getSQLiteDriver(): SQLiteDriver = BundledSQLiteDriver()

    actual fun getDispatcher(): CoroutineDispatcher? = Dispatchers.IO
}