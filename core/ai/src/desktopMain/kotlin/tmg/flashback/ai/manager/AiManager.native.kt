package tmg.flashback.ai.manager

actual class AiManagerImpl actual constructor(): AiManager {
    actual override fun isAvailable(): Boolean {
        return false
    }

    actual override suspend fun status(): String {
        return "n/a"
    }
}