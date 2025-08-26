package tmg.flashback.device.usecases

interface CopyToClipboardUseCase {
    operator fun invoke(text: String)
}