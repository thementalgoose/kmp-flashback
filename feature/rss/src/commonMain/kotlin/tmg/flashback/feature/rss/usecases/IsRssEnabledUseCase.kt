package tmg.flashback.feature.rss.usecases

import tmg.flashback.feature.rss.repositories.RssRepository

interface IsRssEnabledUseCase {
    operator fun invoke(): Boolean
}

internal class IsRssEnabledUseCaseImpl(
    private val rssRepository: RssRepository
): IsRssEnabledUseCase {
    override operator fun invoke() = rssRepository.isEnabled
}