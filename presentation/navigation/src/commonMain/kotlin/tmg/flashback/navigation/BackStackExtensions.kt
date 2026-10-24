package tmg.flashback.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

inline fun <reified T: Screen> NavBackStack<NavKey>.replaceDetail(with: T) {
    this.removeDetail()
    this.add(with)
}

fun NavBackStack<NavKey>.removeDetail() {
    this.removeAll { key -> !key.isList() }
}

inline fun <reified T: Screen> NavBackStack<NavKey>.replaceList(vararg with: T) {
    this.clear()
    for (x in with) {
        this.add(x)
    }
}

fun NavKey.isList(): Boolean = (this as? Screen)?.isList() ?: true
