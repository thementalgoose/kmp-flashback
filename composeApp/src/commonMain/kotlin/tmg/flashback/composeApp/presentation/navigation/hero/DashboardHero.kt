package tmg.flashback.composeApp.presentation.navigation.hero

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import flashback.eastereggs.generated.resources.Res
import flashback.eastereggs.generated.resources.ic_easteregg_bonfire
import flashback.eastereggs.generated.resources.ic_easteregg_christmas
import flashback.eastereggs.generated.resources.ic_easteregg_diwali
import flashback.eastereggs.generated.resources.ic_easteregg_easter
import flashback.eastereggs.generated.resources.ic_easteregg_halloween
import flashback.eastereggs.generated.resources.ic_easteregg_newyears
import flashback.eastereggs.generated.resources.ic_easteregg_valentines
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.app_name
import flashback.presentation.localisation.generated.resources.app_subtitle
import flashback.presentation.localisation.generated.resources.easter_egg_slava_ukraine
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.eastereggs.model.MenuIcons
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.ColourType
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.ui.components.text.ShimmerText
import tmg.flashback.ui.components.text.TextWithIcon

@Composable
internal fun DashboardHero(
    modifier: Modifier = Modifier,
    rainbow: Boolean = false,
) {
    Column(modifier.padding(
        top = AppTheme.dimens.xsmall,
        bottom = AppTheme.dimens.xsmall,
        end = AppTheme.dimens.nsmall
    )) {
        TextWithIcon(
            icon = null,
            iconModifier = Modifier
                .rotate(20f)
                .size(18.dp),
            content = {
                if (rainbow) {
                    TextHeadline2(
                        text = stringResource(resource = string.app_name),
                        colourType = ColourType.RAINBOW
                    )
                } else {
                    ShimmerText(
                        text = stringResource(resource = string.app_name),
                    )
                }
            }
        )
        TextCaption(
            modifier = Modifier.padding(top = 4.dp),
            fontStyle = FontStyle.Italic,
            text = stringResource(resource = string.app_subtitle),
            maxLines = 1
        )
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        DashboardHero()
    }
}

@PreviewTheme
@Composable
private fun PreviewValentinesDay() {
    ApplicationThemePreview {
        DashboardHero()
    }
}

@PreviewTheme
@Composable
private fun PreviewChristmas() {
    ApplicationThemePreview {
        DashboardHero()
    }
}

@PreviewTheme
@Composable
private fun PreviewPride() {
    ApplicationThemePreview {
        DashboardHero(
            rainbow = true
        )
    }
}