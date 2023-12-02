import adventofcode2023.problem1.input1a

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    input1a.split("\n").map {
        val numbersOnly = it.filter { char -> char.isDigit() }
        (numbersOnly.first() + "" + numbersOnly.last()).toInt()
    }.sum().let(::println)
}

fun solveB() {
    val numbers = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    input1a.split("\n").map {
        var notNumbers = it
        numbers.forEachIndexed { index, number ->
            notNumbers = notNumbers.replace(number, "${number[0]}${index + 1}${number[number.length - 1]}")
        }
        val numbersOnly = notNumbers.filter { char -> char.isDigit() }
        if (numbersOnly.isBlank()) {
            0
        } else {
            (numbersOnly.first() + "" + numbersOnly.last()).toInt()
        }
    }.sum().let(::println)
}
