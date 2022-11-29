package adventofcode2022.problem1

import adventofcode2022.asLongList
import kotlin.math.floor

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    println(input1a.asLongList().map { floor(it.toDouble() / 3f) - 2 }.sum())
}

fun solveB() {

}
