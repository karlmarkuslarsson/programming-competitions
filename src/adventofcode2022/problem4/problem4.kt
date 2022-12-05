package adventofcode2022.problem4

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var sum = 0
    input4.split("\n").forEach { row ->
        val rowSplit = row.split(",")
        val firstSection = parseSection(rowSplit[0])
        val secondSection = parseSection(rowSplit[1])
        if (firstSection.from <= secondSection.from && firstSection.to >= secondSection.to) {
            sum++
        }else if (secondSection.from <= firstSection.from && secondSection.to >= firstSection.to) {
            sum++
        }
    }
    println(sum)
}

fun parseSection(input: String): Section {
    val sectionSplit = input.split("-")
    return Section(sectionSplit[0].toInt(), sectionSplit[1].toInt())
}

data class Section(val from: Int, val to: Int)

fun solveB() {
    var sum = 0
    input4.split("\n").forEach { row ->
        val rowSplit = row.split(",")
        val firstSection = parseSection(rowSplit[0])
        val secondSection = parseSection(rowSplit[1])
        if (secondSection.from <= firstSection.to && secondSection.to >= firstSection.to) {
            sum++
        } else if (firstSection.from <= secondSection.to && firstSection.to >= secondSection.to) {
            sum++
        }
    }
    println(sum)
}
