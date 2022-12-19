package adventofcode2022.problem18

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val pointsX = mutableMapOf<Int, MutableList<Point>>()
    val pointsY = mutableMapOf<Int, MutableList<Point>>()
    val pointsZ = mutableMapOf<Int, MutableList<Point>>()
    val points = mutableSetOf<Point>()
    input18.split("\n").forEach {
        val pointSplit = it.split(",")
        val point = Point(pointSplit[0].toInt(), pointSplit[1].toInt(), pointSplit[2].toInt())
        pointsX.add(point.x, point)
        pointsY.add(point.y, point)
        pointsZ.add(point.z, point)
        points.add(point)
    }
    var sum = 0
    points.forEach { lookAtPoint1 ->
        var subtract = 0
        points.forEach { lookAtPoint2 ->
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.y == lookAtPoint2.y && Math.abs(lookAtPoint1.z - lookAtPoint2.z) == 1) {
                subtract++
            }
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.z == lookAtPoint2.z && Math.abs(lookAtPoint1.y - lookAtPoint2.y) == 1) {
                subtract++
            }
            if (lookAtPoint1.y == lookAtPoint2.y && lookAtPoint1.z == lookAtPoint2.z && Math.abs(lookAtPoint1.x - lookAtPoint2.x) == 1) {
                subtract++
            }
        }
        sum += 6 - subtract
    }
    println(sum)
}

fun <T> MutableMap<Int, MutableList<T>>.add(value: Int, t: T) {
    val list = get(value) ?: mutableListOf<T>()
    list.add(t)
    put(value, list)
}

data class Point(val x: Int, val y: Int, val z: Int) {

    override fun equals(other: Any?): Boolean {
        return other is Point && this.x == other.x && this.y == other.y && this.z == other.z
    }
}

fun solveB() {
    val pointsX = mutableMapOf<Int, MutableList<Point>>()
    val pointsY = mutableMapOf<Int, MutableList<Point>>()
    val pointsZ = mutableMapOf<Int, MutableList<Point>>()
    val points = mutableSetOf<Point>()
    val extraPoints = mutableSetOf<Point>()
    var minX = 0
    var minY = 0
    var minZ = 0
    val limitFrom = Point(minX, minY, minZ)
    input18.split("\n").forEach {
        val pointSplit = it.split(",")
        val point = Point(pointSplit[0].toInt(), pointSplit[1].toInt(), pointSplit[2].toInt())
        pointsX.add(point.x, point)
        pointsY.add(point.y, point)
        pointsZ.add(point.z, point)
        points.add(point)
    }
    val maxX = pointsX.keys.max() + 1
    val maxY = pointsY.keys.max() + 1
    val maxZ = pointsZ.keys.max() + 1
    val limitTo = Point(maxX, maxY, maxZ)
    var sum = 0
    points.forEach { lookAtPoint1 ->
        var subtract = 0
        var subtractXNeg = false
        var subtractYNeg = false
        var subtractZNeg = false
        var subtractXPos = false
        var subtractYPos = false
        var subtractZPos = false
        points.forEach { lookAtPoint2 ->
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.y == lookAtPoint2.y && lookAtPoint1.z - lookAtPoint2.z == 1) {
                subtract++
                subtractZNeg = true
            }
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.z == lookAtPoint2.z && lookAtPoint1.y - lookAtPoint2.y == 1) {
                subtract++
                subtractYNeg = true
            }
            if (lookAtPoint1.y == lookAtPoint2.y && lookAtPoint1.z == lookAtPoint2.z && lookAtPoint1.x - lookAtPoint2.x == 1) {
                subtract++
                subtractXNeg = true
            }
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.y == lookAtPoint2.y && lookAtPoint1.z - lookAtPoint2.z == -1) {
                subtract++
                subtractZPos = true
            }
            if (lookAtPoint1.x == lookAtPoint2.x && lookAtPoint1.z == lookAtPoint2.z && lookAtPoint1.y - lookAtPoint2.y == -1) {
                subtract++
                subtractYPos = true
            }
            if (lookAtPoint1.y == lookAtPoint2.y && lookAtPoint1.z == lookAtPoint2.z && lookAtPoint1.x - lookAtPoint2.x == -1) {
                subtract++
                subtractXPos = true
            }
        }
        if (!subtractZNeg) {
            if (isAir(lookAtPoint1.copy(z = lookAtPoint1.z - 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        if (!subtractYNeg) {
            if (isAir(lookAtPoint1.copy(y = lookAtPoint1.y - 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        if (!subtractXNeg) {
            if (isAir(lookAtPoint1.copy(x = lookAtPoint1.x - 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        if (!subtractXPos) {
            if (isAir(lookAtPoint1.copy(x = lookAtPoint1.x + 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        if (!subtractYPos) {
            if (isAir(lookAtPoint1.copy(y = lookAtPoint1.y + 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        if (!subtractZPos) {
            if (isAir(lookAtPoint1.copy(z = lookAtPoint1.z + 1), limitFrom, limitTo, points, mutableSetOf())) {
                subtract++
            }
        }
        sum += 6 - subtract
    }
    println(sum)

}

val air = mutableMapOf<Point, Boolean>()
fun isAir(point: Point, limitFrom: Point, limitTo: Point, points: Set<Point>, lookingAt: MutableSet<Point>): Boolean {
    lookingAt.add(point)
    val cacheValue = air.get(point)
    if (cacheValue != null) {
        return cacheValue
    }
    if (points.contains(point)) {
        return true
    }
    if (point.x <= limitFrom.x) {
        return false
    }
    if (point.y <= limitFrom.y) {
        return false
    }
    if (point.z <= limitFrom.z) {
        return false
    }
    if (point.x >= limitTo.x) {
        return false
    }
    if (point.y >= limitTo.y) {
        return false
    }
    if (point.z >= limitTo.z) {
        return false
    }
    val x1 = point.copy(x = point.x - 1)
    val x2 = point.copy(x = point.x + 1)
    val y1 = point.copy(y = point.y - 1)
    val y2 = point.copy(y = point.y + 1)
    val z1 = point.copy(z = point.z - 1)
    val z2 = point.copy(z = point.z + 1)
    val pointsToCheck = listOf(x1, x2, y1, y2, z1, z2)
    val badPoint = pointsToCheck.filter { !lookingAt.contains(it) }.firstOrNull {
        lookingAt.add(it)
        isAir(it, limitFrom, limitTo, points, lookingAt) == false
    }
    if (badPoint != null) {
        return false
    }
    return true
}
