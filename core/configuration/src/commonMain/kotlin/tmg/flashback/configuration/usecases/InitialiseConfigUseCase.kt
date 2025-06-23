package tmg.flashback.configuration.usecases

import tmg.flashback.configuration.manager.ConfigManager

interface InitialiseConfigUseCase {
    operator fun invoke(defaultValues: Map<String, String>)
}

internal class InitialiseConfigUseCaseImpl(
    private val configManager: ConfigManager
): InitialiseConfigUseCase {
    override operator fun invoke(defaultValues: Map<String, String>) {
        configManager.initialiseRemoteConfig(defaultValues)
    }
}