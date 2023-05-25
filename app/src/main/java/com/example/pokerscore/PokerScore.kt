package com.example.pokerscore


class PokerScore {
    fun calculatePokerGains(
        players: List<Player>,
        potAmount: Int,
        communityCards: List<String>
    ): Map<String, Int> {
        val playerGains = mutableMapOf<String, Int>()

        // Classement des combinaisons de cartes pour chaque joueur
        val playerHands = players.associate { player ->
            player to evaluateHand(player.cards + communityCards)
        }

        // Recherche de la meilleure main parmie tous les joueurs
        val bestHand = playerHands.values.max()

        // Vérification des gagnants avec la meilleure main
        val winners = playerHands.filter { it.value == bestHand }
        val numWinners = winners.size

        // Repartition des gains aux joueurs
        if (numWinners == 1) {
            val winner = winners.keys.first()
            //val winnings = potAmount / numWinners
            val winnings = winner.chipCount * players.size - 1
            playerGains[winner.name] = winnings
        } else {
            val sortedPlayers = players.sortedByDescending { it.chipCount }
            var remainingPot = potAmount
            for (player in sortedPlayers) {
                val winnings = if (player in winners) {
                    remainingPot / numWinners
                } else {
                    0
                }
                playerGains[player.name] = winnings
                remainingPot -= winnings
            }
        }

        return playerGains
    }


    fun evaluateHand(cards: List<String>): Hand {
        val suits = cards.map { it.last() }
        val ranks = cards.map { it.dropLast(1) }.map { parseRank(it) }.sortedDescending()

        val isFlush = this.isFlush(suits)
        val isStraight = this.isStraight(ranks)

        val groups = ranks.groupBy { rank -> rank }
        val maxGroupSize = groups.values.map { it.size }.maxOrNull() ?: 0
        val numMaxGroups = groups.values.count { it.size == maxGroupSize }

        val hasThreeOfAKind = this.hasThreeOfAKind(groups)
        val hasPair = this.hasPair(groups)

        return when {
            isFlush && isStraight && ranks[2] == 12 -> Hand.RoyalFlush
            isFlush && isStraight -> Hand.StraightFlush
            maxGroupSize == 4 -> Hand.FourOfAKind
            hasThreeOfAKind && hasPair -> Hand.FullHouse
            isFlush -> Hand.Flush
            isStraight -> Hand.Straight
            hasThreeOfAKind -> Hand.ThreeOfAKind
            numMaxGroups == 2 && maxGroupSize == 2 -> Hand.TwoPair
            hasPair -> Hand.Pair
            else -> Hand.None
        }

    }

    fun isFlush(cards: List<Char>): Boolean {
        val groupedBySuit = cards.groupBy { it }
        val flush = groupedBySuit.values.any { it.size >= 5 }
        return flush
    }

    fun isStraight(cards: List<Int>): Boolean {
        val uniqueCards = cards.distinct().sorted()
        val consecutiveCount = uniqueCards.zipWithNext().count { (prev, next) -> next - prev == 1 }
        return consecutiveCount >= 4 || uniqueCards == listOf(2, 3, 4, 5, 14)
    }

    /*fun hasTwoPair(numbers: List<Int>): Boolean {
        val distinctNumbers = numbers.distinct()
        val pairs = distinctNumbers.filter { number -> numbers.count { it == number } == 2 }
        val numPairs = pairs.size
        return numPairs >= 2
    }*/

    fun hasThreeOfAKind(groups: Map<Int, List<Int>>): Boolean {
        return groups.any { it.value.size == 3 }
    }

    fun hasPair(groups: Map<Int, List<Int>>): Boolean {
        return groups.any { it.value.size == 2 }
    }

    // Récupération de la valeur de la carte
    fun parseRank(rankString: String): Int {
        return when (rankString) {
            "A" -> 14
            "K" -> 13
            "Q" -> 12
            "J" -> 11
            else -> rankString.toIntOrNull() ?: 0
        }
    }


    // Enum pour représenter les différentes combinaisons de mains possibles
    enum class Hand {
        None, // Aucune combinaison
        Pair, // Paire
        TwoPair, // Deux paires
        ThreeOfAKind, // Brelan
        Straight, // Quinte
        Flush, // Couleur
        FullHouse, // Full
        FourOfAKind, // Carré
        StraightFlush, // Quinte flush
        RoyalFlush // Quinte flush royale
    }
}