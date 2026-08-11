package tmg.flashback.ai.manager

interface AiManager {
    fun isAvailable(): Boolean
    suspend fun status(): String
}

expect class AiManagerImpl: AiManager {
    override fun isAvailable(): Boolean
    override suspend fun status(): String
}