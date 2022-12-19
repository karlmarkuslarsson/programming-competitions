package adventofcode2022.problem14

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    var maxY = 0
    val sandPos = Position(500, 0)
    input14.split("\n").forEach { row ->
        var lastPos: Position? = null
        row.split(" -> ").forEach { posString ->
            val curPos = Position(posString)
            if (lastPos != null) {
                for (x in Math.min(lastPos!!.x, curPos.x)..Math.max(lastPos!!.x, curPos.x)) {
                    for (y in Math.min(lastPos!!.y, curPos.y)..Math.max(lastPos!!.y, curPos.y)) {
                        matrix.add(x, y, '#')
                        if (y > maxY) {
                            maxY = y
                        }
                    }
                }

            }
            lastPos = curPos
        }
    }
    while (fallDown(sandPos, matrix, maxY)) {

    }
    var sum = 0
    matrix.forEach {
        it.value.forEach {
            if (it.value == 'o') {
                sum++
            }
        }
    }
    println(sum);

}

fun fallDown(sandPos: Position, matrix: MutableMap<Int, MutableMap<Int, Char>>, maxY: Int): Boolean {
    var x = sandPos.x
    var y = sandPos.y
    while (true) {
        if (y >= maxY) {
            return false
        }
        if (matrix.get(x, y + 1) == null) {
            y++
        } else if (matrix.get(x - 1, y + 1) == null) {
            y++
            x--
        } else if (matrix.get(x + 1, y + 1) == null) {
            y++
            x++
        } else {
            matrix.add(x, y, 'o')
            return true
        }

    }
}

fun fallDownB(sandPos: Position, matrix: MutableMap<Int, MutableMap<Int, Char>>, maxY: Int): Boolean {
    var x = sandPos.x
    var y = sandPos.y
    while (true) {
        if (y == maxY - 1) {
            matrix.add(x, y, 'o')
            return true
        }
        if (matrix.get(x, y + 1) == null) {
            y++
        } else if (matrix.get(x - 1, y + 1) == null) {
            y++
            x--
        } else if (matrix.get(x + 1, y + 1) == null) {
            y++
            x++
        } else {
            matrix.add(x, y, 'o')
            if (x == sandPos.x && y == sandPos.y) {
                return false
            }
            return true
        }

    }
}

fun <T> MutableMap<Int, MutableMap<Int, T>>.add(x: Int, y: Int, char: T) {
    val row = get(x) ?: mutableMapOf()
    row[y] = char
    put(x, row)
}

fun <T> MutableMap<Int, MutableMap<Int, T>>.get(x: Int, y: Int): T? {
    return get(x)?.get(y)
}

data class Position(val x: Int, val y: Int) {
    constructor(posString: String) : this(
        posString.split(",")[0].toInt(),
        posString.split(",")[1].toInt(),
    )
}

fun solveB() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    var maxY = 0
    val sandPos = Position(500, 0)
    input14.split("\n").forEach { row ->
        var lastPos: Position? = null
        row.split(" -> ").forEach { posString ->
            val curPos = Position(posString)
            if (lastPos != null) {
                for (x in Math.min(lastPos!!.x, curPos.x)..Math.max(lastPos!!.x, curPos.x)) {
                    for (y in Math.min(lastPos!!.y, curPos.y)..Math.max(lastPos!!.y, curPos.y)) {
                        matrix.add(x, y, '#')
                        if (y > maxY) {
                            maxY = y
                        }
                    }
                }

            }
            lastPos = curPos
        }
    }
    var floorY = 2 + maxY
    while (fallDownB(sandPos, matrix, floorY)) {

    }
    var sum = 0
    matrix.forEach {
        it.value.forEach {

            if (it.value == 'o') {
                sum++
            }
        }
    }
    println(sum)
}
