package tmg.flashback.feature.drivers.presentation.comparison

import dev.mokkery.mock
import kotlin.test.Test
import kotlin.test.assertEquals


internal class DriverComparisonScreenStateTest {

    private val fakeComparison = Comparison(
        left = mock(),
        leftConstructors = emptyList(),
        right = mock(),
        rightConstructors = emptyList()
    )

    data class TestCase(
        val a: Double,
        val b: Double,
        val highIsBest: Boolean,
        val expectedLeft: Float,
        val expectedRight: Float
    )
    private val testCases = listOf(
        TestCase(4.0,0.0,true,1f,0f),
        TestCase(0.0,4.0,true,0f,1f),
        TestCase(4.0,4.0,true,1f,1f),
        TestCase(2.0,4.0,true,0.5f,1f),
        TestCase(4.0,1.0,true,1f,0.25f),
        TestCase(4.0,0.0,false,0f,1f),
        TestCase(0.0,4.0,false,1f,0f),
        TestCase(4.0,4.0,false,1f,1f),
        TestCase(2.0,4.0,false,1f,0.5f),
        TestCase(4.0,1.0,false,0.25f,1f),
    )

    @Test
    fun `percentage is calculated`() {
        testCases.forEach { (a, b, highIsBest, expectedLeft, expectedRight) ->
            assertEquals(expectedLeft to expectedRight, fakeComparison.getPercentages(a.toFloat(), b.toFloat(), highIsBest))
        }
    }
}