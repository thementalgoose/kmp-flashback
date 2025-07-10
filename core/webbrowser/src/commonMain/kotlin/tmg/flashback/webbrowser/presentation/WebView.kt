package tmg.flashback.webbrowser.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flashback.core.webbrowser.generated.resources.Res
import flashback.core.webbrowser.generated.resources.ic_open_browser
import flashback.core.webbrowser.generated.resources.ic_share
import flashback.core.webbrowser.generated.resources.ic_web_close
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_close
import flashback.presentation.localisation.generated.resources.ab_rss_open_in_browser
import flashback.presentation.localisation.generated.resources.ab_rss_share
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2

@Composable
expect fun WebView(
    url: String,
    domainChanged: (String) -> Unit,
    titleChanged: (String) -> Unit,
    urlChanged: (String) -> Unit,
    progressUpdated: (Float) -> Unit
)

@Composable
fun WebScreen(
    url: String,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    shareClicked: () -> Unit,
    openInBrowser: () -> Unit
) {
    val titleValue = remember { mutableStateOf("title") }
    val domainValue = remember { mutableStateOf("url") }
    val urlValue = remember { mutableStateOf(url) }
    val progress = remember { mutableStateOf(0.0f )}

    Column(modifier = Modifier
        .padding(paddingValues)) {
        Toolbar(
            actionUpClicked = actionUpClicked,
            title = titleValue.value,
            domain = domainValue.value,
            shareClicked = shareClicked,
            openInBrowser = openInBrowser
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier.height(4.dp),
                progress = progress.value,
                color = AppTheme.colors.primary
            )
        }
        WebView(
            url = url,
            domainChanged = { domainValue.value = it },
            titleChanged = { titleValue.value = it },
            urlChanged = { urlValue.value = it },
            progressUpdated = { progress.value = it }
        )
    }
}

@Composable
private fun Toolbar(
    actionUpClicked: () -> Unit,
    title: String,
    domain: String,
    shareClicked: () -> Unit,
    openInBrowser: () -> Unit
) {
    Row(Modifier.padding(
        vertical = 4.dp
    )) {
        IconButton(
            onClick = actionUpClicked
        ) {
            Icon(
                painter = painterResource(resource = Res.drawable.ic_web_close),
                contentDescription = stringResource(resource = string.ab_close),
                tint = AppTheme.colors.onSurface
            )
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(
                horizontal = AppTheme.dimens.medium
            )
        ) {
            TextBody1(
                modifier = Modifier.padding(top = 4.dp),
                text = title,
                maxLines = 1
            )
            TextBody2(
                text = domain,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        IconButton(onClick = { shareClicked() }) {
            Icon(
                painter = painterResource(resource = Res.drawable.ic_share),
                contentDescription = stringResource(resource = string.ab_rss_share),
                tint = AppTheme.colors.onSurface
            )
        }

        IconButton(onClick = { openInBrowser() }) {
            Icon(
                painter = painterResource(resource = Res.drawable.ic_open_browser),
                contentDescription = stringResource(resource = string.ab_rss_open_in_browser),
                tint = AppTheme.colors.onSurface
            )
        }
    }
}