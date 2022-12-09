package adventofcode2022.problem7

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    var folders = mutableMapOf<String, Folder>()
    var currentFolder = ""
    input7.split("\n").forEach { row ->
        val cols = row.split(" ")
        val first = cols[0]
        when (first) {
            "${'$'}" -> {
                val second = cols[1]
                when (second) {
                    "cd" -> {
                        val third = cols[2]
                        when (third) {
                            "/" -> {
                                currentFolder = "f";
                            }

                            ".." -> {
                                val folderParts = currentFolder.split("/")
                                currentFolder = folderParts.subList(0, folderParts.size - 1).joinToString("/")
                            }

                            else -> {
                                currentFolder += "/${third}"
                            }
                        }
                        if (!folders.contains(currentFolder)) {
                            folders.put(currentFolder, Folder(path = currentFolder))
                        }
                    }

                    "ls" -> {
                        // listing files upcoming lines
                    }
                }
            }

            "dir" -> {
                val second = currentFolder + "/" + cols[1]
                val folder = folders[second] ?: Folder(path = second)
                folders.put(folder.path, folder)
                folders[currentFolder]?.children?.add(folder)
            }

            else -> {
                val second = cols[1]
                folders[currentFolder]?.files?.add(File(second, first.toInt()))
                folders[currentFolder]?.let {
                    it.sumFiles += first.toInt()
                }
            }
        }
    }
    var totalSum = 0
    folders.forEach {
        it.value.calculateTotalSum()
        if ((it.value.totalSum ?: 0) <= 100000) {
            totalSum += it.value.totalSum ?: 0
        }
    }
    println(totalSum)

}

fun solveB() {
    var folders = mutableMapOf<String, Folder>()
    var currentFolder = ""
    input7.split("\n").forEach { row ->
        val cols = row.split(" ")
        val first = cols[0]
        when (first) {
            "${'$'}" -> {
                val second = cols[1]
                when (second) {
                    "cd" -> {
                        val third = cols[2]
                        when (third) {
                            "/" -> {
                                currentFolder = "f";
                            }

                            ".." -> {
                                val folderParts = currentFolder.split("/")
                                currentFolder = folderParts.subList(0, folderParts.size - 1).joinToString("/")
                            }

                            else -> {
                                currentFolder += "/${third}"
                            }
                        }
                        if (!folders.contains(currentFolder)) {
                            folders.put(currentFolder, Folder(path = currentFolder))
                        }
                    }

                    "ls" -> {
                        // listing files upcoming lines
                    }
                }
            }

            "dir" -> {
                val second = currentFolder + "/" + cols[1]
                val folder = folders[second] ?: Folder(path = second)
                folders.put(folder.path, folder)
                folders[currentFolder]?.children?.add(folder)
            }

            else -> {
                val second = cols[1]
                folders[currentFolder]?.files?.add(File(second, first.toInt()))
                folders[currentFolder]?.let {
                    it.sumFiles += first.toInt()
                }
            }
        }
    }
    var totalSum = 0
    folders.forEach {
        it.value.calculateTotalSum()
        if ((it.value.totalSum ?: 0) <= 100000) {
            totalSum += it.value.totalSum ?: 0
        }
    }
    val totalUsed = folders["f"]?.totalSum ?: 0
    val totalFree = 70000000 - totalUsed
    val needToFree = 30000000 - totalFree
    var best = Int.MAX_VALUE
    folders.forEach {
        val currentSum = it.value.totalSum ?: 0
        if (currentSum >= needToFree && best > currentSum) {
            best = currentSum
        }
    }
    println(best)

}

data class Folder(
    var totalSum: Int? = null,
    val path: String,
    val files: MutableSet<File> = mutableSetOf(),
    var sumFiles: Int = 0,
    val children: MutableSet<Folder> = mutableSetOf()
) : Comparable<Folder> {

    fun calculateTotalSum(): Int {
        if (totalSum != null) {
            return totalSum!!;
        }
        totalSum = sumFiles + children.sumOf { it.calculateTotalSum() }
        return totalSum!!;
    }

    override fun compareTo(other: Folder): Int {
        return other.path.compareTo(path)
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }
}

data class File(
    val fileName: String,
    val size: Int
) : Comparable<File> {
    override fun compareTo(other: File): Int {
        return other.fileName.compareTo(fileName)
    }

    override fun hashCode(): Int {
        return fileName.hashCode()
    }

}
