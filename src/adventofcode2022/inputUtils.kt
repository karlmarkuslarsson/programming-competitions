package adventofcode2022

fun String.asLongList(): List<Long> {
    return split("\n").map { it.toLong() }
}

fun String.asStringList(): List<String> {
    return split("\n")
}

fun String.asDoubleList(): List<Double> {
    return split("\n").map { it.toDouble() }
}