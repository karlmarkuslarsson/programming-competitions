package adventofcode2022.problem3

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var sum = 0
    input3.split("\n").forEach { row ->
        val firstPart = row.substring(0, row.length / 2)
        val secondPart = row.substring(row.length / 2, row.length)
        firstPart.first { firstChar ->
            if (secondPart.contains(firstChar)) {
                if (firstChar.isLowerCase()) {
                    sum += firstChar.code - 'a'.code + 1
                } else {
                    sum += firstChar.code - 'A'.code + 27
                }
                true
            } else {
                false
            }
        }
    }
    println(sum)
}

fun solveB() {
    var sum = 0
    val rows = input3.split("\n")
    for (i in 0 until rows.size step 3) {
        val firstRow = rows[i]
        val secondRow = rows[i+1]
        val thirdRow = rows[i+2]
        firstRow.first { firstChar ->
            if (secondRow.contains(firstChar) && thirdRow.contains(firstChar)) {
                if (firstChar.isLowerCase()) {
                    sum += firstChar.code - 'a'.code + 1
                } else {
                    sum += firstChar.code - 'A'.code + 27
                }
                true
            } else {
                false
            }
        }
    }
    println(sum)

}
