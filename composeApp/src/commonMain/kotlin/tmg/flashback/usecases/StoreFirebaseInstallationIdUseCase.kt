package tmg.flashback.usecases

interface StoreFirebaseInstallationIdUseCase {
    suspend operator fun invoke(): Boolean
}