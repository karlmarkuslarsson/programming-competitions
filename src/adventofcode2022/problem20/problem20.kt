package adventofcode2022.problem20

import java.util.LinkedList
import java.util.UUID

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val inputList = mutableListOf<Item>()
    var newList = LinkedList<Item>()
    input20test.split("\n").forEach {
        val item = Item(UUID.randomUUID().toString(), it.toInt())
        inputList.add(item)
        newList.add(item)
    }
    inputList.forEach { input ->
        val index = newList.indexOfFirst { input.id == it.id }
        val steps = input.value
        val final = ((index + steps) + newList.size * 4) % newList.size
        newList.remove(input)
        if (final == 0) {
            newList.addFirst(input)
        } else if (final < index) {
            val firstPart = newList.subList(0, final)
            val secondPart = newList.subList(final, newList.size)
            newList = LinkedList<Item>()
            newList.addAll(firstPart)
            newList.add(input)
            newList.addAll(secondPart)
        } else {
            val firstPart = newList.subList(0, final)
            val secondPart = newList.subList(final, newList.size)
            newList = LinkedList<Item>()
            newList.addAll(firstPart)
            newList.add(input)
            newList.addAll(secondPart)
        }
    }
    var sum = 0
    for(i in 1000 .. 3000 step 1000) {
        sum += newList[i % newList.size].value
    }
    println(sum)

}

data class Item(
    val id: String,
    val value: Int
)


fun solveB() {

}
