package adventofcode2023.problem4

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var sum = 0
    input4.split("\n").forEach {
        val twoSides = it.split(": ")[1].split(" | ")
        val setLeft = mutableSetOf<Int>()
        val setRight = mutableSetOf<Int>()
        twoSides[0].split(" ").forEach {
            if (it.isNotBlank()) {
                setLeft.add(it.toInt())
            }
        }
        twoSides[1].split(" ").forEach {
            if (it.isNotBlank()) {
                setRight.add(it.toInt())
            }
        }
        val common = setRight.intersect(setLeft).size
        if (common > 0) {
            var sum2 = 1
            for (i in 1..common - 1) {
                sum2 *= 2
            }
            sum += sum2
        }
    }
    println(sum)

}

fun solveB() {
    var sum = 0
    val copies = mutableMapOf<Int, Int>()
    input4.split("\n").forEachIndexed { index, s ->
        val number = index + 1
        copies.put(number, (copies.get(number) ?: 0) + 1)
        val ss = s.split(": ")
        val twoSides = ss[1].split(" | ")
        val setLeft = mutableSetOf<Int>()
        val setRight = mutableSetOf<Int>()
        twoSides[0].split(" ").forEach {
            if (it.isNotBlank()) {
                setLeft.add(it.toInt())
            }
        }
        twoSides[1].split(" ").forEach {
            if (it.isNotBlank()) {
                setRight.add(it.toInt())
            }
        }
        val numberOfCopies = copies.get(number)!!
        val common = setRight.intersect(setLeft).size
        if (common > 0) {
            for (i in number + 1..number + 1 + common - 1) {
                copies.put(i, (copies.get(i) ?: 0) + numberOfCopies)
            }
        }
    }
    println(copies.map { it.value }.sum())
}
