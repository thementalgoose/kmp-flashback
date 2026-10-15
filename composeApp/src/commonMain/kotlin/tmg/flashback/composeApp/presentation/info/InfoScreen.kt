package tmg.flashback.composeApp.presentation.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
internal fun InfoScreen(
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    paddingValues: PaddingValues,
    viewModel: InfoViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    InfoScreen(
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        paddingValues = paddingValues,
        refresh = viewModel::refresh,
        uiState = uiState.value
    )
}

@Composable
internal fun InfoScreen(
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    paddingValues: PaddingValues,
    refresh: () -> Unit,
    uiState: InfoUiState,
) {
    SwipeRefresh(
        isLoading = false,
        onRefresh = refresh,
        windowSizeClass = windowSizeClass
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.surfaceContainer1),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
        ) {
            item("header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = actionUpClicked,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = AppTheme.colors.onSurface
                            )
                        }
                    )
                    TextHeadline1(
                        text = "Info",
                        modifier = Modifier
                            .padding(horizontal = AppTheme.dimens.medium)
                            .fillMaxWidth()
                    )
                }
            }
            item("texts") {
                Column(
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.medium),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall)
                ) {
                    TextTitle("Config URL")
                    TextBody1(uiState.baseUrl)
                }
            }
        }
    }
}