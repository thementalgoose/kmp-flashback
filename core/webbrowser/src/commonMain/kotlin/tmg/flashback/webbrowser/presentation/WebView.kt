package tmg.flashback.webbrowser.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_rss_back
import flashback.presentation.localisation.generated.resources.ab_rss_open_in_browser
import flashback.presentation.localisation.generated.resources.ab_rss_share
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.text.TextBody1

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
    toolbarAtTop: Boolean,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    shareClicked: () -> Unit,
    openInBrowser: () -> Unit,
    webViewImpl: @Composable (
        url: String,
        domainChanged: (String) -> Unit,
        titleChanged: (String) -> Unit,
        urlChanged: (String) -> Unit,
        progressUpdated: (Float) -> Unit
    ) -> Unit = { url, domainChanged, titleChanged, urlChanged, progressUpdated ->
        WebView(
            url = url,
            domainChanged = domainChanged,
            titleChanged = titleChanged,
            urlChanged = urlChanged,
            progressUpdated = progressUpdated
        )
    }
) {
    val titleValue = remember { mutableStateOf("title") }
    val domainValue = remember { mutableStateOf("url") }
    val urlValue = remember { mutableStateOf(url) }
    val progress = remember { mutableStateOf(0.0f )}



    Box(modifier = Modifier.padding(paddingValues)) {
        webViewImpl(
            url,
            { domainValue.value = it },
            { titleValue.value = it },
            { urlValue.value = it },
            { progress.value = it }
        )
        ControlPanel(
            modifier = Modifier
                .padding(AppTheme.dimens.medium)
                .align(if (toolbarAtTop) Alignment.TopCenter else Alignment.BottomCenter),
            backClicked = actionUpClicked,
            shareClicked = shareClicked,
            openInBrowser = openInBrowser
        )
    }
}


@Composable
private fun ControlPanel(
    modifier: Modifier = Modifier,
    backClicked: () -> Unit,
    shareClicked: () -> Unit,
    openInBrowser: () -> Unit,
) {
    Row(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(AppTheme.colors.surfaceContainer5)
                .padding(AppTheme.dimens.xsmall)
        ) {
            IconButton(
                onClick = backClicked,
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = stringResource(string.ab_rss_back)
                    )
                }
            )
        }
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(AppTheme.colors.surfaceContainer5)
                .padding(AppTheme.dimens.xsmall)
        ) {
            IconButton(
                onClick = shareClicked,
                content = {
                    Icon(
                        imageVector = Icons.Default.Share,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = stringResource(string.ab_rss_share)
                    )
                }
            )
            IconButton(
                onClick = openInBrowser,
                content = {
                    Icon(
                        imageVector = Icons.Default.OpenInBrowser,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = stringResource(string.ab_rss_open_in_browser)
                    )
                }
            )
        }
    }
}

@Composable
@Preview
private fun PreviewTop() {
    ApplicationThemePreview {
        WebScreen(
            url = "url",
            toolbarAtTop = true,
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = {},
            shareClicked = {},
            openInBrowser = {},
            webViewImpl = { url, domainChanged, titleChanged, urlChanged, progressUpdated ->
                Box(Modifier.fillMaxSize()
                    .padding(8.dp)
                    .border(width = 1.dp, Color.Magenta)
                    .padding(8.dp)) {
                    TextBody1(
                        text = "WebView",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}

@Composable
@Preview
private fun PreviewBottom() {
    ApplicationThemePreview {
        WebScreen(
            url = "url",
            toolbarAtTop = false,
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = {},
            shareClicked = {},
            openInBrowser = {},
            webViewImpl = { url, domainChanged, titleChanged, urlChanged, progressUpdated ->
                Box(Modifier.fillMaxSize()
                    .padding(8.dp)
                    .border(width = 1.dp, Color.Magenta)
                    .padding(8.dp)) {
                    TextBody1(
                        text = "WebView",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        )
    }
}