package adventofcode2022.problem12

fun main() {
    solveA()
    solveB()
}

fun <T> MutableMap<Int, MutableMap<Int, T>>.add(x: Int, y: Int, char: T) {
    val row = get(x) ?: mutableMapOf()
    row[y] = char
    put(x, row)
}

fun <T> MutableMap<Int, MutableMap<Int, T>>.get(x: Int, y: Int): T? {
    return get(x)?.get(y)
}

fun solveA() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    val matrixMinSteps = mutableMapOf<Int, MutableMap<Int, Int?>>()
    var x = 0
    var y = 0
    var startPosition = Position(x, y)
    input12.split("\n").forEach { row ->
        row.forEach {
            matrix.add(x, y, it)
            matrixMinSteps.add(x, y, null)
            if (it == 'S') {
                startPosition = Position(x, y)
            }
            x++
        }
        y++
        x = 0
    }
    val minSteps = findMinSteps(startPosition, matrix, matrixMinSteps, 0, 'a')
    println(minSteps)

}

fun findMinSteps(
    currentPosition: Position,
    matrix: MutableMap<Int, MutableMap<Int, Char>>,
    matrixMinSteps: MutableMap<Int, MutableMap<Int, Int?>>,
    numberOfSteps: Int,
    lastChar: Char
): Int {
    val last = if (lastChar == 'S') 'a' else lastChar
    val currentChar = matrix.get(currentPosition.x, currentPosition.y)
    var current = (if (currentChar == 'E') 'z' else currentChar) ?: return Int.MAX_VALUE
    current = (if (current == 'S') 'a' else current)
    if (current.code > last.code + 1) {
        return Int.MAX_VALUE
    }
    if (currentChar == 'E') {
        return numberOfSteps
    }
    val bestSteps = matrixMinSteps.get(currentPosition.x, currentPosition.y)
    if (bestSteps != null && bestSteps <= numberOfSteps) {
        return Int.MAX_VALUE
    }
    matrixMinSteps.add(currentPosition.x, currentPosition.y, numberOfSteps)
    val right =
        findMinSteps(
            Position(currentPosition.x + 1, currentPosition.y),
            matrix,
            matrixMinSteps,
            numberOfSteps + 1,
            current
        )
    val bottom =
        findMinSteps(
            Position(currentPosition.x, currentPosition.y + 1),
            matrix,
            matrixMinSteps,
            numberOfSteps + 1,
            current
        )
    val left =
        findMinSteps(
            Position(currentPosition.x - 1, currentPosition.y),
            matrix,
            matrixMinSteps,
            numberOfSteps + 1,
            current
        )
    val top =
        findMinSteps(
            Position(currentPosition.x, currentPosition.y - 1),
            matrix,
            matrixMinSteps,
            numberOfSteps + 1,
            current
        )
    return Math.min(left, Math.min(top, Math.min(right, bottom)))
}

data class Position(
    val x: Int,
    val y: Int,
)

fun solveB() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    val matrixMinSteps = mutableMapOf<Int, MutableMap<Int, Int?>>()
    var x = 0
    var y = 0
    val startPositions = mutableListOf<Position>()
    input12.split("\n").forEach { row ->
        row.forEach {
            matrix.add(x, y, it)
            matrixMinSteps.add(x, y, null)
            if (it == 'a') {
                startPositions.add(Position(x, y))
            }
            x++
        }
        y++
        x = 0
    }
    var bestSteps = Int.MAX_VALUE
    startPositions.forEach {
        val minSteps = findMinSteps(it, matrix, matrixMinSteps, 0, 'a')
        if (minSteps < bestSteps) {
            bestSteps = minSteps
        }

    }
    println(bestSteps)
}
