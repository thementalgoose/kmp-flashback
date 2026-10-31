package tmg.flashback.persistence.flashback.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import tmg.flashback.infrastructure.coroutines.ioDispatcher
import tmg.flashback.persistence.flashback.FlashbackDatabase
import tmg.flashback.persistence.flashback.FlashbackDatabaseFactory
import tmg.flashback.persistence.flashback.Migrations

@Module
@ComponentScan("tmg.flashback.persistence.flashback")
@Configuration
class DataPersistenceFlashbackModule {
    @Single
    fun provideFlashbackDatabase(factory: FlashbackDatabaseFactory): FlashbackDatabase {
        val migrationsArray = Migrations.entries
            .map { it.migration }
            .toTypedArray()

        return factory
            .createDatabase()
            .setDriver(factory.getSQLiteDriver())
            .setQueryCoroutineContext(ioDispatcher)
            .addMigrations(*migrationsArray)
            .build()
    }
}