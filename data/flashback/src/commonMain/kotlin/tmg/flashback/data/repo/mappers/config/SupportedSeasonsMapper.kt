package tmg.flashback.data.repo.mappers.config

import tmg.flashback.data.repo.model.config.SupportedSeasonsJson

internal fun SupportedSeasonsJson.convert(): Set<Int> {
    return this.seasons
        ?.mapNotNull { it.s }
        ?.toSet() ?: emptySet()
}