package adventofcode2022.problem15

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val matrix = mutableMapOf<Long, MutableMap<Long, Char>>()
    var minX = Long.MAX_VALUE
    var maxX = Long.MIN_VALUE
    val goalY = 2000000L
    input15.split("\n").forEach {
        val rowSplitX = it.split("x=")
        val rowSplitY = it.split("y=")
        val sensorX = rowSplitX[1].split(", ")[0].toLong()
        val sensorY = rowSplitY[1].split(": ")[0].toLong()

        val beaconX = rowSplitX[2].split(", ")[0].toLong()
        val beaconY = rowSplitY[2].toLong()
        val deltaX = Math.abs(sensorX - beaconX)
        val deltaY = Math.abs(sensorY - beaconY)
        val totalDistance = deltaX + deltaY
        matrix.add(sensorX, sensorY, 'S')
        matrix.add(beaconX, beaconY, 'B')
        for (x in sensorX - totalDistance..sensorX + totalDistance) {
            if (goalY >= sensorY - totalDistance && goalY <= sensorY + totalDistance) {
                if (Math.abs(sensorX - x) + Math.abs(sensorY - goalY) <= totalDistance && matrix.get(
                        x,
                        goalY
                    ) == null
                ) {
                    matrix.add(x, goalY, 'X')
                    minX = Math.min(x, minX)
                    maxX = Math.max(x, maxX)
                }
            }
        }
    }
    var sum = 0
    for (x in minX..maxX) {
        if (matrix.get(x, goalY) == 'X') sum++
    }
    println(sum)

}

data class Section(
    val minX: Long,
    val maxX: Long,
    val minY: Long,
    val maxY: Long,
    val sensor: Position,
    val maxDistance: Long
) {
    fun inSection(x: Long, y: Long): Boolean {
        if (x < minX) {
            return false
        }
        if (x > maxX) {
            return false
        }
        if (y < minY) {
            return false
        }
        if (y > maxY) {
            return false
        }
        if (Math.abs(sensor.x - x) + Math.abs(sensor.y - y) <= maxDistance) {
            return true
        }
        return false
    }
}

data class Position(val x: Long, val y: Long)

fun solveB() {
    // x > 30000
    val sections = mutableListOf<Section>()
    val minArea = 0L
    val maxArea = 4000000L
    val positionsToDiscover = mutableListOf<Position>()
    input15.split("\n").forEach {
        val rowSplitX = it.split("x=")
        val rowSplitY = it.split("y=")
        val sensorX = rowSplitX[1].split(", ")[0].toLong()
        val sensorY = rowSplitY[1].split(": ")[0].toLong()

        val beaconX = rowSplitX[2].split(", ")[0].toLong()
        val beaconY = rowSplitY[2].toLong()
        val deltaX = Math.abs(sensorX - beaconX)
        val deltaY = Math.abs(sensorY - beaconY)
        val totalDistance = deltaX + deltaY
        sections.add(
            Section(
                minX = sensorX - totalDistance,
                minY = sensorY - totalDistance,
                maxX = sensorX + totalDistance,
                maxY = sensorY + totalDistance,
                maxDistance = totalDistance,
                sensor = Position(sensorX, sensorY)
            )
        )
        for (x in sensorX - totalDistance - 1..sensorX + totalDistance + 1) {
            //for (y in sensorY - totalDistance - 1..sensorY + totalDistance + 1) {
            val distanceX = Math.abs(x - sensorX)
            val distanceY = totalDistance + 1 - distanceX
            val y1 = sensorY + distanceY
            val y2 = sensorY - distanceY
            if (x >= 0 && y1 >= 0 && x <= maxArea && y1 <= maxArea) {
                positionsToDiscover.add(Position(x, y1))
            }
            if (x >= 0 && y2 >= 0 && x <= maxArea && y2 <= maxArea) {
                positionsToDiscover.add(Position(x, y2))
            }
            //}
        }
    }
    sections.sortByDescending { it.maxDistance }

    //for (x in 120000L..maxArea) {
    //   for (y in minArea..maxArea) {
    positionsToDiscover.forEach { position ->
        val section = sections.firstOrNull { it.inSection(position.x, position.y) }
        if (section == null) {
            println("x: ${position.x}")
            println("y: ${position.y}")
            println(position.x * 4000000 + position.y)
        }
    }
    // if (x % 1000 == 0L) {
    //    println("x: ${x}")
    // }
    //}
}

fun <T> MutableMap<Long, MutableMap<Long, T>>.add(x: Long, y: Long, char: T) {
    val row = get(x) ?: mutableMapOf()
    row[y] = char
    put(x, row)
}

fun <T> MutableMap<Long, MutableMap<Long, T>>.get(x: Long, y: Long): T? {
    return get(x)?.get(y)
}
