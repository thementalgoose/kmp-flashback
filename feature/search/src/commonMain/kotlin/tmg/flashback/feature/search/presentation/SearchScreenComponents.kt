package tmg.flashback.feature.search.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.search_category_circuits
import flashback.presentation.localisation.generated.resources.search_category_constructors
import flashback.presentation.localisation.generated.resources.search_category_driver
import flashback.presentation.localisation.generated.resources.search_category_races
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.formula1.enums.TrackLayout
import tmg.flashback.formula1.model.Circuit
import tmg.flashback.formula1.model.Constructor
import tmg.flashback.formula1.model.Driver
import tmg.flashback.formula1.preview.preview
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.constructor.ConstructorImage
import tmg.flashback.ui.components.driver.DriverImage
import tmg.flashback.ui.components.flag.Flag

private val startImageSize = 36.dp
private val endImageSize = 48.dp

@Composable
internal fun SearchHeader(
    category: Category,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.xsmall
            )
    ) {
        TextTitle(
            text = stringResource(category.label),
        )
        HorizontalDivider()
    }
}

@Composable
internal fun DriverEntry(
    model: Result.ResultDriver,
    driverClicked: (Driver) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = { driverClicked(model.driver) }
            )
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
    ) {
        DriverImage(
            photoUrl = model.driver.photoUrl,
            size = startImageSize
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = AppTheme.dimens.nsmall),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextBody1(
                text = model.driver.name,
                bold = true
            )
            val label = listOfNotNull(
//                model.driver.code,
//                model.driver.number?.toString(),
                model.driver.nationality
            ).joinToString(separator = " - ")
            TextBody2(label)
        }
    }
}

@Composable
internal fun ConstructorEntry(
    model: Result.ResultTeam,
    constructorClicked: (Constructor) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = { constructorClicked(model.constructor) }
            )
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.small
            )
    ) {
        ConstructorImage(
            photoUrl = model.constructor.photoUrl,
            constructorColor = model.constructor.colour,
            size = startImageSize - 6.dp,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = AppTheme.dimens.nsmall),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextBody1(
                text = model.constructor.name,
                bold = true
            )
            TextBody2(model.constructor.nationality)
        }
    }
}

@Composable
internal fun CircuitEntry(
    model: Result.ResultCircuit,
    circuitClicked: (Circuit) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier
        .clickable(
            onClick = { circuitClicked(model.circuit) }
        )
        .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
        .padding(
            horizontal = AppTheme.dimens.medium,
            vertical = AppTheme.dimens.small
        )
    ) {
        Flag(
            modifier = Modifier
                .size(startImageSize)
                .align(Alignment.CenterVertically),
            iso = model.circuit.countryISO,
            nationality = model.circuit.country
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = AppTheme.dimens.nsmall),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextBody1(
                text = model.circuit.name,
                bold = true
            )
            TextBody2("${model.circuit.city}, ${model.circuit.country}")
        }
        val track = TrackLayout.getTrack(model.circuit.id)
        if (track != null) {
            Icon(
                modifier = Modifier.size(endImageSize),
                painter = painterResource(track.getDefaultIcon()),
                contentDescription = null,
                tint = AppTheme.colors.onSurface
            )
        }
    }
}

internal val Category.label: StringResource
    get() {
        return when (this) {
            Category.Drivers -> string.search_category_driver
            Category.Teams -> string.search_category_constructors
            Category.Circuits -> string.search_category_circuits
//            Category.Races -> string.search_category_races
        }
    }


@PreviewTheme
@Composable
private fun PreviewDriver() {
    ApplicationThemePreview {
        DriverEntry(
            model = Result.ResultDriver(Driver.preview()),
            driverClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewHeader() {
    ApplicationThemePreview {
        SearchHeader(
            category = Category.Drivers
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewConstructor() {
    ApplicationThemePreview {
        ConstructorEntry(
            model = Result.ResultTeam(Constructor.preview()),
            constructorClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewCircuit() {
    ApplicationThemePreview {
        CircuitEntry(
            model = Result.ResultCircuit(Circuit.preview()),
            circuitClicked = { }
        )
    }
}