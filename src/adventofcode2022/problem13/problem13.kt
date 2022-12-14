package adventofcode2022.problem13

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var sum = 0
    val rows = input13.split("\n")
    var j = 1
    for (i in 1..rows.size step 3) {

        val first = rows[i - 1]
        val second = rows[i]
        val firstItem = createItem(first)
        val secondItem = createItem(second)
        if (compareLists(firstItem.children, secondItem.children) != false) {
            sum += j
        }
        j++
    }
    println(sum)

}

fun createItem(first: String): Item {
    var currentValue = ""
    val firstItem = Item(null, mutableListOf(), null, first)
    var currentItem: Item = firstItem
    first.forEach {
        when (it) {
            '[' -> {
                val newItem = Item(null, mutableListOf(), currentItem, null)
                currentItem.children.add(newItem)
                currentItem = newItem
            }

            ',' -> {
                if (currentValue.isNotEmpty()) {
                    currentItem.children.add(Item(currentValue.toInt(), mutableListOf(), currentItem, null))
                } else {
                    // nothing
                }
                currentValue = ""

            }

            ']' -> {
                if (currentValue.isNotEmpty()) {
                    currentItem.children.add(Item(currentValue.toInt(), mutableListOf(), currentItem, null))
                } else {
                    // nothing
                }
                currentValue = ""
                currentItem = currentItem.parentItem!!
            }

            else -> {
                currentValue += it.toString()
            }
        }
    }
    return firstItem
}

fun solveB() {
    var sum = 0
    val rows = input13.split("\n")
    var j = 1
    val items = mutableListOf<Item>()
    for (i in 1..rows.size step 3) {
        val first = rows[i - 1]
        val second = rows[i]
        val firstItem = createItem(first)
        val secondItem = createItem(second)
        items.add(firstItem)
        items.add(secondItem)
        j++
    }
    items.add(createItem("[[2]]"))
    items.add(createItem("[[6]]"))
    items.sortWith(ItemComparator())
    val indicator1 = items.indexOfFirst { it.fullString == "[[2]]" } +1
    val indicator2 = items.indexOfFirst { it.fullString == "[[6]]" }+1
    println(indicator1 * indicator2)
}

class ItemComparator : Comparator<Item> {
    override fun compare(o1: Item, o2: Item): Int {
        if (compareLists(o1.children, o2.children) != false) {
            return -1
        }
        return 1

    }

}

class Item(val value: Int?, val children: MutableList<Item>, val parentItem: Item?, val fullString: String? = null) {

}

fun compareLists(left: List<Item>, right: List<Item>): Boolean? {
    if (left.isEmpty() && right.isEmpty()) {
        return null
    }
    if (left.isEmpty()) {
        return true
    }
    if (right.isEmpty()) {
        return false
    }
    for (i in 0 until Math.min(left.size, right.size)) {
        val leftItem = left[i]
        val rightItem = right[i]
        if (leftItem.value != null && rightItem.value != null) {
            if (leftItem.value < rightItem.value) {
                return true
            } else if (rightItem.value < leftItem.value) {
                return false
            } else {
                // check next item
            }
        } else if (leftItem.value != null) {
            val result = compareLists(left = listOf(Item(leftItem.value, mutableListOf(), null)), rightItem.children)
            if (result != null) {
                return result
            }
        } else if (rightItem.value != null) {
            val result =
                compareLists(left = leftItem.children, right = listOf(Item(rightItem.value, mutableListOf(), null)))
            if (result != null) {
                return result
            }
        } else {
            val result = compareLists(left = leftItem.children, right = rightItem.children)
            if (result != null) {
                return result
            }
        }
    }
    if (left.size < right.size) {
        return true
    } else if (left.size > right.size) {
        return false
    }
    return null
}