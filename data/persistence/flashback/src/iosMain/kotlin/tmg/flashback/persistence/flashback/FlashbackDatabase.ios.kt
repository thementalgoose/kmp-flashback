package tmg.flashback.persistence.flashback

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class FlashbackDatabaseFactory {

    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> {
        val dbFile = "${fileDirectory()}/$DB_NAME"

        return Room
            .databaseBuilder<FlashbackDatabase>(
                name = dbFile,
            )
    }

    actual fun getSQLiteDriver(): SQLiteDriver = BundledSQLiteDriver()

    actual fun getDispatcher(): CoroutineDispatcher? = Dispatchers.IO

    @OptIn(ExperimentalForeignApi::class)
    private fun fileDirectory(): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}