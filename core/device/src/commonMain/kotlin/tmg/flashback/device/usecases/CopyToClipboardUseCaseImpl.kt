package tmg.flashback.device.usecases

expect class CopyToClipboardUseCaseImpl(): CopyToClipboardUseCase {
    override fun invoke(text: String)
}