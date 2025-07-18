package tmg.flashback.feature.rss.presentation.configure

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.settings_rss_add_cancel
import flashback.presentation.localisation.generated.resources.settings_rss_add_confirm
import flashback.presentation.localisation.generated.resources.settings_rss_add_description
import flashback.presentation.localisation.generated.resources.settings_rss_add_title
import flashback.presentation.localisation.generated.resources.settings_rss_configure
import flashback.presentation.localisation.generated.resources.settings_rss_configure_sources_title
import flashback.presentation.localisation.generated.resources.settings_rss_show_description_description
import flashback.presentation.localisation.generated.resources.settings_rss_show_description_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.feature.rss.models.SupportedSource
import tmg.flashback.feature.rss.presentation.configure.component.Source
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.buttons.ButtonPrimary
import tmg.flashback.style.buttons.ButtonSecondary
import tmg.flashback.style.input.InputSwitch
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextSection
import tmg.flashback.style.text.TextTitle
import tmg.flashback.style.textinput.TextInput
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

private val badgeSize: Dp = 42.dp

@Composable
fun RssConfigureScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    showBack: Boolean,
    viewModel: RssConfigureViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    RssConfigureScreen(
        actionUpClicked = actionUpClicked,
        insetPadding = insetPadding,
        showBack = showBack,
        uiState = uiState.value,
        updateSource = viewModel::updateSource,
        updateShowDescription = viewModel::updateShowDescription,
        openContactLink = viewModel::clickContactLink
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RssConfigureScreen(
    actionUpClicked: () -> Unit,
    insetPadding: PaddingValues,
    uiState: RssConfigureUiState,
    showBack: Boolean,
    updateSource: (String, Boolean) -> Unit,
    updateShowDescription: (Boolean) -> Unit,
    openContactLink: (SupportedSource) -> Unit,
) {
    val showAddDialog = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = insetPadding,
        content = {
            item("header") {
                Header(
                    actionUpClicked = actionUpClicked,
                    action = HeaderAction.BACK.takeIf { showBack },
                    text = stringResource(string.settings_rss_configure_sources_title)
                )
            }
            item("show_description") {
                Column {
                    SettingHeader(
                        title = string.settings_rss_show_description_title
                    )
                    ShowDescription(
                        isChecked = uiState.showDescription,
                        modifier = Modifier
                            .clickable {
                                updateShowDescription(!uiState.showDescription)
                            }
                            .padding(
                                horizontal = AppTheme.dimens.medium,
                                vertical = AppTheme.dimens.small
                            )
                    )
                }
            }

            if (uiState.showAddCustom) {
                item("add-custom") {
                    Column {
                        SettingHeader(
                            title = string.settings_rss_add_title
                        )
                        AddCustom(
                            modifier = Modifier
                                .clickable {
                                    showAddDialog.value = true
                                }
                                .padding(
                                    horizontal = AppTheme.dimens.medium,
                                    vertical = AppTheme.dimens.small
                                )
                        )
                    }
                }
            }

            item("source_header") {
                SettingHeader(
                    title = string.settings_rss_configure
                )
            }
            items(uiState.sources, key = { it.article.rssLink }) {
                Source(
                    model = it,
                    sourceAdded = { updateSource(it.rssLink, true) },
                    sourceRemoved = { updateSource(it.rssLink, false) },
                    contactLink = { openContactLink(it) }
                )
            }
        }
    )

    if (showAddDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { },
            content = {
                CustomSourceDialog(
                    addCustomSource = {
                        updateSource(it, true)
                        showAddDialog.value = false
                    },
                    onDismissClicked = { showAddDialog.value = false }
                )
            }
        )
    }
}


@Composable
fun SettingHeader(
    title: StringResource,
    modifier: Modifier = Modifier.padding(
        vertical = AppTheme.dimens.nsmall,
        horizontal = AppTheme.dimens.medium
    ),
) {
    Box(modifier = modifier) {
        TextSection(
            brand = true,
            text = stringResource(title)
        )
    }
}

@Composable
private fun ShowDescription(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(end = 8.dp)
        ) {
            TextTitle(
                text = stringResource(resource = string.settings_rss_show_description_title)
            )
            TextBody2(
                text = stringResource(resource = string.settings_rss_show_description_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
        }
        InputSwitch(
            isChecked = isChecked,
        )
    }
}

@Composable
private fun AddCustom(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(end = 8.dp)
        ) {
            TextTitle(
                text = stringResource(resource = string.settings_rss_add_title)
            )
            TextBody2(
                text = stringResource(resource = string.settings_rss_add_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp)
            )
        }
    }
}

@Composable
private fun CustomSourceDialog(
    addCustomSource: (String) -> Unit,
    onDismissClicked: () -> Unit,
) {
    val input = remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
            .background(AppTheme.colors.surface)
            .padding(
                horizontal = AppTheme.dimens.medium,
                vertical = AppTheme.dimens.medium,
            ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        TextTitle(
            text = stringResource(string.settings_rss_add_title),
            bold = true,
        )
        TextBody2(
            text = stringResource(string.settings_rss_add_description)
        )
        TextInput(
            text = input,
            placeholder = "https://.....",
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
        ) {
            Spacer(Modifier.weight(1f))
            ButtonSecondary(
                text = stringResource(string.settings_rss_add_cancel),
                onClick = { onDismissClicked() }
            )
            ButtonPrimary(
                text = stringResource(string.settings_rss_add_confirm),
                onClick = {
                    addCustomSource(input.value.text)
                }
            )
        }
    }
}



@Preview
@Composable
private fun PreviewDialog(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        CustomSourceDialog(
            addCustomSource = { },
            onDismissClicked = { }
        )
    }
}



@Preview
@Composable
private fun PreviewList(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        RssConfigureScreen(
            actionUpClicked = { },
            insetPadding = PaddingValues(0.dp),
            showBack = true,
            uiState = RssConfigureUiState(
                sources = listOf(
                    ConfiguredSupportedSource(fakeArticleSource.copy(rssLink = "https://www.test.com"), isEnabled = true),
                    ConfiguredSupportedSource(fakeArticleSource, isEnabled = false)
                ),
                showDescription = true,
                showAddCustom = true
            ),
            updateSource = { source, enabled -> },
            updateShowDescription = { },
            openContactLink = { }
        )
    }
}

private val fakeArticleSource = SupportedSource(
    title = "help",
    colour = "#981494",
    textColour = "#ffffff",
    rssLink = "https://www.google.com",
    source = "google",
    sourceShort = "GOO",
    contactLink = "https://www.google.com/help"
)