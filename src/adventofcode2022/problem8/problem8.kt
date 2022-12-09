package adventofcode2022.problem8

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Tree>>()
    var i = 0
    var j = 0
    input8.split("\n").forEach { row ->
        i = 0
        row.forEach {
            val treeHeight = it.toString().toInt()
            matrix.add(i, j, tree = Tree(treeHeight, i, j))
            i++
        }
        j++
    }
    var sum = 0
    for (x in 0 until i) {
        for (y in 0 until j) {
            if (matrix[x]!![y]!!.calculateVisible(matrix)) {
                sum++
            }
        }
    }
    println(sum)
}

fun MutableMap<Int, MutableMap<Int, Tree>>.add(x: Int, y: Int, tree: Tree) {
    val row = get(x) ?: mutableMapOf()
    row.put(y, tree)
    put(x, row)
}

fun solveB() {
    val matrix = mutableMapOf<Int, MutableMap<Int, Tree>>()
    var i = 0
    var j = 0
    input8.split("\n").forEach { row ->
        i = 0
        row.forEach {
            val treeHeight = it.toString().toInt()
            matrix.add(i, j, tree = Tree(treeHeight, i, j))
            i++
        }
        j++
    }
    var best = 0
    for (x in 0 until i) {
        for (y in 0 until j) {
            val scenic = matrix[x]!![y]!!.calculateScenic(matrix)
            if (scenic > best) {
                best = scenic
            }
        }
    }
    println(best)
}

data class Tree(
    val height: Int,
    val xx: Int,
    val yy: Int,
    var visibleLeft: Boolean? = null,
    var visibleTop: Boolean? = null,
    var visibleRight: Boolean? = null,
    var visibleBottom: Boolean? = null,
    var visibleLeftAmount: Int? = null,
    var visibleTopAmount: Int? = null,
    var visibleRightAmount: Int? = null,
    var visibleBottomAmount: Int? = null,
    var visible: Boolean? = null
) {
    fun calculateVisible(matrix: MutableMap<Int, MutableMap<Int, Tree>>): Boolean {
        if (visible != null) {
            return visible!!
        }

        visibleLeft = visibleLeft ?: if (xx == 0) {
            true
        } else {
            matrix[xx - 1]!![yy]!!.calculateVisibleLeft(matrix, height)
        }
        visibleRight = visibleRight ?: if (xx == matrix.size - 1) {
            true
        } else {
            matrix[xx + 1]!![yy]!!.calculateVisibleRight(matrix, height)
        }
        visibleTop = visibleTop ?: if (yy == 0) {
            true
        } else {
            matrix[xx]!![yy - 1]!!.calculateVisibleTop(matrix, height)
        }
        visibleBottom = visibleBottom ?: if (yy == matrix[0]!!.size - 1) {
            true
        } else {
            matrix[xx]!![yy + 1]!!.calculateVisibleBottom(matrix, height)
        }
        visible = visibleRight!! || visibleTop!! || visibleBottom!! || visibleLeft!!
        return visible!!
    }

    private fun calculateVisibleLeftAmount(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int, sum: Int): Int {
        if (this.height >= height) {
            return sum
        }
        if (xx == 0) {
            return sum;
        }
        return matrix[xx - 1]!![yy]!!.calculateVisibleLeftAmount(matrix, height, sum + 1)
    }

    private fun calculateVisibleRightAmount(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int, sum: Int): Int {
        if (this.height >= height) {
            return sum
        }
        if (xx == matrix.size - 1) {
            return sum;
        }
        return matrix[xx + 1]!![yy]!!.calculateVisibleRightAmount(matrix, height, sum + 1)
    }

    private fun calculateVisibleTopAmount(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int, sum: Int): Int {
        if (this.height >= height) {
            return sum
        }
        if (yy == 0) {
            return sum;
        }
        return matrix[xx]!![yy-1]!!.calculateVisibleTopAmount(matrix, height, sum + 1)
    }

    private fun calculateVisibleBottomAmount(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int, sum: Int): Int {
        if (this.height >= height) {
            return sum
        }
        if (yy == matrix.size - 1) {
            return sum;
        }
        return matrix[xx]!![yy+1]!!.calculateVisibleBottomAmount(matrix, height, sum + 1)
    }

    private fun calculateVisibleLeft(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int): Boolean {
        if (this.height >= height) {
            return false
        }
        if (visibleLeft == true) {
            return true
        }
        if (xx == 0) {
            visibleLeft = true
            return visibleLeft!!;
        }
        return matrix[xx - 1]!![yy]!!.calculateVisibleLeft(matrix, height)
    }

    private fun calculateVisibleRight(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int): Boolean {
        if (this.height >= height) {
            return false
        }
        if (visibleRight == true) {
            return true
        }
        if (xx == matrix.size - 1) {
            visibleRight = true
            return visibleRight!!;
        }
        return matrix[xx + 1]!![yy]!!.calculateVisibleRight(matrix, height)
    }

    private fun calculateVisibleTop(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int): Boolean {
        if (this.height >= height) {
            return false
        }
        if (visibleTop == true) {
            return true
        }
        if (yy == 0) {
            visibleTop = true
            return visibleTop!!;
        }
        return matrix[xx]!![yy - 1]!!.calculateVisibleTop(matrix, height)
    }

    private fun calculateVisibleBottom(matrix: MutableMap<Int, MutableMap<Int, Tree>>, height: Int): Boolean {
        if (this.height >= height) {
            return false
        }
        if (visibleBottom == true) {
            return true
        }
        if (yy == matrix[0]!!.size - 1) {
            visibleBottom = true
            return visibleBottom!!;
        }
        return matrix[xx]!![yy + 1]!!.calculateVisibleBottom(matrix, height)
    }

    fun calculateScenic(matrix: MutableMap<Int, MutableMap<Int, Tree>>): Int {
        visibleLeftAmount = if (xx == 0) {
            0
        } else {
            matrix[xx - 1]!![yy]!!.calculateVisibleLeftAmount(matrix, height, 1)
        }
        visibleRightAmount = if (xx == matrix.size - 1) {
            0
        } else {
            matrix[xx + 1]!![yy]!!.calculateVisibleRightAmount(matrix, height, 1)
        }
        visibleTopAmount = if (yy == 0) {
            0
        } else {
            matrix[xx]!![yy - 1]!!.calculateVisibleTopAmount(matrix, height, 1)
        }
        visibleBottomAmount = if (yy == matrix[0]!!.size - 1) {
            0
        } else {
            matrix[xx]!![yy + 1]!!.calculateVisibleBottomAmount(matrix, height, 1)
        }
        return visibleLeftAmount!! * visibleRightAmount!! * visibleTopAmount!! * visibleBottomAmount!!
    }
}
