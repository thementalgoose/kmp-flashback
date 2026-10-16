package tmg.flashback.style.preview

import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_NO
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Mode",
    showBackground = true,
    backgroundColor = 0xFFF8F8F8,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF080808,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
annotation class PreviewTheme

@Preview(
    name = "Pixel - Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF8F8F8,
    device = Devices.PIXEL,
)
@Preview(
    name = "Pixel - Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF080808,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL,
)
annotation class PreviewDevicePixel

@Preview(
    name = "Tablet - Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0x080808,
    device = Devices.TABLET,
)
@Preview(
    name = "Tablet - Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF080808,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    device = Devices.TABLET,
)
annotation class PreviewDeviceTablet
@Preview(
    name = "Foldable - Light Mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFF8F8F8,
    device = Devices.FOLDABLE,
)
@Preview(
    name = "Foldable - Dark Mode",
    showBackground = true,
    backgroundColor = 0xFF080808,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    device = Devices.FOLDABLE,
)
annotation class PreviewDeviceFoldable

@PreviewDevicePixel
@PreviewDeviceFoldable
@PreviewDeviceTablet
annotation class PreviewDevices