package tmg.flashback.configuration.usecases

import org.koin.core.annotation.Single
import tmg.flashback.configuration.manager.ConfigManager

interface InitialiseConfigUseCase {
    operator fun invoke(defaultValues: Map<String, Any>)
}

@Single(binds = [InitialiseConfigUseCase::class])
class InitialiseConfigUseCaseImpl(
    private val configManager: ConfigManager
): InitialiseConfigUseCase {
    override operator fun invoke(defaultValues: Map<String, Any>) {
        configManager.initialiseRemoteConfig(defaultValues)
    }
}