package adventofcode2023.problem2

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var sum = 0
    input2.split("\n").forEach {
        val split = it.split(": ")
        val game = split[0].split(" ")[1]
        val rounds = split[1].split("; ")
        var valid = true
        rounds.forEach {
            it.split(", ").forEach {
                val ballsSplit = it.split(" ")
                val ballValue = ballsSplit[0].toInt()
                when (ballsSplit[1]) {
                    "blue" -> {
                        if (ballValue > 14) {
                            valid = false
                        }
                    }

                    "green" -> {
                        if (ballValue > 13) {
                            valid = false
                        }
                    }

                    "red" -> {
                        if (ballValue > 12) {
                            valid = false
                        }
                    }
                }
            }

        }
        if (valid) {
            sum += game.toInt()
        }
    }
    println(sum)

}

fun solveB() {
    var sum = 0
    input2.split("\n").forEach {
        val split = it.split(": ")
        val rounds = split[1].split("; ")
        var greenHighest = 0
        var blueHighest = 0
        var redHighest = 0
        rounds.forEach {
            it.split(", ").forEach {
                val ballsSplit = it.split(" ")
                val ballValue = ballsSplit[0].toInt()
                when (ballsSplit[1]) {
                    "blue" -> {
                        if (ballValue > blueHighest) {
                            blueHighest = ballValue
                        }
                    }

                    "green" -> {
                        if (ballValue > greenHighest) {
                            greenHighest = ballValue
                        }
                    }

                    "red" -> {
                        if (ballValue > redHighest) {
                            redHighest = ballValue
                        }
                    }
                }
            }

        }
        sum += greenHighest * blueHighest * redHighest

    }
    println(sum)
}
