package tmg.flashback.feature.about.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.ab_back
import flashback.presentation.localisation.generated.resources.about_additional
import flashback.presentation.localisation.generated.resources.about_dependencies
import flashback.presentation.localisation.generated.resources.app_name
import flashback.presentation.localisation.generated.resources.app_version
import flashback.presentation.localisation.generated.resources.dependency_thank_you
import flashback.presentation.ui.generated.resources.Res
import flashback.presentation.ui.generated.resources.arrow_down
import flashback.presentation.ui.generated.resources.ic_menu
import flashback.presentation.ui.generated.resources.unknown_avatar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.infrastructure.device.Device
import tmg.flashback.style.AppTheme
import tmg.flashback.style.AppThemePreview
import tmg.flashback.style.LOGO_GRADIENT_1
import tmg.flashback.style.LOGO_GRADIENT_2
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextTitle

@Composable
fun AboutScreen(
    appIcon: DrawableResource,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    windowSizeClass: WindowSizeClass,
    viewModel: AboutViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        AboutListScreen(
            icon = appIcon,
            paddingValues = paddingValues,
            actionUpClicked = actionUpClicked,
            email = uiState.value.contactEmail,
            deviceId = uiState.value.deviceUuid,
            installationId = "n/a"
        )
    } else {
        AboutPaneScreen(
            icon = appIcon,
            paddingValues = paddingValues,
            actionUpClicked = actionUpClicked,
            email = uiState.value.contactEmail,
            deviceId = uiState.value.deviceUuid,
            installationId = "n/a"
        )
    }
}

@Composable
private fun AboutListScreen(
    icon: DrawableResource,
    email: String,
    deviceId: String,
    installationId: String,
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
) {
    val showDependencies = remember { mutableStateOf(true) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item("back") {
            ActionUpButton(actionUpClicked = actionUpClicked)
        }
        item("hero") {
            Hero(
                modifier = Modifier
                    .animateItem()
                    .padding(horizontal = AppTheme.dimens.medium),
                email = email,
                icon = icon,
            )
        }
        item("header") {
            Header(
                modifier = Modifier
                    .animateItem()
                    .padding(
                        horizontal = AppTheme.dimens.medium,
                        vertical = AppTheme.dimens.medium
                    )
            )
        }

        item("section") {
            Section(
                title = stringResource(string.about_dependencies),
                isExpanded = showDependencies,
                modifier = Modifier
                    .animateItem()
                    .clickable { showDependencies.value = !showDependencies.value }
                    .padding(horizontal = AppTheme.dimens.medium)
            )
        }

        if (showDependencies.value) {
            items(AboutDependency.entries, key = { it.name }) {
                Dependency(
                    dependency = it,
                    dependencyClicked = { },
                    modifier = Modifier
                        .animateItem()
                        .padding(horizontal = AppTheme.dimens.medium)
                )
            }
        }

        item("footer") {
            Footer(
                modifier = Modifier
                    .animateItem()
                    .padding(horizontal = AppTheme.dimens.medium),
                version = Device.versionName,
                debugIds = "$deviceId\n$installationId"
            )
        }
    }
}

