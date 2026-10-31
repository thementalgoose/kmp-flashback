package tmg.flashback.feature.notifications.model

import flashback.presentation.localisation.generated.resources.Res.string
import flashback.presentation.localisation.generated.resources.notification_channel_breaking_news
import org.jetbrains.compose.resources.StringResource

enum class NotificationNews(
    val channelId: String,
    val channelLabel: StringResource,
    val remoteSubscriptionTopic: String = channelId
) {
    BREAKING_NEWS("notify_breaking_news", string.notification_channel_breaking_news)
}