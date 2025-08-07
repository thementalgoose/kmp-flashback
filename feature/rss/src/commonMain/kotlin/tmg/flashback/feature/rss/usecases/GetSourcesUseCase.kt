package tmg.flashback.feature.rss.usecases

import tmg.flashback.feature.rss.models.SupportedSource

interface GetSourcesUseCase {
    operator fun invoke(): List<SupportedSource>
}