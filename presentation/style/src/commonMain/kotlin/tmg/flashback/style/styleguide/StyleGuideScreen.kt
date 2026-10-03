@file:OptIn(ExperimentalLayoutApi::class)

package tmg.flashback.style.styleguide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import flashback.presentation.style.generated.resources.Res
import flashback.presentation.style.generated.resources.ic_preview_icon
import flashback.presentation.style.generated.resources.preview
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonItem
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.buttons.ButtonTertiary
import tmg.flashback.style.buttons.Segments
import tmg.flashback.style.input.InputRadio
import tmg.flashback.style.input.InputSelection
import tmg.flashback.style.input.InputSwitch
import tmg.flashback.style.preview.PreviewTheme
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextCaption
import tmg.flashback.style.text.TextHeadline1
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.style.theme.LocalDarkMode

@Composable
fun StyleGuideScreen(
    actionUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier
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
                            contentDescription = null
                        )
                    }
                )
                TextHeadline1(
                    text = "Style Guide",
                    modifier = Modifier
                        .padding(horizontal = AppTheme.dimens.medium)
                        .fillMaxWidth()
                )
            }
        }
        item("texts") {
            StyleGuideSection(title = "Texts") {
                StyleGuideTexts()
            }
        }
        item("buttons") {
            StyleGuideSection(title = "Buttons") {
                StyleGuideButtons()
            }
        }
        item("inputs") {
            StyleGuideSection(title = "Inputs") {
                StyleGuideInputs()
            }
        }
        item("surfaces") {
            StyleGuideSection(title = "Surfaces") {
                StyleGuideSurfaces()
            }
        }
        item("formula1") {
            StyleGuideSection(title = "Formula 1 Colors") {
                StyleGuideFormula1()
            }
        }
    }
}

@Composable
private fun StyleGuideSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.medium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        TextHeadline2(text = title)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surfaceContainer2)
                .padding(AppTheme.dimens.medium)
        ) {
            content()
        }
    }
}

@Composable
fun StyleGuideTexts(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextHeadline1("Headline 1")
        TextHeadline2("Headline 2")
        TextTitle("Title 1")
        TextBody1("Body 1")
        TextBody2("Body 2")
        TextCaption("Caption")
    }
}

@Composable
fun StyleGuideButtons(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ButtonPrimary("Primary button", onClick = { })
        ButtonPrimary("Primary button (Disabled)", onClick = { }, enabled = false)
        ButtonSecondary("Secondary button", onClick = { })
        ButtonSecondary("Secondary button (Disabled)", onClick = { }, enabled = false)
        val fakeItems = listOf(
            ButtonItem("1", Res.string.preview),
            ButtonItem("2", Res.string.preview),
            ButtonItem("3", Res.string.preview)
        )
        Segments(fakeItems, fakeItems.first(), segmentClicked = { })
        Segments(fakeItems, fakeItems.first(), segmentClicked = { }, showTick = true)
        Segments(fakeItems, fakeItems.last(), segmentClicked = { }, enabled = false)
        ButtonTertiary("Tertiary button", onClick = { })
        ButtonTertiary("Tertiary button (Disabled)", onClick = { }, enabled = false)
    }
}

@Composable
fun StyleGuideInputs(
    modifier: Modifier = Modifier
) {
    val textState = remember { mutableStateOf(TextFieldValue("Hey")) }
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextInput(textState, placeholder = "backup")
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InputRadio(isChecked = true)
            InputRadio(isChecked = false)
            InputSwitch(isChecked = true)
            InputSwitch(isChecked = false)
        }
        HorizontalDivider()
        InputSelection("label", icon = Res.drawable.ic_preview_icon, isSelected = false)
        InputSelection("label", icon = Res.drawable.ic_preview_icon, isSelected = true)
    }
}

