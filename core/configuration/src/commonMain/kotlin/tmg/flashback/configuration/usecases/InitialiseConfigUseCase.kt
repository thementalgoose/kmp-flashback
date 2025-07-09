package tmg.flashback.configuration.usecases

import tmg.flashback.configuration.manager.ConfigManager

interface InitialiseConfigUseCase {
    operator fun invoke(defaultValues: Map<String, Any>)
}

internal class InitialiseConfigUseCaseImpl(
    private val configManager: ConfigManager
): InitialiseConfigUseCase {
    override operator fun invoke(defaultValues: Map<String, Any>) {
        configManager.initialiseRemoteConfig(defaultValues)
    }
}