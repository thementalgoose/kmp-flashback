package tmg.flashback.feature.search.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.search_category_circuits
import flashback.presentation.localisation.generated.resources.search_category_constructors
import flashback.presentation.localisation.generated.resources.search_category_driver
import flashback.presentation.localisation.generated.resources.search_category_races
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextTitle

@Composable
internal fun SearchHeader(
    category: Category,
    modifier: Modifier = Modifier
) {
    TextTitle(
        text = stringResource(category.label),
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
    )
}

@Composable
internal fun DriverEntry(
    model: Result.ResultDriver,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
    ) {
        TextBody1(model.driver.name)
    }
}

@Composable
internal fun ConstructorEntry(
    model: Result.ResultTeam,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
    ) {
        TextBody1(model.constructor.name)
    }
}

@Composable
internal fun CircuitEntry(
    model: Result.ResultCircuit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
    ) {
        TextBody1(model.circuit.name)
    }
}

internal val Category.label: StringResource
    get() {
        return when (this) {
            Category.Drivers -> string.search_category_driver
            Category.Teams -> string.search_category_constructors
            Category.Circuits -> string.search_category_circuits
            Category.Races -> string.search_category_races
        }
    }