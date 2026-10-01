@file:OptIn(ExperimentalMaterial3Api::class)

package tmg.flashback.ui.components.tyres

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.tyres_dry_compounds
import flashback.presentation.localisation.generated.resources.tyres_label
import flashback.presentation.localisation.generated.resources.tyres_size
import flashback.presentation.localisation.generated.resources.tyres_wet_compounds
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.unknown_avatar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline3
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.header.Header

@Composable
fun TyreBottomSheet(
    season: Int,
    dry: List<TyreInfo>,
    wet: List<TyreInfo>,
    show: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { show.value = false },
        containerColor = AppTheme.colors.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
            Box(Modifier
                .padding(vertical = AppTheme.dimens.nsmall)
                .clip(RoundedCornerShape(4.dp))
                .background(AppTheme.colors.surfaceInverse)
                .size(width = 50.dp, height = 4.dp)
            )
        },
        content = {
            TyreScreen(
                season = season,
                dry = dry,
                wet = wet,
                dismissed = { show.value = false}
            )
        }
    )
}

@Composable
private fun TyreScreen(
    season: Int,
    dry: List<TyreInfo>,
    wet: List<TyreInfo>,
    dismissed: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(AppTheme.colors.surface),
        columns = GridCells.Adaptive(minSize = 250.dp),
        content = {
            item("header", span = { GridItemSpan(maxLineSpan) }) {
                Header(
                    // Needed for the dialog to show the background colour in XR.
                    modifier = Modifier.background(AppTheme.colors.surface),
                    text = "$season\n${stringResource(resource = string.tyres_label)}",
                    action = null,
                    actionUpClicked = dismissed
                )
            }
            item("dry", span = { GridItemSpan(maxLineSpan) }) {
                TextHeadline3(
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                    text = stringResource(resource = string.tyres_dry_compounds)
                )
            }
            items(dry) {
                TyreRow(tyreInfo = it)
            }
            item("wet", span = { GridItemSpan(maxLineSpan) }) {
                TextHeadline3(
                    modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
                    text = stringResource(resource = string.tyres_wet_compounds)
                )
            }
            items(wet) {
                TyreRow(tyreInfo = it)
            }
            item("spacer", span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier
                    .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                )
            }
        }
    )
}

@Composable
private fun TyreRow(
    tyreInfo: TyreInfo,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(
        horizontal = AppTheme.dimens.medium,
        vertical = AppTheme.dimens.small
    )) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(resource = tyreInfo.icon),
            contentDescription = null
        )
        Column(modifier = Modifier
            .weight(1f)
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
        ) {
            TextTitle(text = stringResource(resource = tyreInfo.label), bold = true)
            TextBody1(text = stringResource(resource = string.tyres_size, tyreInfo.size))
        }
    }
}

@PreviewTheme
@Composable
private fun Preview() {
    ApplicationThemePreview {
        TyreScreen(
            season = 2022,
            dry = listOf(fakeTyre),
            wet = listOf(fakeTyre),
            dismissed = { }
        )
    }
}

private val fakeTyre = TyreInfo(
    icon = Res.drawable.unknown_avatar,
    label = string.tyres_label,
    size = 13
)