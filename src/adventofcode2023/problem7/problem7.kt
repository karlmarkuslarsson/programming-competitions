package adventofcode2023.problem7

fun main() {
    solveA()
    solveB()
}

fun solveA() {
    fun Char.cardValue(): Int {
        return when (this) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            else -> this.toString().toInt()
        }

    }

    val cards = input7.split("\n").map { line ->
        val split = line.split(" ")
        val card = split[0]
        val bid = split[1].toInt()
        Card(card, bid)
    }
    val sortedCards = cards.sortedWith<Card>(object : Comparator<Card> {
        override fun compare(c1: Card, c2: Card): Int {
            if (c1.score() > c2.score()) {
                return 1
            }
            if (c1.score() == c2.score()) {
                c1.card.forEachIndexed { index, c ->
                    if (c.cardValue() > c2.card[index].cardValue()) {
                        return 1
                    }
                    if (c.cardValue() < c2.card[index].cardValue()) {
                        return -1
                    }
                }
                return 0
            }
            return -1
        }
    })
    var index = 0
    println(sortedCards.sumOf {
        index++
        it.bid * (index)
    })
}

data class Card(
    val card: String,
    val bid: Int
) {
    fun score(): Int {
        val aces = card.count { it == 'A' }
        val kings = card.count { it == 'K' }
        val queens = card.count { it == 'Q' }
        val jacks = card.count { it == 'J' }
        val tens = card.count { it == 'T' }
        val nines = card.count { it == '9' }
        val eights = card.count { it == '8' }
        val sevens = card.count { it == '7' }
        val sixes = card.count { it == '6' }
        val fives = card.count { it == '5' }
        val fours = card.count { it == '4' }
        val threes = card.count { it == '3' }
        val twos = card.count { it == '2' }
        val listOfAll = listOf(
            aces,
            kings,
            queens,
            jacks,
            tens,
            nines,
            eights,
            sevens,
            sixes,
            fives,
            fours,
            threes,
            twos
        )
        if (listOfAll.any { it == 5 }) {
            return 1000
        }
        if (listOfAll.any { it == 4 }) {
            return 900
        }
        if (listOfAll.any { it == 3 } && listOfAll.any { it == 2 }) {
            return 800
        }
        if (listOfAll.any { it == 3 }) {
            return 700
        }
        if (listOfAll.filter { it == 2 }.size == 2) {
            return 600
        }
        if (listOfAll.any { it == 2 }) {
            return 500
        }
        return 400
    }

    fun score2(): Int {
        val aces = card.count { it == 'A' }
        val kings = card.count { it == 'K' }
        val queens = card.count { it == 'Q' }
        val jacks = card.count { it == 'J' }
        val tens = card.count { it == 'T' }
        val nines = card.count { it == '9' }
        val eights = card.count { it == '8' }
        val sevens = card.count { it == '7' }
        val sixes = card.count { it == '6' }
        val fives = card.count { it == '5' }
        val fours = card.count { it == '4' }
        val threes = card.count { it == '3' }
        val twos = card.count { it == '2' }
        val listOfAll = listOf(
            aces,
            kings,
            queens,
            tens,
            nines,
            eights,
            sevens,
            sixes,
            fives,
            fours,
            threes,
            twos
        )
        if (jacks == 5) {
            return 1000
        }
        if (listOfAll.any { it + jacks >= 5 }) {
            return 1000
        }
        if (listOfAll.any { it + jacks >= 4 }) {
            return 900
        }
        if (listOfAll.filter { it == 2 }.size == 2 && jacks == 1) {
            return 800
        }
        if (listOfAll.any { it == 3 } && listOfAll.any { it == 2 }) {
            return 800
        }
        if (listOfAll.any { it + jacks >= 3 }) {
            return 700
        }
        if (listOfAll.filter { it == 2 }.size == 2) {
            return 600
        }
        if (listOfAll.any { it + jacks >= 2 }) {
            return 500
        }
        return 400
    }
}

fun solveB() {

    fun Char.cardValue(): Int {
        return when (this) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> this.toString().toInt()
        }

    }

    val cards = input7.split("\n").map { line ->
        val split = line.split(" ")
        val card = split[0]
        val bid = split[1].toInt()
        Card(card, bid)
    }
    val sortedCards = cards.sortedWith<Card>(object : Comparator<Card> {
        override fun compare(c1: Card, c2: Card): Int {
            if (c1.score2() > c2.score2()) {
                return 1
            }
            if (c1.score2() == c2.score2()) {
                c1.card.forEachIndexed { index, c ->
                    if (c.cardValue() > c2.card[index].cardValue()) {
                        return 1
                    }
                    if (c.cardValue() < c2.card[index].cardValue()) {
                        return -1
                    }
                }
                return 0
            }
            return -1
        }
    })
    var index = 0
    println(sortedCards.sumOf {
        index++
        it.bid * (index)
    })
}

