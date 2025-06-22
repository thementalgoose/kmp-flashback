package tmg.flashback.eastereggs.repository

class FakeEasterEggsRepository(
    override var isSnowEnabled: Boolean = false,
    override var isSummerEnabled: Boolean = false,
    override var isUkraineEnabled: Boolean = false
) : EasterEggsRepository