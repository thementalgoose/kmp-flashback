@file:OptIn(ExperimentalMaterial3Api::class)

package tmg.flashback.feature.season.presentation.tyres

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.tyres_dry_compounds
import flashback.presentation.localisation.generated.resources.tyres_label
import flashback.presentation.localisation.generated.resources.tyres_size
import flashback.presentation.localisation.generated.resources.tyres_wet_compounds
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import tmg.flashback.formula1.enums.SeasonTyres
import tmg.flashback.formula1.enums.TyreLabel
import tmg.flashback.formula1.enums.getBySeason
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun TyreBottomSheet(
    season: Int,
    dismissed: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = dismissed,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        content = {
            TyreScreen(
                season = season,
                dismissed = dismissed
            )
        }
    )
}

@Composable
private fun TyreScreen(
    season: Int,
    dismissed: () -> Unit
) {
    val tyres = SeasonTyres.getBySeason(season)
    val dry = tyres?.tyres?.filter { it.tyre.isDry } ?: emptyList()
    val wet = tyres?.tyres?.filter { !it.tyre.isDry } ?: emptyList()
    Column(
        modifier = Modifier
            .background(AppTheme.colors.surfaceContainer2)
    ) {
        Header(
            text = stringResource(resource = string.tyres_label),
            action = HeaderAction.CLOSE,
            actionUpClicked = dismissed
        )
        TextBody1(
            modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
            text = stringResource(resource = string.tyres_dry_compounds)
        )
        for (x in dry) {
            TyreRow(tyreLabel = x)
        }
        TextBody1(
            modifier = Modifier.padding(horizontal = AppTheme.dimens.medium),
            text = stringResource(resource = string.tyres_wet_compounds)
        )
        for (x in wet) {
            TyreRow(tyreLabel = x)
        }
        Spacer(modifier = Modifier
            .height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
        )
    }
}

@Composable
private fun TyreRow(
    tyreLabel: TyreLabel,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(
        horizontal = AppTheme.dimens.medium,
        vertical = AppTheme.dimens.small
    )) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(resource = tyreLabel.tyre.icon),
            contentDescription = null
        )
        Column(modifier = Modifier
            .weight(1f)
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
        ) {
            TextHeadline2(text = stringResource(resource = tyreLabel.label))
            TextBody1(text = stringResource(resource = string.tyres_size, tyreLabel.tyre.size))
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        TyreScreen(
            season = 2022,
            dismissed = { }
        )
    }
}