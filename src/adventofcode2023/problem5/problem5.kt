package adventofcode2023.problem5

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val split1 = input5.split("seeds: ")
    val split2 = split1[1].split("seed-to-soil")
    val seeds = split2[0].split(" ")
        .mapNotNull {
            try {
                it.trim().toLong()
            } catch (e: Exception) {
                null
            }
        }
    val maps = split2[1]
        .split("map:")
        .map {
            val items = mutableListOf<Row>()
            it.split("\n").forEach {
                val rowSplit = it.split(" ")
                try {
                    val destinationFrom = rowSplit[0].toLong()
                    val sourceFrom = rowSplit[1].toLong()
                    val interval = rowSplit[2].toLong()
                    items.add(Row(destinationFrom, sourceFrom, interval))
                } catch (e: Exception) {

                }
            }
            items
        }
    var lowest = Long.MAX_VALUE
    seeds.forEach { seed ->
        var current = seed
        maps.forEach { items ->
            current = items.firstOrNull { row ->
                row.mapTo(current) != null
            }?.mapTo(current) ?: current
        }
        if (current < lowest) {
            lowest = current
        }
    }
    println(lowest)
}

data class Row(val destinationFrom: Long, val sourceFrom: Long, val interval: Long) {
    fun mapTo(number: Long): Long? {
        if (number >= sourceFrom && number <= sourceFrom + interval) {
            return number + (destinationFrom - sourceFrom)
        }
        return null
    }
}


fun solveB() {
    val split1 = input5.split("seeds: ")
    val split2 = split1[1].split("seed-to-soil")
    val seeds1 = split2[0].split(" ")
        .mapNotNull {
            try {
                it.trim().toLong()
            } catch (e: Exception) {
                null
            }
        }
    val maps = split2[1]
        .split("map:")
        .map {
            val items = mutableListOf<Row>()
            it.split("\n").forEach {
                val rowSplit = it.split(" ")
                try {
                    val destinationFrom = rowSplit[0].toLong()
                    val sourceFrom = rowSplit[1].toLong()
                    val interval = rowSplit[2].toLong()
                    items.add(Row(destinationFrom, sourceFrom, interval))
                } catch (e: Exception) {

                }
            }
            items
        }
    var lowest = Long.MAX_VALUE
    for (i in 0 until seeds1.size step 2) {
        for(j in seeds1[i] .. seeds1[i] + seeds1[i+1]) {
            var current = j
            maps.forEach { items ->
                current = items.firstOrNull { row ->
                    row.mapTo(current) != null
                }?.mapTo(current) ?: current
            }
            if (current < lowest) {
                lowest = current
                println(lowest)
            }
        }
    }
    println(lowest)
}
