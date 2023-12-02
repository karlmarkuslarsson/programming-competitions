package adventofcode2022.problem16

fun main() {
    // solveA()
    solveB()
}

var bestSum = 0

fun solveA() {
    val valves = mutableMapOf<String, Valve>()
    val startValveName = "AA"
    input16.split("\n").forEach { row ->
        val name = row.split(" ")[1]
        val rate = row.split(" ")[4].split("=")[1].split(";")[0].toInt()
        val childrenNames = row.split("valves ")
        if (childrenNames.size > 1) {

            val valve = Valve(name, rate, childrenNames[1].split(", ").map { Child(it) })
            valves.put(name, valve)
        } else {
            val childName = row.split("valve ")[1]
            val valve = Valve(name, rate, listOf(Child(childName)))
            valves.put(name, valve)
        }
    }
    val startValve = valves.get(startValveName)
    val sum = 0
    val minutesLeft = 30
    println(startValve!!.calculateBestWay(sum, minutesLeft, valves))
}

fun solveB() {
    val valves = mutableMapOf<String, Valve>()
    val startValveName = "AA"
    input16.split("\n").forEach { row ->
        val name = row.split(" ")[1]
        val rate = row.split(" ")[4].split("=")[1].split(";")[0].toInt()
        val childrenNames = row.split("valves ")
        if (childrenNames.size > 1) {
            val valve = Valve(name, rate, childrenNames[1].split(", ").map { Child(it) })
            valves.put(name, valve)
        } else {
            val childName = row.split("valve ")[1]
            val valve = Valve(name, rate, listOf(Child(childName)))
            valves.put(name, valve)
        }
    }
    val startValve = valves.get(startValveName)
    val sum = 0
    val minutesLeft = 26
    println(calculateBestWayB(startValve!!, startValve!!, sum, minutesLeft, valves))
}

data class Valve(val name: String, val rate: Int, val children: List<Child>, val calculated: Boolean = false) {
    fun calculateBestWay(
        sum: Int,
        minutesLeftBefore: Int,
        valves: Map<String, Valve>,
        openIfNotOpen: Boolean = false
    ): Int {
        val minutesLeft = if (!openIfNotOpen) minutesLeftBefore else minutesLeftBefore - 1
        val mySum = if (openIfNotOpen) (rate * minutesLeft) else {
            0
        }
        val totalSum = mySum + sum
        if (minutesLeft < 16 && sum < 100) {
            return 0
        }
        if (minutesLeft < 14 && sum < 200) {
            return 0
        }
        if (minutesLeft < 10 && sum < 1000) {
            return 0
        }
        if (minutesLeft < 6 && sum < 1500) {
            return 0
        }
        if (minutesLeft <= 2) {
            if (totalSum > bestSum) {
                bestSum = totalSum
                println(bestSum)
            }
            return totalSum
        }
        return children.filter { it.visited < 3 }.maxOfOrNull { currentChild ->
            val currentChildValve = valves.get(currentChild.name)!!
            var bestSum = 0
            if (!currentChildValve.calculated && currentChildValve.rate != 0) {
                val newValves = mutableMapOf<String, Valve>()
                valves.forEach {
                    newValves.put(
                        it.key,
                        it.value.copy(
                            children = if (it.key == name) it.value.children.map { it.copy(visited = it.visited + 1) } else it.value.children.map { it.copy() },
                            calculated = if (it.key == name) (openIfNotOpen || it.value.calculated) else it.value.calculated
                        )
                    )
                }
                bestSum =
                    newValves.get(currentChild.name)!!.calculateBestWay(totalSum, minutesLeft - 1, newValves, true)
            }
            val newValves = mutableMapOf<String, Valve>()
            valves.forEach {
                newValves.put(
                    it.key,
                    it.value.copy(
                        children = if (it.key == name) it.value.children.map { it.copy(visited = it.visited + 1) } else it.value.children.map { it.copy() },
                        calculated = if (it.key == name) (openIfNotOpen || it.value.calculated) else it.value.calculated
                    )
                )
            }
            Math.max(newValves.get(currentChild.name)!!.calculateBestWay(totalSum, minutesLeft - 1, newValves), bestSum)
        } ?: (totalSum)
    }

}

data class Child(val name: String, var visited: Int = 0)

var bestB = hashMapOf<String, BestValue>()

