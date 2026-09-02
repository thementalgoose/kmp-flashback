package tmg.flashback.persistence.flashback.di

import kotlinx.coroutines.Dispatchers
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
        val factory = get<FlashbackDatabaseFactory>()

        return@single factory
            .createDatabase()
            .setDriver(factory.getSQLiteDriver())
            .setQueryCoroutineContext(factory.getDispatcher() ?: Dispatchers.Default)
            .addMigrations(*migrationsArray)
            .build()
    }
}