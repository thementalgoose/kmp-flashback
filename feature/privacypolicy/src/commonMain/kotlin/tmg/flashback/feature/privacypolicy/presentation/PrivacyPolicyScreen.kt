package tmg.flashback.feature.privacypolicy.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.privacy_policy_data
import flashback.presentation.localisation.generated.resources.privacy_policy_title
import org.jetbrains.compose.resources.stringResource
import tmg.flashback.style.AppTheme
import tmg.flashback.style.text.TextBody2
import tmg.flashback.ui.components.header.Header
import tmg.flashback.ui.components.header.HeaderAction

@Composable
fun PrivacyPolicyScreen(
    paddingValues: PaddingValues,
    actionUpClicked: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
    ) {
        Header(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            actionUpClicked = actionUpClicked,
            action = HeaderAction.MENU,
            text = stringResource(string.privacy_policy_title)
        )
        TextBody2(
            text = stringResource(string.privacy_policy_data),
            modifier = Modifier.padding(
                start = AppTheme.dimens.medium,
                end = AppTheme.dimens.medium,
                top = AppTheme.dimens.small,
                bottom = AppTheme.dimens.small + paddingValues.calculateBottomPadding()
            )
        )
    }
}