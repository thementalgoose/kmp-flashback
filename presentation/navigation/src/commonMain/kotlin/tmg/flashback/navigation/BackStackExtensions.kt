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

inline fun <reified T: Screen> NavBackStack<NavKey>.replaceList(with: T) {
    this.clear()
    this.add(with)
}

fun NavKey.isList(): Boolean = (this as? Screen)?.isList() ?: true
