package adventofcode2022.problem17

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val matrix = mutableMapOf<Long, MutableMap<Long, Char>>()
    var stoppedRocks = 0L
    val limitStoppedRocks = 2022L
    var maxY = 0L
    var lastHit = 0L
    var lastRocksFallen = 0L
    var currentRock = Rock(
        positions = listOf(
            Position(3, maxY + 4),
            Position(4, maxY + 4),
            Position(5, maxY + 4),
            Position(6, maxY + 4)
        ), RockName.line
    )
    for (i in 1..7) {
        matrix.add(i.toLong(), 0, '-')
    }
    while (true) {
        input17.forEach {
            when (it) {
                '<' -> {
                    currentRock.left(matrix)
                }

                '>' -> {
                    currentRock.right(matrix)
                }
            }
            if (currentRock.down(matrix)) {
                stoppedRocks++
                maxY = maxOf(currentRock.maxY(), maxY)

                var interval = true
                var result = ""
                for (y in (maxY - 5)..(maxY)) {
                    for (x in 1L..7L) {
                        result += matrix.get(x, y) ?: '.'
                    }
                }
                if (stoppedRocks == limitStoppedRocks) {
                    println(maxY)

                    for (y in 1..maxY) {
                        for (x in 1..7) {
                            // print(matrix.get(x, y) ?: '.')
                        }
                        //println()
                    }
                    return
                }
                currentRock = currentRock.nextRock(maxY)
            }

        }
    }
}

fun Rock.nextRock(maxY: Long): Rock {
    val rockName = rockName
    val nextRockName = RockName.values()[(rockName.ordinal + 1) % RockName.values().size]
    return when (nextRockName) {
        RockName.line -> {
            Rock(
                positions = listOf(
                    Position(3, maxY + 4),
                    Position(4, maxY + 4),
                    Position(5, maxY + 4),
                    Position(6, maxY + 4)
                ), nextRockName
            )
        }

        RockName.plus -> Rock(
            positions = listOf(
                Position(4, maxY + 6L),
                Position(3, maxY + 5L),
                Position(4, maxY + 5L),
                Position(5, maxY + 5L),
                Position(4, maxY + 4L)
            ), nextRockName
        )

        RockName.backL -> Rock(
            positions = listOf(
                Position(5, maxY + 6),
                Position(5, maxY + 5),
                Position(3, maxY + 4),
                Position(4, maxY + 4),
                Position(5, maxY + 4)
            ), nextRockName
        )

        RockName.i -> Rock(
            positions = listOf(
                Position(3, maxY + 7),
                Position(3, maxY + 6),
                Position(3, maxY + 5),
                Position(3, maxY + 4),
            ), nextRockName
        )

        RockName.square -> Rock(
            positions = listOf(
                Position(3, maxY + 5),
                Position(4, maxY + 5),
                Position(3, maxY + 4),
                Position(4, maxY + 4),
            ), nextRockName
        )
    }
}

data class Rock(val positions: List<Position>, val rockName: RockName) {
    fun maxY(): Long {
        return positions.maxOf { it.y }
    }

    fun down(matrix: MutableMap<Long, MutableMap<Long, Char>>): Boolean {
        val firstNonNull = positions.firstOrNull {
            matrix.get(it.x, it.y - 1) != null
        }
        if (firstNonNull == null) {
            positions.forEach {
                it.y--
            }
            return false
        }
        positions.forEach {
            matrix.add(it.x, it.y, '#')
        }
        return true
    }

    fun left(matrix: MutableMap<Long, MutableMap<Long, Char>>) {
        val firstNonNull = positions.firstOrNull {
            matrix.get(it.x - 1, it.y) != null || it.x - 1L == 0L
        }
        if (firstNonNull == null) {
            positions.forEach {
                it.x--
            }
        }
    }

    fun right(matrix: MutableMap<Long, MutableMap<Long, Char>>) {
        val firstNonNull = positions.firstOrNull {
            matrix.get(it.x + 1L, it.y) != null || it.x + 1 == 8L
        }
        if (firstNonNull == null) {
            positions.forEach {
                it.x++
            }
        }
    }
}

enum class RockName {
    line,
    plus,
    backL,
    i,
    square
}

data class Position(
    var x: Long,
    var y: Long
)

fun solveB() {
    val matrix = mutableMapOf<Long, MutableMap<Long, Char>>()
    var stoppedRocks = 0L
    var limitStoppedRocks = 1000000L //  10650+1600
    var maxY = 0L
    var lastHit = 0L
    var lastRocksFallen = 0L
    var currentRock = Rock(
        positions = listOf(
            Position(3, maxY + 4),
            Position(4, maxY + 4),
            Position(5, maxY + 4),
            Position(6, maxY + 4)
        ), RockName.line
    )
    for (i in 1..7L) {
        matrix.add(i, 0, '-')
    }
    var firstIntervalRocks = 0L
    var firstIntervalY = 0L
    var secondIntervalRocks = 0L
    var secondIntervalY = 0L
    var lastIntervalYValue = 0L
    var intervals = 0L
    var left = 0L
    run breaking@{

        while (true) {
            input17.forEach {
                when (it) {
                    '<' -> {
                        currentRock.left(matrix)
                    }

                    '>' -> {
                        currentRock.right(matrix)
                    }
                }
                if (currentRock.down(matrix)) {
                    stoppedRocks++
                    maxY = maxOf(currentRock.maxY(), maxY)

                    var interval = true
                    var result = ""
                    for (y in (maxY - 5)..(maxY)) {
                        for (x in 1..7L) {
                            result += matrix.get(x, y) ?: '.'
                        }
                    }
                    val lookingFor = """#####..#..###.#...#..#...####...###....###"""

                    if (result == lookingFor) {
                        if (firstIntervalRocks == 0L) {
                            firstIntervalRocks = stoppedRocks
                            firstIntervalY = maxY
                        } else {
                            secondIntervalRocks = stoppedRocks - lastRocksFallen
                            secondIntervalY = maxY - lastHit
                            lastIntervalYValue = maxY
                            intervals = (1000000000000L - firstIntervalRocks) / secondIntervalRocks
                            left = 1000000000000L - firstIntervalRocks - intervals * secondIntervalRocks
                            limitStoppedRocks = stoppedRocks + left
                        }
                        lastHit = maxY
                        lastRocksFallen = stoppedRocks
                    }
                    if (stoppedRocks == limitStoppedRocks) {
                        return@breaking
                    }
                    currentRock = currentRock.nextRock(maxY)
                }

            }
        }
    }

    println(intervals * secondIntervalY + firstIntervalY + maxY - lastIntervalYValue)
}

fun <T> MutableMap<Long, MutableMap<Long, T>>.add(x: Long, y: Long, char: T) {
    val row = get(x) ?: mutableMapOf()
    row[y] = char
    put(x, row)
}

fun <T> MutableMap<Long, MutableMap<Long, T>>.get(x: Long, y: Long): T? {
    return get(x)?.get(y)
}
