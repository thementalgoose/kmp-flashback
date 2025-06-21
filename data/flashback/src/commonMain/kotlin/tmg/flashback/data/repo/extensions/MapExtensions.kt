package tmg.flashback.data.repo.extensions

fun <T,R> Map<T, R>?.valueList(): List<R> {
    return (this?.values ?: emptyList()).toMutableList()
}