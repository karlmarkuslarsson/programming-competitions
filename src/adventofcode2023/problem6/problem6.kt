package adventofcode2023.problem6

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val times = input6.split("\n")[0]
        .split(": ")[1]
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.trim().toInt() }

    val distances = input6.split("\n")[1]
        .split(": ")[1]
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.trim().toInt() }

    val beats = mutableListOf<Int>()

    repeat(times.size) {
        val time = times[it]
        val distance = distances[it]
        var count = 0

        (1..time).forEach {
            val remainingTime = time - it
            val totalDistance = it * remainingTime

            if (totalDistance > distance) {
                count++
            }
        }

        beats.add(count)
    }

    var sum = 1
    beats.forEach { beat ->
        sum *= beat
    }
    println(sum)

}

fun solveB() {

    val input = input6
    val time = input.split("\n")[0]
        .split(": ")[1]
        .split(" ")
        .filter { it.isNotBlank() }
        .joinToString("")
        .toLong()

    val distance = input.split("\n")[1]
        .split(": ")[1]
        .split(" ")
        .filter { it.isNotBlank() }
        .joinToString("")
        .toLong()


    var count = 0
    (1..time).forEach {
        val remainingTime = time - it
        val totalDistance = it * remainingTime

        if (totalDistance > distance) {
            count++
        }
    }

    println(count)
}
