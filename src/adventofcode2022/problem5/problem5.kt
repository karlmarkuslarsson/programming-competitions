package adventofcode2022.problem5

import java.util.*

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val startHeight = 8
    val totalStacks = 9
    val stacks = mutableListOf<LinkedList<Char>>()
    for (i in 0 until totalStacks) {
        stacks.add(LinkedList())
    }
    var rowCounter = 0
    input5.split("\n").forEach {
        if (rowCounter < startHeight) {
            var j = 0
            for (i in 0 until it.length step 4) {
                val currentChar = it[i + 1]
                if (currentChar != ' ') {
                    stacks[j].add(currentChar)
                }
                j++
            }
        } else if (rowCounter < startHeight + 2) {
            // do nothing
        } else {
            val moveRow = it.split(" ")
            val createsToMove = moveRow[1].toInt()
            val from = moveRow[3].toInt()
            val to = moveRow[5].toInt()
            for (i in 0 until createsToMove) {
                val char = stacks[from - 1].removeFirst()
                stacks[to - 1].addFirst(char)
            }
        }

        rowCounter++
    }
    println(stacks.map { it.first }.joinToString(""))

}

fun solveB() {
    val startHeight = 8
    val totalStacks = 9
    val stacks = mutableListOf<LinkedList<Char>>()
    for (i in 0 until totalStacks) {
        stacks.add(LinkedList())
    }
    var rowCounter = 0
    input5.split("\n").forEach {
        if (rowCounter < startHeight) {
            var j = 0
            for (i in 0 until it.length step 4) {
                val currentChar = it[i + 1]
                if (currentChar != ' ') {
                    stacks[j].add(currentChar)
                }
                j++
            }
        } else if (rowCounter < startHeight + 2) {
            // do nothing
        } else {
            val moveRow = it.split(" ")
            val createsToMove = moveRow[1].toInt()
            val from = moveRow[3].toInt()
            val to = moveRow[5].toInt()
            val itemsToBeRemoved = mutableListOf<Char>()
            for (i in 0 until createsToMove) {
                itemsToBeRemoved.add(stacks[from - 1].removeFirst())
            }
            itemsToBeRemoved.reversed().forEach {
                stacks[to - 1].addFirst(it)
            }
        }

        rowCounter++
    }
    println(stacks.map { it.first }.joinToString(""))
}

