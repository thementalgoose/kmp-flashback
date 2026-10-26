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

inline fun <reified T: Screen> NavBackStack<NavKey>.appendMaster(vararg with: T) {
    this.removeDetail()
    for (x in with) {
        if (x is NavCalendar) {
            this.clear()
            this.add(NavCalendar)
            return
        }
        if (x.requiresPaddock()) {
            if (this.contains(NavPaddock)) {
                this.remove(NavPaddock)
            }
            this.add(NavPaddock)
        }
        if (x.requiresSettings()) {
            if (this.contains(NavSettings)) {
                this.remove(NavSettings)
            }
            this.add(NavSettings)
        }
        this.removeAll { it::class == x::class }
        this.add(x)
    }
}

inline fun <reified T: Screen> NavBackStack<NavKey>.addMaster(vararg with: T) {
    this.removeDetail()
    for (x in with) {
        if (x.requiresPaddock() && !this.contains(NavPaddock)) {
            this.add(NavPaddock)
        }
        if (x.requiresSettings() && !this.contains(NavSettings)) {
            this.add(NavSettings)
        }
        if (this.lastOrNull() != x) {
            this.add(x)
        }
    }
}

inline fun <reified T: Screen> NavBackStack<NavKey>.replaceMaster(vararg with: T) {
    this.clear()
    for (x in with) {
        if (x.requiresPaddock() && !this.contains(NavPaddock)) {
            this.add(NavPaddock)
        }
        if (x.requiresSettings() && !this.contains(NavSettings)) {
            this.add(NavSettings)
        }
        this.add(x)
    }
}

fun NavBackStack<NavKey>.pop(): Boolean {
    if (this.isEmpty()) return false
    val last = this.lastOrNull() ?: return false
    return if (!last.isList()) {
        this.removeDetail()
        true
    } else if (this.size > 1) {
        this.removeAt(this.lastIndex)
        true
    } else {
        false
    }
}

fun NavBackStack<NavKey>.popMaster(): Boolean {
    this.removeDetail()
    return if (this.size > 1) {
        this.removeAt(this.lastIndex)
        true
    } else {
        false
    }
}

fun NavKey.isList(): Boolean = (this as? Screen)?.isList() ?: true

fun NavKey.requiresPaddock(): Boolean = (this as? Screen)?.requiresPaddock() ?: false

fun NavKey.requiresSettings(): Boolean = (this as? Screen)?.requiresSettings() ?: false
