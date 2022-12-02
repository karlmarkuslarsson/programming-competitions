package adventofcode2022.problem1

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val elfCalories = mutableListOf<Long>()
    var currentSum = 0L
    input1a.split("\n").forEach {
        if(it == "") {
            elfCalories.add(currentSum)
            currentSum = 0
        } else {
            currentSum += it.toLong()
        }
    }
    elfCalories.sortByDescending { it }
    println(elfCalories.first())
}

fun solveB() {
    val elfCalories = mutableListOf<Long>()
    var currentSum = 0L
    input1a.split("\n").forEach {
        if(it == "") {
            elfCalories.add(currentSum)
            currentSum = 0
        } else {
            currentSum += it.toLong()
        }
    }
    elfCalories.sortByDescending { it}
    println(elfCalories.take(3).sumOf { it })
}
