package tmg.flashback.persistence.flashback

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

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
}