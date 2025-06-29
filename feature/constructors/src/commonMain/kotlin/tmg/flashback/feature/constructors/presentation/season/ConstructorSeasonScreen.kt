package tmg.flashback.feature.constructors.presentation.season

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.constructors.presentation.shared.ConstructorHeader
import tmg.flashback.feature.drivers.presentation.shared.ConstructorNotFound
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.swiperefresh.SwipeRefresh


data class ConstructorSeasonInfo(
    val season: Int,
    val id: String,
    val name: String,
)

@Composable
fun ConstructorSeasonScreen(
    constructorSeasonInfo: ConstructorSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    windowSizeClass: WindowSizeClass,
    viewModel: ConstructorSeasonViewModel = koinViewModel()
) {
    LaunchedEffect(constructorSeasonInfo) {
        viewModel.load(constructorSeasonInfo.season, constructorSeasonInfo.id)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    ConstructorSeasonScreen(
        constructorSeasonInfo = constructorSeasonInfo,
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        windowSizeClass = windowSizeClass,
        showBack = showBack,
        uiState = uiState.value,
        refresh = viewModel::refresh,
        isLoading = loading.value,
    )
}


@Composable
fun ConstructorSeasonScreen(
    constructorSeasonInfo: ConstructorSeasonInfo,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean,
    isLoading: Boolean,
    uiState: ConstructorSeasonUiState?,
    windowSizeClass: WindowSizeClass,
    refresh: () -> Unit
) {
    val direction = LocalLayoutDirection.current
    val bottomOnlyPadding = PaddingValues(
        start = paddingValues.calculateStartPadding(direction),
        end = paddingValues.calculateEndPadding(direction),
        bottom = paddingValues.calculateBottomPadding()
    )
    SwipeRefresh(
        isLoading = isLoading,
        onRefresh = refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = bottomOnlyPadding
        ) {
            val constructor = when (uiState) {
                is ConstructorSeasonUiState.Data -> uiState.constructor
                is ConstructorSeasonUiState.NoRaces -> uiState.constructor
                else -> null
            }
            item("header") {
                ConstructorHeader(
                    label = "${constructorSeasonInfo.name}\n${constructorSeasonInfo.season}",
                    constructorImage = constructor?.photoUrl,
                    insetPadding = paddingValues,
                    showBack = showBack,
                    colour = constructor?.colour ?: AppTheme.colors.primary,
                    backClicked = actionUpClicked
                )
            }


            if (uiState is ConstructorSeasonUiState.Data) {
                item("badges") {
//                    DriverBadges(
//                        driver = uiState.driver,
//                        constructors = uiState.constructors
//                    )
                }
                items(uiState.stats) {
                    Stat(it)
                }
            }
            else if (uiState is ConstructorSeasonUiState.NoRaces) {
//                item("badges") {
//                    DriverBadges(
//                        driver = uiState.driver,
//                        constructors = emptyList()
//                    )
//                }
                item("no_races") {
                    ConstructorNotFound()
                }
            }
        }
    }
}


@Composable
private fun Stat(
    model: ConstructorSeasonStat,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.xsmall
        ),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        Icon(
            modifier = Modifier
                .background(AppTheme.colors.surfaceContainer4)
                .clip(CircleShape)
                .size(16.dp),
            painter = painterResource(model.icon),
            contentDescription = null,
            tint = AppTheme.colors.onSurface
        )
        TextBody2(
            modifier = Modifier.weight(1f),
            text = stringResource(model.string),
        )
        TextBody1(
            bold = true,
            text = model.value,
        )
    }
}