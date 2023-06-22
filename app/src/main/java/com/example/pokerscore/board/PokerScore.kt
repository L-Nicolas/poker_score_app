package com.example.pokerscore.board

import com.example.pokerscore.card.Card
import com.example.pokerscore.card.Suit
import com.example.pokerscore.player.PlayerInGame

class PokerScore {
    fun calculatePokerGains(
        board: BoardPoker
    ) {
        val sortedPlayers = sortPlayerByHand(board.getPlayerInGame(), board.communityCards.cards)
        spreadPokerGains(board.getPotAmount(), sortedPlayers)
        println("sortedPlayers : ")
        sortedPlayers.forEach(::println)
    }

    private fun sortPlayerByHand(
        players: List<PlayerInGame>,
        communityCards: List<Card>
    ): List<Map.Entry<PlayerInGame, List<Card>>> {
        val playerHands = players.associate { player ->
            player to getBestFiveCards(player.getHand()?.cards?.plus(communityCards) ?: listOf())
        }
        val result = playerHands.entries.sortedWith(compareByDescending<Map.Entry<PlayerInGame, List<Card>>> { (_, hand) ->
            evaluateCombinaison(hand)
        }.thenByDescending { (_, hand) ->
            getValueOfHand(hand)
        })
        return result
    }


    private fun spreadPokerGains(
        potAmount: Int,
        players: List<Map.Entry<PlayerInGame, List<Card>>>,
    ) {
        val sortedPlayers = players.sortedBy { it.key.name }
        var remainingPotAmount = potAmount

        sortedPlayers.forEach { player ->
            var gain = 0
            if (remainingPotAmount > 0) {
                val maxWinnings = player.key.getChipCount() * players.size
                gain = minOf(maxWinnings, remainingPotAmount)
            }
            player.key.addChips(gain)
            remainingPotAmount -= gain
        }
    }


    private fun getBestFiveCards(cards: List<Card>): List<Card> {
        val combinations = cards.combinations(5)
        val bestCombination = combinations.maxByOrNull { evaluateCombinaison(it) } ?: emptyList()
        val bestHandCategory = evaluateCombinaison(bestCombination)
        val newcomb = combinations.filter { it -> evaluateCombinaison(it) == bestHandCategory }
        val bestValue = newcomb.maxOf { it -> getValueOfHand(it) }
        val bestCombinationWithBestValue = newcomb.filter { evaluateCombinaison(it) == bestHandCategory && getValueOfHand(it) == bestValue }
            .maxByOrNull { getValueOfHand(it) } ?: emptyList()
        return bestCombinationWithBestValue
    }

    private fun <T> List<T>.combinations(size: Int): List<List<T>> {
        if (size == 0 || size > this.size) return emptyList()
        if (size == 1) return this.map { listOf(it) }

        val combinations = mutableListOf<List<T>>()
        for (i in 0..this.size - size) {
            val element = this[i]
            val subCombinations = this.subList(i + 1, this.size).combinations(size - 1)
            combinations.addAll(subCombinations.map { listOf(element) + it })
        }
        return combinations
    }


    private fun evaluateCombinaison(cards: List<Card>): Hand {
        val suits = cards.map { it.suit }
        val ranks = cards.map { it.getValue() }.sortedDescending()

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

    private fun isFlush(cards: List<Suit>): Boolean {
        val groupedBySuit = cards.groupBy { it }
        val flush = groupedBySuit.values.any { it.size >= 5 }
        return flush
    }

    private fun isStraight(cards: List<Int>): Boolean {
        val uniqueCards = cards.distinct().sorted()
        val consecutiveCount = uniqueCards.zipWithNext().count { (prev, next) -> next - prev == 1 }
        return consecutiveCount == 5 || uniqueCards == listOf(2, 3, 4, 5, 14)
    }

    /*fun hasTwoPair(numbers: List<Int>): Boolean {
        val distinctNumbers = numbers.distinct()
        val pairs = distinctNumbers.filter { number -> numbers.count { it == number } == 2 }
        val numPairs = pairs.size
        return numPairs >= 2
    }*/

    private fun hasThreeOfAKind(groups: Map<Int, List<Int>>): Boolean {
        return groups.any { it.value.size == 3 }
    }

    private fun hasPair(groups: Map<Int, List<Int>>): Boolean {
        return groups.any { it.value.size == 2 }
    }

    private fun getValueOfHand(cards: List<Card>) : Int {
        return cards.map { card -> card.getValue()
        }.sum()
    }
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