package tmg.flashback.composeApp.usecases

interface StoreFirebaseInstallationIdUseCase {
    suspend operator fun invoke(): Boolean
}