package tmg.flashback.feature.privacypolicy.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.privacy_policy_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.koin.compose.viewmodel.koinViewModel
import tmg.flashback.style.AppTheme
import tmg.flashback.style.ApplicationThemePreview
import tmg.flashback.style.preview.PreviewConfig
import tmg.flashback.style.preview.PreviewConfigProvider
import tmg.flashback.style.text.TextBody1
import tmg.flashback.style.text.TextBody2
import tmg.flashback.style.text.TextHeadline2
import tmg.flashback.style.text.TextTitle
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun PrivacyPolicyScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean = true,
    viewModel: PrivacyPolicyViewModel = koinViewModel()
) {
    PrivacyPolicyScreen(
        paddingValues = paddingValues,
        actionUpClicked = actionUpClicked,
        showBack = showBack,
        openWebpage = viewModel::openWebpage
    )
}

@Composable
private fun PrivacyPolicyScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
    showBack: Boolean = true,
    openWebpage: (String) -> Unit
) {
    val elements = remember { getPolicy().elements }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = paddingValues
    ) {
        item("header") {
            Header(
                actionUpClicked = actionUpClicked,
                action = HeaderAction.BACK.takeIf { showBack },
                text = stringResource(string.privacy_policy_title)
            )
        }
        items(elements) {
            when (it) {
                is PolicyElement.Header1 -> {
                    TextHeadline2(
                        modifier = Modifier.padding(
                            top = AppTheme.dimens.nsmall,
                            end = AppTheme.dimens.medium,
                            start = AppTheme.dimens.medium,
                            bottom = AppTheme.dimens.small
                        ),
                        text = it.text
                    )
                }
                is PolicyElement.Header2 -> {
                    TextTitle(
                        modifier = Modifier.padding(
                            vertical = AppTheme.dimens.xsmall,
                            horizontal = AppTheme.dimens.medium
                        ),
                        text = it.text
                    )
                }
                is PolicyElement.ListItem -> {
                    Row(
                        modifier = Modifier
                            .clickable(
                                enabled = it.link != null
                            ) {
                                openWebpage(it.link!!)
                            }
                            .padding(
                                vertical = AppTheme.dimens.xsmall,
                                horizontal = AppTheme.dimens.medium
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.AutoMirrored.Default.ArrowRight,
                            contentDescription = null,
                            tint = AppTheme.colors.onSurfaceVariant
                        )
                        TextBody1(
                            text = it.text
                        )
                        if (it.link != null) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Link,
                                contentDescription = null,
                                tint = AppTheme.colors.onSurfaceVariant
                            )
                        }
                    }
                }
                is PolicyElement.Text -> {
                    TextBody1(
                        modifier = Modifier.padding(
                            vertical = AppTheme.dimens.xsmall,
                            horizontal = AppTheme.dimens.medium
                        ),
                        text = it.text
                    )
                }
            }
        }

        item("footer") {
            TextBody2(
                modifier = Modifier.padding(
                    top = AppTheme.dimens.medium,
                    start = AppTheme.dimens.medium,
                    end = AppTheme.dimens.medium,
                    bottom = AppTheme.dimens.xsmall
                ),
                text = "This privacy policy page was created at https://privacypolicytemplate.net and modified/generated by App Privacy Policy Generator (app-privacy-policy-generator.firebaseapp.com)"
            )
        }
    }
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(PreviewConfigProvider::class) previewConfig: PreviewConfig
) {
    ApplicationThemePreview(previewConfig) {
        PrivacyPolicyScreen(
            paddingValues = PaddingValues(0.dp),
            actionUpClicked = { },
            showBack = true,
            openWebpage = { }
        )
    }
}