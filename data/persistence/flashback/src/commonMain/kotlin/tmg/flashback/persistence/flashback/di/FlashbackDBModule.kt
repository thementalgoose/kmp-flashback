package tmg.flashback.persistence.flashback.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import tmg.flashback.persistence.flashback.FlashbackDatabase
import tmg.flashback.persistence.flashback.FlashbackDatabaseFactory
import tmg.flashback.persistence.flashback.Migrations

val dataPersistenceFlashbackModule = listOf(
    platformModule(),
    module()
)

internal fun module() = module {
    single<FlashbackDatabase> {

        val migrationsArray = Migrations.entries
            .map { it.migration }
            .toTypedArray()

        get<FlashbackDatabaseFactory>()
            .createDatabase()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .addMigrations(*migrationsArray)
            .build()
    }
}