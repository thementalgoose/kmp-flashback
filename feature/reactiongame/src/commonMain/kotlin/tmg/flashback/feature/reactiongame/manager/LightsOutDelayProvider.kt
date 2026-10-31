package tmg.flashback.feature.reactiongame.manager

import kotlin.random.Random
import org.koin.core.annotation.Single

interface LightsOutDelayProvider {
    fun getDelay(): Long
}

@Single
internal class LightsOutDelayProviderImpl(): LightsOutDelayProvider {
    override fun getDelay(): Long {
        return Random.Default.nextLong(500, 2500)
    }
}