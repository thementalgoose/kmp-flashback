package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import kotlin.reflect.KClass
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class ScreenStateConfigurationTest {

    @OptIn(ExperimentalSerializationApi::class)
    @ParameterizedTest
    @MethodSource("sealedSubclasses")
    fun `screen is registered in saveStateConfiguration`(kclass: KClass<out Screen>) {
        val serializersModule = saveStateConfiguration.serializersModule
        val className = kclass.simpleName ?: return
        val expectedSerialName = "tmg.flashback.navigation.$className"
        
        val polymorphicSerializer = serializersModule.getPolymorphic(Screen::class, expectedSerialName)
        assertTrue(
            polymorphicSerializer != null,
            "$className is not registered in saveStateConfiguration in Screen scope ($expectedSerialName)"
        )

        val navKeyPolymorphicSerializer = serializersModule.getPolymorphic(NavKey::class, expectedSerialName)
        assertTrue(
            navKeyPolymorphicSerializer != null,
            "$className is not registered in saveStateConfiguration in NavKey scope ($expectedSerialName)"
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun testAllSealedScreensInFileRegisteredInSaveStateConfiguration() {
        val serializersModule = saveStateConfiguration.serializersModule
        val screenFile = findScreenFile()
        if (screenFile != null && screenFile.exists()) {
            val fileContent = screenFile.readText()
            val regex = Regex("""(?:data\s+object|data\s+class|class|object)\s+([A-Za-z0-9_]+)\s*:\s*(?:Screen|NavKey)""")
            val matches = regex.findAll(fileContent).map { it.groupValues[1] }.toList()
            
            val missingClasses = mutableSetOf<String>()
            for (className in matches) {
                val expectedSerialName = "tmg.flashback.navigation.$className"
                val polymorphicSerializer = serializersModule.getPolymorphic(Screen::class, expectedSerialName)
                if (polymorphicSerializer == null) {
                    missingClasses.add("$className in Screen scope ($expectedSerialName)")
                }
                val navKeyPolymorphicSerializer = serializersModule.getPolymorphic(NavKey::class, expectedSerialName)
                if (navKeyPolymorphicSerializer == null) {
                    missingClasses.add("$className in NavKey scope ($expectedSerialName)")
                }
            }

            assertTrue(
                missingClasses.isEmpty(),
                "The following screens are declared in Screen.kt but are not registered in saveStateConfiguration:\n" +
                        missingClasses.joinToString("\n") { " - $it" }
            )
        }
    }

    companion object {
        @JvmStatic
        fun sealedSubclasses(): List<KClass<out Screen>> {
            return Screen::class.sealedSubclasses
        }

        private fun findScreenFile(): File? {
            var currentDir = File(".").canonicalFile
            while (currentDir.exists()) {
                val candidate = File(currentDir, "presentation/navigation/src/commonMain/kotlin/tmg/flashback/navigation/Screen.kt")
                if (candidate.exists()) {
                    return candidate
                }
                val parent = currentDir.parentFile ?: break
                currentDir = parent
            }
            return null
        }
    }
}
