package adventofcode2023

import java.io.File
import java.io.FileWriter

fun String.asLongList(): List<Long> {
    return split("\n").map { it.toLong() }
}

fun String.asStringList(): List<String> {
    return split("\n")
}

fun String.asDoubleList(): List<Double> {
    return split("\n").map { it.toDouble() }
}

fun generateFoldersAndProblemAndInput(fromDay: Int, toDay: Int) {
    for (i in fromDay..toDay) {
        File("./src/adventofcode2023/problem$i").mkdir()
        generateInputForDay(i)
        generateProblemForDay(i)
    }
}

fun generateInputForDay(day: Int) {
    val file = File("./src/adventofcode2023/problem$day/input$day.kt")
    val writer = FileWriter(file)
    writer.write(
        "package adventofcode2023.problem$day\n" +
                "\n" +
                "const val input$day = \"\"\"\"\"\"" +
                "\n" +
                "const val input${day}test = \"\"\"\"\"\""
    )
    writer.close()
}

fun generateProblemForDay(day: Int) {
    val file = File("./src/adventofcode2023/problem$day/problem$day.kt")
    val writer = FileWriter(file)
    writer.write(
        "package adventofcode2023.problem$day\n" +
                "\n" +
                "fun main() {\n" +
                "    solveA()\n" +
                "    solveB()\n" +
                "}\n" +
                "\n" +
                "fun solveA() {\n" +
                "\n" +
                "}\n" +
                "\n" +
                "fun solveB() {\n" +
                "\n" +
                "}\n"
    )
    writer.close()
}