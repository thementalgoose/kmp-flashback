package tmg.flashback.feature.reactiongame.manager

import kotlin.random.Random

interface LightsOutDelayProvider {
    fun getDelay(): Long
}

internal class LightsOutDelayProviderImpl(): LightsOutDelayProvider {
    override fun getDelay(): Long {
        return Random.Default.nextLong(500, 2500)
    }
}