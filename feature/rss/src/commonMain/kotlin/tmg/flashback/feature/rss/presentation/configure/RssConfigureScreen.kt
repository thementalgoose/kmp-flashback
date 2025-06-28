package tmg.flashback.feature.rss.presentation.configure

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_rss_configure_sources_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.presentation.configure.component.Source
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

private val badgeSize: Dp = 42.dp

@Composable
fun RssConfigureScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: RssConfigureViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    RssConfigureScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateSource = viewModel::updateSource,
        updateShowDescription = viewModel::updateShowDescription
    )
}

@Composable
fun RssConfigureScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    uiState: RssConfigureUiState,
    showBack: Boolean,
    updateSource: (SupportedSource, Boolean) -> Unit,
    updateShowDescription: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding,
        content = {
            item("header") {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.BACK.takeIf { showBack },
                    text = stringResource(string.settings_rss_configure_sources_title)
                )
            }
            item("show_description") {

            }
            items(uiState.sources, key = { it.article.rssLink }) {
                Source(
                    model = it,
                    sourceAdded = { updateSource(it, true) },
                    sourceRemoved = { updateSource(it, false) },
                    contactLink = { }
                )
            }
        }
    )
}