package adventofcode2023.problem3

import adventofcode2023.add

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    input3.split("\n").forEachIndexed { x, row ->
        row.forEachIndexed { y, char ->
            matrix.add(x, y, char)
        }
    }
    var sum = 0
    matrix.forEach { (x, yMap) ->
        var currentNumber = ""
        var shouldCount = false
        yMap.forEach { (y, char) ->
            when {
                char.isDigit() -> {
                    currentNumber += "$char"
                    if (!shouldCount && findSymbol(x, y, matrix, '.')) {
                        shouldCount = true
                    }
                }

                else -> {
                    if (shouldCount && currentNumber.isNotEmpty()) {
                        sum += currentNumber.toInt()
                    }
                    currentNumber = ""
                    shouldCount = false
                }

            }
        }
        if (currentNumber.isNotEmpty()) {
            if (!shouldCount && findSymbol(x, yMap.size, matrix, '.')) {
                sum += currentNumber.toInt()
            }
            if (shouldCount) {
                sum += currentNumber.toInt()
            }
        }
    }
    println(sum)
}

fun findSymbol(x: Int, y: Int, matrix: MutableMap<Int, MutableMap<Int, Char>>, c: Char): Boolean {
    for (x1 in x - 1..x + 1) {
        for (y1 in y - 1..y + 1) {
            val value = matrix.get(x1)?.get(y1)
            if (value != null && value != c && !value.isDigit()) {
                return true
            }
        }
    }
    return false
}

fun findSymbolExact(
    x: Int,
    y: Int,
    matrix: MutableMap<Int, MutableMap<Int, Char>>,
    c: Char,
    gears: MutableMap<Int, MutableMap<Int, Gear>>,
    waitingGeas: MutableSet<Gear>
): Boolean {
    var valid = false
    for (x1 in x - 1..x + 1) {
        for (y1 in y - 1..y + 1) {
            val value = matrix.get(x1)?.get(y1)
            if (value != null && value == c) {
                var gear = gears.get(x1)?.get(y1)
                if (gear == null) {
                    gear = Gear()
                    gears.add(x1, y1, gear)
                }
                waitingGeas.add(gear)
                valid = true
            }
        }
    }
    return valid
}

fun isNumber(char: Char): Boolean {
    return char.isDigit()

}

fun solveB() {
    val gears = mutableMapOf<Int, MutableMap<Int, Gear>>()
    val matrix = mutableMapOf<Int, MutableMap<Int, Char>>()
    input3.split("\n").forEachIndexed { x, row ->
        row.forEachIndexed { y, char ->
            matrix.add(x, y, char)
        }
    }
    matrix.forEach { (x, yMap) ->
        var currentNumber = ""
        var shouldCount = false
        val waitingGeas = mutableSetOf<Gear>()
        waitingGeas.clear()
        yMap.forEach { (y, char) ->
            when {
                char.isDigit() -> {
                    currentNumber += "$char"
                    if (findSymbolExact(x, y, matrix, '*', gears, waitingGeas)) {
                        shouldCount = true
                    }
                }

                else -> {
                    if (shouldCount && currentNumber.isNotEmpty()) {
                        waitingGeas.forEach {
                            it.numbers.add(currentNumber.toInt())
                        }
                    }
                    waitingGeas.clear()
                    currentNumber = ""
                    shouldCount = false
                }

            }
        }
        if (currentNumber.isNotEmpty()) {
            if (findSymbolExact(x, yMap.size, matrix, '*', gears, waitingGeas)) {
                shouldCount = true

            }
            if (shouldCount) {
                waitingGeas.forEach {
                    it.numbers.add(currentNumber.toInt())
                }
            }
        }
    }
    var sum = 0
    gears.forEach { (x, yMap) ->
        yMap.forEach { (y, gear) ->
            if (gear.numbers.size == 2) {
                sum += gear.numbers[0] * gear.numbers[1]
            }
        }
    }
    println(sum)
}

data class Gear(
    var numbers: MutableList<Int> = mutableListOf(),
)
