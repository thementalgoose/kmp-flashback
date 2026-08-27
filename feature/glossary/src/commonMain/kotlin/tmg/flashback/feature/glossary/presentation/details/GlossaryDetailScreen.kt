package tmg.flashback.feature.glossary.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsCircuitId
import tmg.flashback.analytics.constants.AnalyticsConstants.analyticsGlossary
import tmg.flashback.analytics.presentation.ScreenView
import tmg.flashback.feature.glossary.presentation.all.GlossaryUiState
import tmg.flashback.feature.glossary.presentation.all.GlossaryViewModel
import tmg.flashback.formula1.constants.Glossary
import tmg.flashback.formula1.model.Location
import tmg.flashback.navigation.NavCircuit
import tmg.flashback.navigation.NavGlossaryDetail
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.ui.components.Refresh
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh

@Composable
fun GlossaryDetailScreen(
    data: NavGlossaryDetail,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: GlossaryDetailViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    ScreenView("Glossary Detail", args = mapOf(
        analyticsGlossary to data.id
    ))

    LaunchedEffect(data) {
        viewModel.load(data.id)
    }

    GlossaryDetailScreen(
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        uiState = uiState.value,
    )
}

@Composable
private fun GlossaryDetailScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    showBack: Boolean,
    uiState: GlossaryDetailUiState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = paddingValues
    ) {
        item("header") {
            Header(
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack },
                text = uiState.glossary?.let { stringResource(it.label) } ?: "",
                overrideIcons = {}
            )
        }
        val glossary = uiState.glossary ?: return@LazyColumn
        item("details") {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.medium)
            ) {
//                Icon(
//                    painter = painterResource()
//                )
                TextBody1(
                    text = stringResource(glossary.desc)
                )
            }
        }
    }
}