@Composable
private fun AboutPaneScreen(
    paddingValues: PaddingValues,
    icon: DrawableResource,
    email: String,
    deviceId: String,
    installationId: String,
    actionUpClicked: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            item("hero") {
                Hero(
                    modifier = Modifier
                        .animateItem()
                        .padding(
                            start = AppTheme.dimens.medium,
                            end = AppTheme.dimens.medium,
                            top = AppTheme.dimens.medium,
                            bottom = AppTheme.dimens.small
                        ),
                    icon = icon,
                    email = email
                )
            }
            item("header") {
                Header(modifier = Modifier
                    .animateItem()
                    .padding(horizontal = AppTheme.dimens.medium))
            }

            item("footer") {
                Footer(
                    modifier = Modifier
                        .animateItem()
                        .padding(horizontal = AppTheme.dimens.medium),
                    version = Device.versionName,
                    debugIds = "$deviceId\n$installationId"
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(paddingValues)
                .padding(AppTheme.dimens.medium)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(AppTheme.colors.surfaceContainer3),
            contentPadding = PaddingValues(AppTheme.dimens.medium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
        ) {
            items(AboutDependency.entries) {
                Dependency(
                    dependency = it,
                    dependencyClicked = { },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Composable
private fun ActionUpButton(
    actionUpClicked: () -> Unit
) {
    IconButton(onClick = actionUpClicked) {
        Icon(
            painter = painterResource(resource = flashback.presentation.ui.generated.resources.Res.drawable.ic_menu),
            contentDescription = stringResource(resource = string.ab_back),
            tint = AppTheme.colors.onSurface
        )
    }
}

@Composable
private fun Hero(
    icon: DrawableResource,
    email: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium)
    ) {
        Image(
            painter = painterResource(resource = icon),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .background(linearGradient(
                    colors = listOf(LOGO_GRADIENT_2, LOGO_GRADIENT_1),
                ))
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
        ) {
            TextHeadline2(
                text = stringResource(string.app_name)
            )
            TextBody1(
                text = "Contact Email"
            )
            TextBody2(
                text = email
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier
) {
    TextBody1(
        modifier = modifier,
        text = stringResource(string.dependency_thank_you)
    )
}

@Composable
private fun Section(
    title: String,
    isExpanded: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    val degrees = animateFloatAsState(
        targetValue = if (isExpanded.value) 0f else 90f,
        label = "rotation"
    )
    Row(
        modifier = modifier
            .padding(vertical = AppTheme.dimens.small)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody1(
            text = title,
            modifier = Modifier.weight(1f)
        )
        Icon(
            modifier = Modifier.rotate(degrees.value),
            painter = painterResource(resource = flashback.presentation.ui.generated.resources.Res.drawable.arrow_down),
            contentDescription = null,
            tint = AppTheme.colors.onSurface
        )
    }
}

@Composable
private fun Footer(
    version: String,
    debugIds: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            top = AppTheme.dimens.small,
            bottom = AppTheme.dimens.nsmall
        ),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.small)
    ) {
        TextBody1(
            modifier = Modifier,
            text = stringResource(string.about_additional)
        )

        Spacer(Modifier.height(4.dp))

        TextBody2(
            text = debugIds,
            textColor = AppTheme.colors.onSurfaceVariant
        )

        Spacer(Modifier.height(4.dp))

        TextBody1(
            text = stringResource(string.app_version)
        )
        TextBody2(
            text = version,
            bold = true
        )
    }
}

private val dependencyIconSize = 36.dp

@Composable
private fun Dependency(
    dependency: AboutDependency,
    dependencyClicked: (AboutDependency) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
                .padding(start = dependencyIconSize / 2)
                .clip(RoundedCornerShape(AppTheme.dimens.radiusMedium))
                .clickable { dependencyClicked(dependency) }
                .background(AppTheme.colors.surfaceContainer4)
                .padding(
                    start = (dependencyIconSize / 2) + AppTheme.dimens.nsmall,
                    end = AppTheme.dimens.medium,
                    top = AppTheme.dimens.small,
                    bottom = AppTheme.dimens.small
                ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.xsmall),
        ) {
            TextBody1(
                text = dependency.dependencyName,
                bold = true,
            )
            HorizontalDivider()
            TextBody1(
                text = dependency.author
            )
            TextBody2(
                text = dependency.url,
                maxLines = 1
            )
        }
        Box(modifier = Modifier
            .padding(top = AppTheme.dimens.medium, bottom = AppTheme.dimens.medium)
            .size(dependencyIconSize)
            .clip(CircleShape)
            .align(Alignment.TopStart)
            .background(AppTheme.colors.surfaceContainer5)
        ) {
            AsyncImage(
                model = dependency.imageUrl,
                contentDescription = null,
            )
        }
    }
}


@Preview
@Composable
private fun PreviewList(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        AboutListScreen(
            paddingValues = PaddingValues(0.dp),
            email = "thementalgoose@gmail.com",
            deviceId = "uuid",
            installationId = "installation-uuid",
            actionUpClicked = { },
            icon = Res.drawable.unknown_avatar,
        )
    }
}


@Preview
@Composable
private fun PreviewPane(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    AppThemePreview(previewConfig) {
        AboutPaneScreen(
            paddingValues = PaddingValues(0.dp),
            email = "thementalgoose@gmail.com",
            deviceId = "uuid",
            installationId = "installation-uuid",
            icon = Res.drawable.unknown_avatar,
            actionUpClicked = { }
        )
    }
}