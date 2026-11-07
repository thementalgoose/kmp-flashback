package tmg.flashback.feature.reactiongame.repositories

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import tmg.flashback.preferences.manager.PreferenceManager
import kotlin.time.Instant

interface ReactionGameRepository {
    var results: List<ReactionGameEntry>
}

@Single
internal class ReactionGameRepositoryImpl(
    private val preferenceManager: PreferenceManager
): ReactionGameRepository {

    override var results: List<ReactionGameEntry>
        get() = preferenceManager.getSet(keyReactionGame, emptySet())
            .mapNotNull {
                val results = it.split("|")
                if (results.size == 2) {
                    val time = results[0].toIntOrNull() ?: return@mapNotNull null
                    val date = results[1].toLongOrNull() ?: return@mapNotNull null
                    return@mapNotNull ReactionGameEntry(
                        timeMillis = time,
                        date = Instant.fromEpochSeconds(date).toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                }
                return@mapNotNull null
            }
        set(value) {
            val saveStrings = value.map {
                "${it.timeMillis}|${it.date.toInstant(TimeZone.currentSystemDefault()).epochSeconds}"
            }
            preferenceManager.save(keyReactionGame, saveStrings.toSet())
        }

    companion object {
        private const val keyReactionGame: String = "REACTION_GAME_RESULTS"
    }
}