package adventofcode2022.problem2

fun main() {
    solveA()
    solveB()
}

fun solveA() {

    var totalScore = 0
    input2a.split("\n").forEach {
        val game = it.split(" ")
        val opponent = game[0]
        val you = game[1]
        when (you) {
            "X" -> {
                totalScore += 1
                when (opponent) {
                    "A" -> {
                        totalScore += 3
                    }
                    "C" -> {
                        totalScore += 6
                    }
                }
            }
            "Y" -> {
                totalScore += 2
                when (opponent) {
                    "A" -> {
                        totalScore += 6
                    }
                    "B" -> {
                        totalScore += 3
                    }
                }

            }
            "Z" -> {
                totalScore += 3
                when (opponent) {
                    "B" -> {
                        totalScore += 6
                    }
                    "C" -> {
                        totalScore += 3
                    }
                }

            }
        }
    }
    println(totalScore)
}

fun solveB() {
    var totalScore = 0
    input2a.split("\n").forEach {
        val game = it.split(" ")
        val opponent = game[0]
        val you = game[1]
        when (you) {
            "X" -> {
                totalScore += 0
                when (opponent) {
                    "A" -> {
                        totalScore += 3
                    }
                    "B" -> {
                        totalScore += 1
                    }
                    "C" -> {
                        totalScore += 2
                    }
                }
            }
            "Y" -> {
                totalScore += 3
                when (opponent) {
                    "A" -> {
                        totalScore += 1
                    }
                    "B" -> {
                        totalScore += 2
                    }
                    "C" -> {
                        totalScore += 3
                    }
                }

            }
            "Z" -> {
                totalScore += 6
                when (opponent) {
                    "A" -> {
                        totalScore += 2
                    }
                    "B" -> {
                        totalScore += 3
                    }
                    "C" -> {
                        totalScore += 1
                    }
                }

            }
        }
    }
    println(totalScore)
}
