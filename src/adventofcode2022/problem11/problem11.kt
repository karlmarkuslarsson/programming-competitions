package adventofcode2022.problem11

import java.math.BigInteger

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val monkies: MutableList<Monkey> = mutableListOf()
    var currentMonkey: Monkey? = null
    val rows = input11.split("\n")
    for (i in 0 until rows.size step 7) {
        val divisibleSplit = rows[i + 3].split(" ")
        val divisibleTrueSplit = rows[i + 4].split(" ")
        val divisibleFalseSplit = rows[i + 5].split(" ")
        val operationSplit = rows[i + 2].split(": ")[1]
        val operations = operationSplit.split(" ")
        monkies.add(
            Monkey(
                i,
                rows[i + 1].split(": ")[1].split(", ").map { BigInteger.valueOf(it.toLong()) }.toMutableList(),
                {
                    val first = if (operations[2] == "old") it else BigInteger.valueOf(operations[2].toLong())
                    val second = if (operations[4] == "old") it else BigInteger.valueOf(operations[4].toLong())
                    when (operations[3]) {
                        "*" -> {
                            first * second
                        }

                        "+" -> {
                            first + second
                        }

                        else -> {
                            BigInteger.ZERO
                        }
                    }
                },
                BigInteger.valueOf(divisibleSplit[divisibleSplit.size - 1].toLong()),
                divisibleTrueSplit[divisibleTrueSplit.size - 1].toInt(),
                divisibleFalseSplit[divisibleFalseSplit.size - 1].toInt()
            )
        )
    }
    for (i in 1..20) {
        monkies.forEach { monkey ->
            monkey.startingItems.forEach { item ->
                val new = monkey.operation(item)
                val bored = new.divide(BigInteger.valueOf(3))
                if (bored.mod(monkey.testDivisible) == BigInteger.ZERO) {
                    monkies[monkey.testTrue].startingItems.add(bored)
                } else {
                    monkies[monkey.testFalse].startingItems.add(bored)
                }
                monkey.inspections++
            }
            monkey.startingItems.clear()
        }
    }
    monkies.sortByDescending { it.inspections }
    println(monkies[0].inspections * monkies[1].inspections)
}


data class Monkey(
    val number: Int,
    var startingItems: MutableList<BigInteger>,
    val operation: (old: BigInteger) -> BigInteger,
    val testDivisible: BigInteger,
    val testTrue: Int,
    val testFalse: Int,
    var inspections: BigInteger = BigInteger.ZERO,
)

fun solveB() {
    val monkies: MutableList<Monkey> = mutableListOf()
    var currentMonkey: Monkey? = null
    val rows = input11.split("\n")
    for (i in 0 until rows.size step 7) {
        val divisibleSplit = rows[i + 3].split(" ")
        val divisibleTrueSplit = rows[i + 4].split(" ")
        val divisibleFalseSplit = rows[i + 5].split(" ")
        val operationSplit = rows[i + 2].split(": ")[1]
        val operations = operationSplit.split(" ")
        monkies.add(
            Monkey(
                i,
                rows[i + 1].split(": ")[1].split(", ").map { BigInteger.valueOf(it.toLong()) }.toMutableList(),
                {
                    val first = if (operations[2] == "old") it else BigInteger.valueOf(operations[2].toLong())
                    val second = if (operations[4] == "old") it else BigInteger.valueOf(operations[4].toLong())
                    when (operations[3]) {
                        "*" -> {
                            first * second
                        }

                        "+" -> {
                            first + second
                        }

                        else -> {
                            BigInteger.ZERO
                        }
                    }
                },
                BigInteger.valueOf(divisibleSplit[divisibleSplit.size - 1].toLong()),
                divisibleTrueSplit[divisibleTrueSplit.size - 1].toInt(),
                divisibleFalseSplit[divisibleFalseSplit.size - 1].toInt()
            )
        )
    }
    val divisibles = monkies.map { it.testDivisible }
    var divisibleProduct = BigInteger.ONE
    divisibles.forEach {
        divisibleProduct *= it
    }
    for (i in 1..10000) {
        monkies.forEach { monkey ->
            monkey.startingItems.forEach { item ->
                val new = monkey.operation(item)
                val bored = new.mod (divisibleProduct)

                if (bored.mod(monkey.testDivisible) == BigInteger.ZERO) {
                    monkies[monkey.testTrue].startingItems.add(bored)
                } else {
                    monkies[monkey.testFalse].startingItems.add(bored)
                }
                monkey.inspections++
            }
            monkey.startingItems.clear()
        }
    }
    monkies.sortByDescending { it.inspections }
    println(monkies[0].inspections * monkies[1].inspections)
}
