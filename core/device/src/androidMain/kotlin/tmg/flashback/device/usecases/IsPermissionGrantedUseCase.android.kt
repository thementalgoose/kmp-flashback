package tmg.flashback.device.usecases

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import org.koin.java.KoinJavaComponent
import tmg.flashback.device.models.Permission
import tmg.flashback.device.models.PermissionState
import tmg.flashback.device.models.manifestKey

actual class IsPermissionGrantedUseCaseImpl actual constructor(): IsPermissionGrantedUseCase {

    private fun getApplicationContext(): Context {
        return KoinJavaComponent.get(Context::class.java)
    }

    actual override suspend fun invoke(permission: Permission): PermissionState {
        val result = getApplicationContext().checkSelfPermission(permission.manifestKey)
        return when (result) {
            PERMISSION_GRANTED -> PermissionState.Granted
            PERMISSION_DENIED -> PermissionState.Denied
            else -> PermissionState.Unknown
        }
    }
}