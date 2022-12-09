package adventofcode2022.problem9

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val set = mutableSetOf<Position>()
    var tailX = 0
    var tailY = 0
    var headX = 0
    var headY = 0
    set.add(Position(tailX, tailY))
    input9.split("\n").forEach {
        val rowSplit = it.split(" ")
        val direction = rowSplit[0]
        val steps = rowSplit[1].toInt()
        for (i in 0 until steps) {
            when (direction) {
                "L" -> {
                    headX -= 1
                }

                "U" -> {
                    headY += 1
                }

                "R" -> {
                    headX += 1
                }

                "D" -> {
                    headY -= 1
                }
            }
            val xDifference = headX - tailX
            val yDifference = headY - tailY
            if (yDifference == 0) {
                if (xDifference > 1) {
                    tailX++
                } else if (xDifference < -1) {
                    tailX--
                }
            } else if (yDifference == 1) {
                if (xDifference > 1) {
                    tailX++
                    tailY++
                } else if (xDifference < -1) {
                    tailX--
                    tailY++
                }
            } else if (yDifference == -1) {
                if (xDifference > 1) {
                    tailX++
                    tailY--
                } else if (xDifference < -1) {
                    tailX--
                    tailY--
                }
            } else if (yDifference > 1) {
                if (xDifference > 0) {
                    tailX++
                    tailY++
                } else if (xDifference < 0) {
                    tailX--
                    tailY++
                } else {
                    tailY++
                }
            } else if (yDifference < -1) {
                if (xDifference > 0) {
                    tailX++
                    tailY--
                } else if (xDifference < 0) {
                    tailX--
                    tailY--
                } else {
                    tailY--
                }
            }
            set.add(Position(tailX, tailY))
        }
    }
    println(set.size)
}

fun solveB() {
    val set = mutableSetOf<Position>()
    var tailX = 0
    var tailY = 0
    var tails = (mutableListOf<Position>())
    for (i in 0 until 9) {
        tails.add(Position(0, 0))
    }
    var headX = 0
    var headY = 0
    set.add(Position(tailX, tailY))
    input9.split("\n").forEach {
        val rowSplit = it.split(" ")
        val direction = rowSplit[0]
        val steps = rowSplit[1].toInt()
        for (i in 0 until steps) {
            when (direction) {
                "L" -> {
                    headX -= 1
                }

                "U" -> {
                    headY += 1
                }

                "R" -> {
                    headX += 1
                }

                "D" -> {
                    headY -= 1
                }
            }
            var lastTail = Position(headX, headY)
            tails.forEach {
                it.takeStep(lastTail)
                lastTail = it
            }
            set.add(lastTail.copy())
        }
    }
    println(set.size)
}

data class Position(
    var x: Int,
    var y: Int
) {

    override fun equals(other: Any?): Boolean {
        if (other is Position && other.x == x && other.y == y) {
            return true
        }
        return false
    }

    fun takeStep(lastTail: Position) {
        val xDifference = lastTail.x - x
        val yDifference = lastTail.y - y
        if (yDifference == 0) {
            if (xDifference > 1) {
                x++
            } else if (xDifference < -1) {
                x--
            }
        } else if (yDifference == 1) {
            if (xDifference > 1) {
                x++
                y++
            } else if (xDifference < -1) {
                x--
                y++
            }
        } else if (yDifference == -1) {
            if (xDifference > 1) {
                x++
                y--
            } else if (xDifference < -1) {
                x--
                y--
            }
        } else if (yDifference > 1) {
            if (xDifference > 0) {
                x++
                y++
            } else if (xDifference < 0) {
                x--
                y++
            } else {
                y++
            }
        } else if (yDifference < -1) {
            if (xDifference > 0) {
                x++
                y--
            } else if (xDifference < 0) {
                x--
                y--
            } else {
                y--
            }
        }
    }

}