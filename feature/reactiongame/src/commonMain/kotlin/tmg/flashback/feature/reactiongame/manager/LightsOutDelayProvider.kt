package tmg.flashback.feature.reactiongame.manager

import org.koin.core.annotation.Single
import kotlin.random.Random

interface LightsOutDelayProvider {
    fun getDelay(): Long
}

@Single(binds = [LightsOutDelayProvider::class])
internal class LightsOutDelayProviderImpl(): LightsOutDelayProvider {
    override fun getDelay(): Long {
        return Random.Default.nextLong(500, 2500)
    }
}