package tmg.flashback.persistence.flashback

import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import kotlinx.coroutines.CoroutineDispatcher
import org.w3c.dom.Worker

@OptIn(ExperimentalWasmJsInterop::class)
private fun createSqliteWorker(): Worker = js("""new Worker(new URL("sqlite-wasm-worker/worker.js", import.meta.url))""")

actual class FlashbackDatabaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<FlashbackDatabase> =
        Room.inMemoryDatabaseBuilder()

    actual fun getSQLiteDriver(): SQLiteDriver = WebWorkerSQLiteDriver(createSqliteWorker())

    actual fun getDispatcher(): CoroutineDispatcher? = null
}
