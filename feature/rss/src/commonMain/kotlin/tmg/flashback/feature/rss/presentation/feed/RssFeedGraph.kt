package tmg.flashback.feature.rss.presentation.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.COMPACT
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.feature.rss.presentation.configure.RssConfigureScreen
import tmg.flashback.ui.navigation.MasterDetailPaneState
import tmg.flashback.ui.navigation.MasterDetailsPane

sealed interface RssNavigation {
    data object Configure: RssNavigation
    data class WebPage(
        val article: Article
    ): RssNavigation
}

@Composable
fun RssFeedGraph(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    navigator: MasterDetailPaneState<RssNavigation>,
    viewModel: RSSFeedViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    MasterDetailsPane(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        master = {
            RSSScreen(
                paddingValues = paddingValues,
                actionUpClicked = actionUpClicked,
                uiState = uiState.value,
                refresh = viewModel::refresh,
                itemClicked = {

                },
                configureSources = {
                    navigator.navigateTo(RssNavigation.Configure)
                },
                showMenu = windowSizeClass.windowWidthSizeClass == COMPACT
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            when (model) {
                RssNavigation.Configure -> RssConfigureScreen(
                    actionUpClicked = actionUpClicked,
                    insetPadding = paddingValues,
                    showBack = windowSizeClass.windowWidthSizeClass == COMPACT
                )
                is RssNavigation.WebPage -> {

                }
            }
        }
    )
}