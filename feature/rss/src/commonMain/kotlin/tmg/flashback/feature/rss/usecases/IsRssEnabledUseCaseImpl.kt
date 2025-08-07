package tmg.flashback.feature.rss.usecases

import tmg.flashback.feature.rss.repositories.RssRepository

internal class IsRssEnabledUseCaseImpl(
    private val rssRepository: RssRepository
): IsRssEnabledUseCase {
    override operator fun invoke() = rssRepository.isEnabled
}