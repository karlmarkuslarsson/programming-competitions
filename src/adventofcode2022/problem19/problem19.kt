package adventofcode2022.problem19

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    val blueprints = mutableListOf<BluePrint>()
    input19.split("\n").forEach {
        val id = it.split("Blueprint ")[1].split(": Each ore")[0].toInt()
        val costOreInOre = it.split("Each ore robot costs ")[1].split(" ore. Each ")[0].toInt()
        val costClayInOre = it.split("Each clay robot costs ")[1].split(" ore. Each obsidian ")[0].toInt()
        val costObsidianInOre = it.split("Each obsidian robot costs ")[1].split(" ore and ")[0].toInt()
        val costObsidianInClay =
            it.split("Each obsidian robot costs ")[1].split(" ore and ")[1].split(" clay. Each geode ")[0].toInt()
        val costGeodeInOre = it.split("Each geode robot costs ")[1].split(" ore and ")[0].toInt()
        val costGeodeInObsidian =
            it.split("Each geode robot costs ")[1].split(" ore and ")[1].split(" obsidian.")[0].toInt()
        val bluePrint = BluePrint(
            id,
            Price(ore = costOreInOre),
            Price(ore = costClayInOre),
            Price(ore = costObsidianInOre, clay = costObsidianInClay),
            Price(ore = costGeodeInOre, obsidian = costGeodeInObsidian)
        )
        blueprints.add(bluePrint)
    }
    var sum =0
    blueprints.forEach {
        val result = it.findBest(24, RState(oreRobots = 1))
        println("${it.id}: " + it.findBest(24, RState(oreRobots = 1)))
        sum += (result * it.id)
        best = 0
    }
    println(sum)

}

data class RState(
    val ore: Int = 0,
    val oreRobots: Int = 0,
    val clay: Int = 0,
    val clayRobots: Int = 0,
    val obsidian: Int = 0,
    val obsidianRobots: Int = 0,
    val geode: Int = 0,
    val geodeRobots: Int = 0,
)

fun solveB() {

}

var best = 0

data class BluePrint(
    val id: Int,
    val costOre: Price,
    val costClay: Price,
    val costObsidian: Price,
    val costGeode: Price
) {
    fun findBest(minutes: Int, RState: RState): Int {
        if (minutes == 0) {
            if (RState.geode + RState.geodeRobots > best) {
                best = RState.geode + RState.geodeRobots
                println(best)
            }
            return RState.geode + RState.geodeRobots
        }
        if ((RState.geode + RState.geodeRobots * minutes + minutes) < best) {
            return RState.geode
        }
        var canBuyOre = false
        var canBuyClay = false
        var canBuyObsidian = false
        var canBuyGeode = false
        var stateChangesBefore = mutableListOf(RState)
        var stateChanges = mutableListOf<RState>()
        stateChanges.addAll(stateChangesBefore)
        stateChangesBefore.forEach {
            var lastState = it
            while (lastState.obsidian >= costGeode.obsidian && lastState.clay >= costGeode.clay && lastState.ore >= costGeode.ore) {
                // geode

                canBuyGeode = true
                lastState = lastState.copy(
                    geodeRobots = lastState.geodeRobots + 1,
                    obsidian = lastState.obsidian - costGeode.obsidian,
                    clay = lastState.clay - costGeode.clay,
                    ore = lastState.ore - costGeode.ore,
                )
                stateChanges.add(
                    lastState
                )
            }
        }

        if (canBuyGeode) {
            stateChanges.remove(RState)
            return stateChanges.maxOf {
                findBest(
                    minutes - 1,
                    it.copy(
                        ore = it.ore + RState.oreRobots,
                        clay = it.clay + RState.clayRobots,
                        obsidian = it.obsidian + RState.obsidianRobots,
                        geode = it.geode + RState.geodeRobots
                    )
                )
            }
        }


        stateChangesBefore.clear()
        stateChangesBefore.addAll(stateChanges)

        stateChangesBefore.forEach {
            var lastState = it
            while (lastState.obsidian >= costObsidian.obsidian && lastState.clay >= costObsidian.clay && lastState.ore >= costObsidian.ore) {
                // obsidian
                canBuyObsidian = true
                lastState = lastState.copy(
                    obsidianRobots = lastState.obsidianRobots + 1,
                    obsidian = lastState.obsidian - costObsidian.obsidian,
                    clay = lastState.clay - costObsidian.clay,
                    ore = lastState.ore - costObsidian.ore,
                )
                stateChanges.add(
                    lastState
                )
            }
        }

        stateChangesBefore.clear()
        stateChangesBefore.addAll(stateChanges)

        stateChangesBefore.forEach {
            var lastState = it
            while (lastState.obsidian >= costClay.obsidian && lastState.clay >= costClay.clay && lastState.ore >= costClay.ore) {
                // clay
                canBuyClay = true
                lastState = lastState.copy(
                    clayRobots = lastState.clayRobots + 1,
                    obsidian = lastState.obsidian - costClay.obsidian,
                    clay = lastState.clay - costClay.clay,
                    ore = lastState.ore - costClay.ore,
                )
                stateChanges.add(
                    lastState
                )
            }
        }


        stateChangesBefore.clear()
        stateChangesBefore.addAll(stateChanges)

        stateChangesBefore.forEach {

            var lastState = it
            while (lastState.obsidian >= costOre.obsidian && lastState.clay >= costOre.clay && lastState.ore >= costOre.ore) {
                // ore
                canBuyOre = true
                lastState = lastState.copy(
                    oreRobots = lastState.oreRobots + 1,
                    obsidian = lastState.obsidian - costOre.obsidian,
                    clay = lastState.clay - costOre.clay,
                    ore = lastState.ore - costOre.ore,
                )
                stateChanges.add(
                    lastState
                )
            }
        }
        if (canBuyOre && canBuyClay && canBuyObsidian && canBuyGeode) {
            stateChanges.remove(RState)
        } else if (canBuyClay && canBuyOre && RState.clayRobots == 0) {
            stateChanges.remove(RState)
        } else if (canBuyClay && canBuyOre && canBuyObsidian && RState.obsidianRobots == 0) {
            stateChanges.remove(RState)
        }

        return stateChanges.maxOf {
            findBest(
                minutes - 1,
                it.copy(
                    ore = it.ore + RState.oreRobots,
                    clay = it.clay + RState.clayRobots,
                    obsidian = it.obsidian + RState.obsidianRobots,
                    geode = it.geode + RState.geodeRobots
                )
            )
        }
    }
}

data class Price(val clay: Int = 0, val ore: Int = 0, val obsidian: Int = 0)

