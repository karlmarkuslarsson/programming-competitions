package adventofcode2023.problem8

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var instructions = listOf<Char>()
    val nodeMap = mutableMapOf<String, Node>()
    input8.split("\n").forEachIndexed { index, s ->
        if (index == 0) instructions = s.toCharArray().toList()
        if (index > 1) {
            val split1 = s.split(" = (")
            val current = split1[0]
            val split2 = split1[1].split(")")[0].split(", ")
            val left = split2[0]
            val right = split2[1]
            nodeMap[current] = Node(current, left, right)
        }
    }
    var steps = 0
    var currentNode = nodeMap["AAA"]
    while (true) {
        val leftOrRight = instructions[steps % instructions.size]
        steps++
        if (leftOrRight == 'L') {
            currentNode = nodeMap[currentNode!!.left]
        }
        if (leftOrRight == 'R') {
            currentNode = nodeMap[currentNode!!.right]
        }
        if (currentNode!!.current == "ZZZ") {
            println(steps)
            return
        }
    }

}

fun solveB() {
    var instructions = listOf<Char>()
    val nodeMap = mutableMapOf<String, Node>()
    input8.split("\n").forEachIndexed { index, s ->
        if (index == 0) instructions = s.toCharArray().toList()
        if (index > 1) {
            val split1 = s.split(" = (")
            val current = split1[0]
            val split2 = split1[1].split(")")[0].split(", ")
            val left = split2[0]
            val right = split2[1]
            nodeMap[current] = Node(current, left, right)
        }
    }
    var steps = 0
    var currentNodes = nodeMap.filter { it.key.endsWith("A") }.values.toList()
    val solutions = mutableListOf<Int>()
    while (true) {
        val leftOrRight = instructions[steps % instructions.size]
        steps++
        if (leftOrRight == 'L') {
            currentNodes = currentNodes.map { nodeMap[it.left]!! }
        }
        if (leftOrRight == 'R') {
            currentNodes = currentNodes.map { nodeMap[it.right]!! }
        }
        currentNodes = currentNodes.filter {
            if (it.current.endsWith("Z")) {
                solutions.add(steps)
                false
            } else {
                true
            }
        }

        if (currentNodes.isEmpty()) {
            println(findLCMOfListOfNumbers(solutions.map { it.toLong() }))
            return
        }
    }
}

fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
    var result = numbers[0]
    for (i in 1 until numbers.size) {
        result = findLCM(result, numbers[i])
    }
    return result
}

fun findLCM(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

data class Node(
    var current: String,
    var left: String?,
    var right: String?
)
