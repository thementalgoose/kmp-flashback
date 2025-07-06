package tmg.flashback

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.android.inject
import tmg.flashback.ui.permissions.PermissionManager

class MainActivity : ComponentActivity() {

    val permissionManager: PermissionManager by inject()
    val permissionsController by lazy {
        PermissionsController(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        permissionsController.bind(this)
        permissionManager.permissionsController = permissionsController

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}