@Composable
fun StyleGuideSurfaces(
    modifier: Modifier = Modifier
) {
    @Composable
    fun Container(colour: Color, label: String, labelColour: Color? = null) {
        Box(
            Modifier
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(colour)
                .padding(16.dp)
        ) {
            TextBody2(label, textColor = labelColour)
        }
    }

    @Composable
    fun Component(colour: Color, label: String, labelColour: Color? = null) {
        Box(
            Modifier
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(colour)
                .padding(16.dp)
        ) {
            TextBody2(label, textColor = labelColour)
        }
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Container(AppTheme.colors.surfaceContainer1, "surfaceContainer1")
        Container(AppTheme.colors.surfaceContainer2, "surfaceContainer2")
        Container(AppTheme.colors.surfaceContainer3, "surfaceContainer3")
        Container(AppTheme.colors.surfaceContainer4, "surfaceContainer4", AppTheme.colors.onSurfaceVariant)
        Container(AppTheme.colors.surfaceContainer5, "surfaceContainer5", AppTheme.colors.onSurfaceVariant)
        Container(AppTheme.colors.primaryContainer, "primaryContainer", AppTheme.colors.onPrimaryContainer)
        Container(AppTheme.colors.secondaryContainer, "secondaryContainer", AppTheme.colors.onSecondaryContainer)
        Container(AppTheme.colors.tertiaryContainer, "tertiaryContainer", AppTheme.colors.onTertiaryContainer)
        Container(AppTheme.colors.errorContainer, "errorContainer", AppTheme.colors.onErrorContainer)
        Container(AppTheme.colors.surfaceInverse, "surfaceInverse", AppTheme.colors.onSurfaceInverse)
        Container(AppTheme.colors.surfaceNav, "surfaceNav")
        HorizontalDivider()
        Component(AppTheme.colors.primary, "primary", AppTheme.colors.onPrimary)
        Component(AppTheme.colors.secondary, "secondary", AppTheme.colors.onSecondary)
        Component(AppTheme.colors.tertiary, "tertiary", AppTheme.colors.onTertiary)
        Component(AppTheme.colors.error, "error", AppTheme.colors.onError)
        Component(AppTheme.colors.primaryInverse, "primaryInverse", AppTheme.colors.onPrimaryContainer)
    }
}

@Composable
fun StyleGuideFormula1(
    modifier: Modifier = Modifier
) {
    @Composable
    fun ColourDesc(colour: Color, label: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextBody2(label)
            Box(
                Modifier
                    .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                    .padding(top = 8.dp)
                    .size(40.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_preview_icon),
                    tint = colour,
                    contentDescription = null
                )
            }
        }
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ColourDesc(AppTheme.colors.f1DeltaPositive, "delta positive")
        ColourDesc(AppTheme.colors.f1DeltaNeutral, "delta neutral")
        ColourDesc(AppTheme.colors.f1DeltaNegative, "delta negative")
        HorizontalDivider()
        ColourDesc(AppTheme.colors.f1ResultsFull, "result full")
        ColourDesc(AppTheme.colors.f1ResultsNeutral, "result neutral")
        ColourDesc(AppTheme.colors.f1ResultsPartial, "result partial")
        ColourDesc(AppTheme.colors.f1ResultsUpcoming, "result upcoming")
        HorizontalDivider()
        ColourDesc(AppTheme.colors.f1FastestSector, "purple sector")
        HorizontalDivider()
        ColourDesc(AppTheme.colors.f1Championship, "championship")
        HorizontalDivider()
        ColourDesc(AppTheme.colors.f1StartLightGreen, "start green")
        ColourDesc(AppTheme.colors.f1StartLightAmber, "start amber")
        ColourDesc(AppTheme.colors.f1StartLightRed, "start red")
        HorizontalDivider()
        ColourDesc(AppTheme.colors.f1Podium1, "podium 1")
        ColourDesc(AppTheme.colors.f1Podium2, "podium 2")
        ColourDesc(AppTheme.colors.f1Podium3, "podium 3")
    }
}

@PreviewTheme
@Composable
private fun PreviewStyleGuideScreen() {
    ApplicationThemePreview {
        StyleGuideScreen(
            actionUpClicked = { }
        )
    }
}

@PreviewTheme
@Composable
private fun PreviewTexts() {
    ApplicationThemePreview {
        StyleGuideTexts(modifier = Modifier.padding(8.dp))
    }
}

@PreviewTheme
@Composable
private fun PreviewButtons() {
    ApplicationThemePreview {
        StyleGuideButtons(modifier = Modifier.padding(8.dp))
    }
}

@PreviewTheme
@Composable
private fun PreviewInputs() {
    ApplicationThemePreview {
        StyleGuideInputs(modifier = Modifier.padding(8.dp))
    }
}

@PreviewTheme
@Composable
private fun PreviewSurfaces() {
    ApplicationThemePreview {
        StyleGuideSurfaces(modifier = Modifier.padding(8.dp))
    }
}

@PreviewTheme
@Composable
private fun PreviewFormula1s() {
    ApplicationThemePreview {
        StyleGuideFormula1(modifier = Modifier.padding(8.dp))
    }
}
