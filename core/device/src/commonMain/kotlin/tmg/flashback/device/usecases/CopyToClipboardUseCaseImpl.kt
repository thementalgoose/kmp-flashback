package tmg.flashback.device.usecases

import org.koin.core.annotation.Single

@Single(binds = [CopyToClipboardUseCase::class])
expect class CopyToClipboardUseCaseImpl(): CopyToClipboardUseCase {
    override fun invoke(text: String)
}