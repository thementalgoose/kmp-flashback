package tmg.flashback.feature.reactiongame.presentation

import app.cash.turbine.test
import dev.mokkery.MockMode.autoUnit
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import tmg.flashback.feature.reactiongame.manager.LightsOutDelayProvider
import tmg.flashback.infrastructure.datetime.TimeManager
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class ReactionViewModelTest {

    private companion object {
        private const val LIGHTS_OUT_DELAY = 2000L
        private const val TIME_BETWEEN_LIGHTS = 1000L
        private const val TEST_TIMEOUT = 2500L
    }

    private val mockLightsOutDelayProvider: LightsOutDelayProvider = mock(autoUnit)
    private val mockTimeManager: TimeManager = mock(autoUnit)
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var underTest: ReactionGameViewModel

    private fun initUnderTest() {
        every { mockLightsOutDelayProvider.getDelay() } returns LIGHTS_OUT_DELAY
        underTest = ReactionGameViewModel(
            lightsOutDelayProvider = mockLightsOutDelayProvider,
            timeManager = mockTimeManager,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `clicking start starts sequence and reacting to it works as expected`() = runTest(testDispatcher) {
        initUnderTest()
        setNow(0)

        underTest.uiState.test {
            assertEquals(ReactionUiState.Start, awaitItem())

            underTest.start()

            assertEquals(ReactionUiState.Game(lights = 0), awaitItem())
            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS)
            assertEquals(ReactionUiState.Game(lights = 1), awaitItem())
            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS)
            assertEquals(ReactionUiState.Game(lights = 2), awaitItem())
            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS)
            assertEquals(ReactionUiState.Game(lights = 3), awaitItem())
            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS)
            assertEquals(ReactionUiState.Game(lights = 4), awaitItem())
            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS)
            assertEquals(ReactionUiState.Game(lights = 5), awaitItem())

            setNow(10000)

            advanceTimeBy(LIGHTS_OUT_DELAY)
            assertEquals(ReactionUiState.Game(lights = 0, hasDisplayedSequence = true), awaitItem())

            advanceTimeBy(250L)
            setNow(10250)

            underTest.react()

            val expected = ReactionUiState.Results(
                timeMillis = 250,
                tier = ReactionResultTier.AVERAGE,
                percentage = 0.5f
            )
            assertEquals(expected, awaitItem())

            advanceTimeBy(TEST_TIMEOUT)

            expectNoEvents()
        }
    }

    @Test
    fun `reacting to sequence before lights out causes jump start`() = runTest(testDispatcher) {
        initUnderTest()
        setNow(0)

        underTest.uiState.test {
            assertEquals(ReactionUiState.Start, awaitItem())

            underTest.start()

            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS * 5)
            assertEquals(ReactionUiState.Game(0), awaitItem())
            assertEquals(ReactionUiState.Game(1), awaitItem())
            assertEquals(ReactionUiState.Game(2), awaitItem())
            assertEquals(ReactionUiState.Game(3), awaitItem())
            assertEquals(ReactionUiState.Game(4), awaitItem())
            assertEquals(ReactionUiState.Game(5), awaitItem())

            underTest.react()

            assertEquals(ReactionUiState.JumpStart, awaitItem())
        }
    }

    data class TestCase(
        val millis: Int,
        val expectedTier: ReactionResultTier
    )
    private val testCases = listOf(
        TestCase(10, ReactionResultTier.SUPERHUMAN),
        TestCase(149, ReactionResultTier.SUPERHUMAN),
        TestCase(150, ReactionResultTier.EXCEPTIONAL),
        TestCase(179, ReactionResultTier.EXCEPTIONAL),
        TestCase(181, ReactionResultTier.GOOD),
        TestCase(229, ReactionResultTier.GOOD),
        TestCase(240, ReactionResultTier.AVERAGE),
        TestCase(279, ReactionResultTier.AVERAGE),
        TestCase(280, ReactionResultTier.NOT_GOOD),
        TestCase(400, ReactionResultTier.POOR),
        TestCase(401, ReactionResultTier.POOR),
        TestCase(1000, ReactionResultTier.POOR),
    )

    @Test
    fun `reacting to sequence after millis results in tier`() = runTest(testDispatcher) {
        testCases.forEach { (millis, expectedTier) ->
            initUnderTest()

            underTest.uiState.test {
                assertEquals(ReactionUiState.Start, awaitItem())

                underTest.start()

                testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS * 5)
                assertEquals(ReactionUiState.Game(0), awaitItem())
                assertEquals(ReactionUiState.Game(1), awaitItem())
                assertEquals(ReactionUiState.Game(2), awaitItem())
                assertEquals(ReactionUiState.Game(3), awaitItem())
                assertEquals(ReactionUiState.Game(4), awaitItem())
                assertEquals(ReactionUiState.Game(5), awaitItem())

                setNow(100)
                advanceTimeBy(LIGHTS_OUT_DELAY)
                assertEquals(ReactionUiState.Game(lights = 0, hasDisplayedSequence = true), awaitItem())

                setNow(100 + millis)
                advanceTimeBy(millis.toLong())

                underTest.react()

                val expected = ReactionUiState.Results(
                    timeMillis = millis.toLong(),
                    tier = expectedTier,
                    percentage = (millis / 500f).coerceIn(0f, 1f)
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `not reacting to sequence causes missed ui state`() = runTest(testDispatcher) {
        initUnderTest()

        underTest.uiState.test {
            assertEquals(ReactionUiState.Start, awaitItem())

            underTest.start()

            testDispatcher.scheduler.advanceTimeBy(TIME_BETWEEN_LIGHTS * 5)
            assertEquals(ReactionUiState.Game(0), awaitItem())
            assertEquals(ReactionUiState.Game(1), awaitItem())
            assertEquals(ReactionUiState.Game(2), awaitItem())
            assertEquals(ReactionUiState.Game(3), awaitItem())
            assertEquals(ReactionUiState.Game(4), awaitItem())
            assertEquals(ReactionUiState.Game(5), awaitItem())

            setNow(100)
            advanceTimeBy(LIGHTS_OUT_DELAY)
            assertEquals(ReactionUiState.Game(lights = 0, hasDisplayedSequence = true), awaitItem())

            advanceTimeBy(100L)

            advanceTimeBy(TEST_TIMEOUT)

            val expected = ReactionUiState.Missed
            assertEquals(expected, awaitItem())
        }
    }

    private fun setNow(millis: Int) {
        every { mockTimeManager.nowMillis } returns millis.toLong()
    }
}