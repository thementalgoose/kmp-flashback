package tmg.flashback.device.usecases

import tmg.flashback.device.models.Permission

interface RequestPermissionUseCase {
    operator fun invoke(permission: Permission)
}

expect class RequestPermissionUseCaseImpl(): RequestPermissionUseCase {
    override fun invoke(permission: Permission)
}