fun calculateBestWayB(
    valve1: Valve,
    valve2: Valve,
    sum: Int,
    minutesLeftBefore: Int,
    valves: Map<String, Valve>,
    open1: Boolean = false,
    open2: Boolean = false,
): Int {
    val bestKey1 = mutableListOf(valve1.name + if (open1) "1" else "0", valve2.name + if (open2) "1" else "0").sorted()
        .joinToString("")
    val bestKey2 = valves.filter { it.value.calculated }.map { it.key }.sorted().joinToString("")
    val bestKey3 = minutesLeftBefore
    val bestKey = bestKey1 + bestKey2 + bestKey3
    val preBest = bestB.get(bestKey)
    if (preBest != null) {
        return preBest.sum
    }
    val visitedLimit = 8
    val minutesLeft = minutesLeftBefore - 1
    val mySum1 = if (open1) (valve1.rate * minutesLeft) else {
        0
    }
    val mySum2 = if (open2) (valve2.rate * minutesLeft) else {
        0
    }
    val totalSum = mySum1 + mySum2 + sum
    /*
    if (minutesLeft < 10 && sum < 100) {
        return 0
    }
    if (minutesLeft < 20 && sum < 400) {
        return 0
    }
    */

    /*
    if (minutesLeft <= 21 && sum < 880) {
        return 0
    }
    */
    if (minutesLeft <= 10 && sum < 1500) {
        return 0
    }
    /*
    if (minutesLeft < 12) {
        val heh = 2
    }


     */
    if (minutesLeft <= 0) {
        if (totalSum > bestSum) {
            bestSum = totalSum
            println(bestSum)
        }
        return totalSum
    }
    val sum = if (open1) {
        if (open2) {
            val newValves = createNewValues(valves, open1, open2, valve1.name, valve2.name)
            calculateBestWayB(
                newValves.get(valve1.name)!!,
                newValves.get(valve2.name)!!,
                totalSum,
                minutesLeft,
                newValves,
                false,
                false
            )
        } else {
            var newValves = mutableMapOf<String, Valve>()
            valve2.children.filter { it.visited < visitedLimit }.maxOfOrNull { currentChild ->
                var bestSum = 0
                newValves = createNewValues(valves, open1, open2, valve1.name, valve2.name)
                val currentChildValve = newValves.get(currentChild.name)!!
                if (!currentChildValve.calculated && currentChildValve.rate != 0) {
                    bestSum = calculateBestWayB(
                        newValves.get(valve1.name)!!,
                        newValves.get(currentChild.name)!!,
                        totalSum,
                        minutesLeft,
                        newValves,
                        false,
                        true
                    )
                }
                newValves =
                    createNewValues(valves, open1, open2, valve1.name, valve2.name)
                Math.max(
                    calculateBestWayB(
                        newValves.get(valve1.name)!!,
                        newValves.get(currentChild.name)!!,
                        totalSum,
                        minutesLeft,
                        newValves,
                        false,
                        false
                    ),
                    bestSum
                )
            } ?: totalSum
        }
    } else {
        if (open2) {
            var newValves = mutableMapOf<String, Valve>()
            valve1.children.filter { it.visited < visitedLimit }.maxOfOrNull { currentChild ->
                newValves =
                    createNewValues(valves, open1, open2, valve1.name, valve2.name)
                val currentChildValve = newValves.get(currentChild.name)!!
                var bestSum = 0
                if (!currentChildValve.calculated && currentChildValve.rate != 0) {
                    bestSum = calculateBestWayB(
                        newValves.get(currentChild.name)!!,
                        newValves.get(valve2.name)!!,
                        totalSum,
                        minutesLeft,
                        newValves,
                        true,
                        false
                    )
                }
                newValves =
                    createNewValues(valves, open1, open2, valve1.name, valve2.name)
                Math.max(
                    calculateBestWayB(
                        newValves.get(currentChild.name)!!,
                        newValves.get(valve2.name)!!,
                        totalSum,
                        minutesLeft,
                        newValves,
                        false,
                        false
                    ),
                    bestSum
                )
            } ?: totalSum
        } else {
            var newValves = mutableMapOf<String, Valve>()
            valve1.children.filter { it.visited < visitedLimit }.maxOfOrNull<Child, Int> { currentChild1 ->
                val innerMax =
                    valve2.children.filter { it.visited < visitedLimit }.maxOfOrNull { currentChild2 ->
                        // TEST 1 not open, open,

                        newValves =
                            createNewValues(valves, open1, open2, valve1.name, valve2.name)
                        val currentChildValve1 = newValves.get(currentChild1.name)!!
                        val currentChildValve2 = newValves.get(currentChild2.name)!!
                        var bestSum = 0
                        val youCanBeCalculated = !currentChildValve1.calculated && currentChildValve1.rate != 0
                        val elephantCanBeCalculated =
                            !currentChildValve2.calculated && currentChildValve2.rate != 0
                        if (youCanBeCalculated && elephantCanBeCalculated && currentChild2.name != currentChild1.name) {
                            bestSum = calculateBestWayB(
                                newValves.get(currentChildValve1.name)!!,
                                newValves.get(currentChildValve2.name)!!,
                                totalSum,
                                minutesLeft,
                                newValves,
                                true,
                                true
                            )
                            newValves =
                                createNewValues(valves, open1, open2, valve1.name, valve2.name)
                            bestSum = Math.max(
                                calculateBestWayB(
                                    newValves.get(currentChildValve1.name)!!,
                                    newValves.get(currentChildValve2.name)!!,
                                    totalSum,
                                    minutesLeft,
                                    newValves,
                                    true,
                                    false
                                ), bestSum
                            )
                            newValves =
                                createNewValues(valves, open1, open2, valve1.name, valve2.name)
                            bestSum = Math.max(
                                calculateBestWayB(
                                    newValves.get(currentChildValve1.name)!!,
                                    newValves.get(currentChildValve2.name)!!,
                                    totalSum,
                                    minutesLeft,
                                    newValves,
                                    false,
                                    true
                                ), bestSum
                            )
                        } else if (youCanBeCalculated) {
                            newValves =
                                createNewValues(valves, open1, open2, valve1.name, valve2.name)
                            bestSum = Math.max(
                                calculateBestWayB(
                                    newValves.get(currentChildValve1.name)!!,
                                    newValves.get(currentChildValve2.name)!!,
                                    totalSum,
                                    minutesLeft,
                                    newValves,
                                    true,
                                    false
                                ), bestSum
                            )
                        } else if (elephantCanBeCalculated) {
                            newValves =
                                createNewValues(valves, open1, open2, valve1.name, valve2.name)
                            bestSum = Math.max(
                                calculateBestWayB(
                                    newValves.get(currentChildValve1.name)!!,
                                    newValves.get(currentChildValve2.name)!!,
                                    totalSum,
                                    minutesLeft,
                                    newValves,
                                    false,
                                    true
                                ), bestSum
                            )
                        }
                        newValves =
                            createNewValues(valves, open1, open2, valve1.name, valve2.name)
                        bestSum = Math.max(
                            calculateBestWayB(
                                newValves.get(currentChildValve1.name)!!,
                                newValves.get(currentChildValve2.name)!!,
                                totalSum,
                                minutesLeft,
                                newValves,
                                false,
                                false
                            ), bestSum
                        )
                        bestSum
                    } ?: totalSum
                return innerMax
            } ?: totalSum
        }
    }
    bestB.put(bestKey, BestValue(timeLeft = minutesLeftBefore, sum))
    return sum
}

fun createNewValues(
    valves: Map<String, Valve>,
    valve1Calculated: Boolean,
    valve2Calculated: Boolean,
    valve1Name: String,
    valve2Name: String
): MutableMap<String, Valve> {
    val newValves = mutableMapOf<String, Valve>()
    valves.forEach {
        val calculated =
            if (valve1Calculated && valve1Name == it.key) {
                true
            } else if (valve2Calculated && valve2Name == it.key) {
                true
            } else {
                it.value.calculated
            }
        val visitedExtra = if (!valve1Calculated && valve1Name == it.key) {
            1
        } else if (!valve2Calculated && valve2Name == it.key) {
            1
        } else {
            0
        }
        newValves.put(
            it.key,
            it.value.copy(
                children = it.value.children.map { it.copy(visited = it.visited + visitedExtra) },
                calculated = calculated
            )
        )
    }
    return newValves
}

data class BestValue(
    val timeLeft: Int,
    val sum: Int
)