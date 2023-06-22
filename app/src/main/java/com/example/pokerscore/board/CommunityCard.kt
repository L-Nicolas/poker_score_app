package com.example.pokerscore.board

import com.example.pokerscore.card.Card
import com.example.pokerscore.card.CardPoker

class CommunityCards {
    private val MAX_CARD_COUNT = 5
    var cards: MutableList<Card> = mutableListOf()

    fun addCard(card: Card) {
        if (cards.size == MAX_CARD_COUNT) {
            throw IllegalStateException("Le nombre maximum de cartes est atteint.")
        }
        cards.add(card)
    }

    fun clear() {
        cards.clear()
    }

    fun print() {
        return cards.forEach { card ->
            println(card)
        }
    }
}