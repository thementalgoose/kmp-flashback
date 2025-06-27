package tmg.flashback.feature.rss.presentation.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.window.core.layout.WindowWidthSizeClass.Companion.COMPACT
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.rss.models.Article
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.navigation.MasterDetailsPane
import tmg.flashback.ui.navigation.appBarMaximumHeight
import tmg.flashback.ui.navigation.rememberMasterDetailPaneState

data class NavigationArticle(
    val article: Article
)

@Composable
fun RssFeedGraph(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: RSSFeedViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val navigator = rememberMasterDetailPaneState<NavigationArticle>()

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

                },
                showMenu = windowSizeClass.windowWidthSizeClass == COMPACT
            )
        },
        detailsActionUpClicked = {
            navigator.clear()
        },
        details = { model, actionUpClicked ->
            TextTitle("Model $model")
        }
    )
}