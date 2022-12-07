package adventofcode2022.problem6

import java.util.LinkedList

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val lastCharacters = LinkedList<Char>()
    var counter = 0
    input6.forEach {
        counter++
        lastCharacters.addFirst(it)
        if (lastCharacters.size > 4) {
            lastCharacters.removeLast()
        }
        if (lastCharacters.size == 4) {
            val set = hashSetOf<Char>()
            set.addAll(lastCharacters)
            if (set.size == lastCharacters.size) {
                println(counter)
                return
            }
        }
    }
}

fun solveB() {
    val lastCharacters = LinkedList<Char>()
    var counter = 0
    input6.forEach {
        counter++
        lastCharacters.addFirst(it)
        if (lastCharacters.size > 14) {
            lastCharacters.removeLast()
        }
        if (lastCharacters.size == 14) {
            val set = hashSetOf<Char>()
            set.addAll(lastCharacters)
            if (set.size == lastCharacters.size) {
                println(counter)
                return
            }
        }
    }
}
