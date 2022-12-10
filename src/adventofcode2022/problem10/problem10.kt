package adventofcode2022.problem10

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var clockCycle = 0
    var registerValue = 1
    var totalSum = 0
    input10.split("\n").forEach {
        val split = it.split(" ")
        when (split[0]) {
            "addx" -> {
                for (i in 0 until 2) {
                    clockCycle++
                    if ((clockCycle - 20) % 40 == 0 || clockCycle == 20) {
                        totalSum += clockCycle * registerValue
                    }
                }
                registerValue += split[1].toInt()
            }

            "noop" -> {
                for (i in 0 until 1) {
                    clockCycle++
                    if ((clockCycle - 20) % 40 == 0 || clockCycle == 20) {
                        totalSum += clockCycle * registerValue
                    }
                }
            }
        }
    }
    println(totalSum)

}

fun solveB() {
    var clockCycle = 0
    var startX = 1

    input10.split("\n").forEach {
        val split = it.split(" ")
        when (split[0]) {
            "addx" -> {
                for (i in 0 until 2) {
                    if ((clockCycle %40) >= startX-1 && (clockCycle %40) <= startX + 1) {
                        print("#")
                    } else {
                        print(".")
                    }

                    clockCycle++
                    if (clockCycle % 40 == 0) {
                        println()
                    }
                }
                startX += split[1].toInt()
            }

            "noop" -> {
                for (i in 0 until 1) {
                    if ((clockCycle % 40) >= startX-1 && (clockCycle % 40) <= startX + 1) {
                        print("#")
                    } else {
                        print(".")
                    }
                    clockCycle++
                    if (clockCycle % 40 == 0) {
                        println()
                    }
                }
            }
        }
    }
}
