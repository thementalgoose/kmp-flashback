package tmg.flashback.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertTrue

class ScreenStateConfigurationTest {

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun testAllSealedScreensRegisteredInSaveStateConfiguration() {
        val serializersModule = saveStateConfiguration.serializersModule
        
        // 1. Reflection check on sealed subclasses of Screen
        val sealedSubclasses: List<KClass<out Screen>> = Screen::class.sealedSubclasses
        assertTrue(sealedSubclasses.isNotEmpty(), "Screen sealed interface should have subclasses")

        val missingClasses = mutableSetOf<String>()
        for (kclass in sealedSubclasses) {
            val className = kclass.simpleName ?: continue
            val expectedSerialName = "tmg.flashback.navigation.$className"
            val polymorphicSerializer = serializersModule.getPolymorphic(NavKey::class, expectedSerialName)
            if (polymorphicSerializer == null) {
                missingClasses.add("$className ($expectedSerialName)")
            }
        }

        // 2. Source file scan check on Screen.kt to ensure no NavKey implementations were missed
        val screenFile = findScreenFile()
        if (screenFile != null && screenFile.exists()) {
            val fileContent = screenFile.readText()
            val regex = Regex("""(?:data\s+object|data\s+class|class|object)\s+([A-Za-z0-9_]+)\s*:\s*(?:Screen|NavKey)""")
            val matches = regex.findAll(fileContent).map { it.groupValues[1] }.toList()
            
            for (className in matches) {
                val expectedSerialName = "tmg.flashback.navigation.$className"
                val polymorphicSerializer = serializersModule.getPolymorphic(NavKey::class, expectedSerialName)
                if (polymorphicSerializer == null) {
                    missingClasses.add("$className ($expectedSerialName)")
                }
            }
        }

        assertTrue(
            missingClasses.isEmpty(),
            "The following NavKey screens are declared in Screen.kt but are not registered in saveStateConfiguration:\n" +
                    missingClasses.joinToString("\n") { " - $it" }
        )
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
