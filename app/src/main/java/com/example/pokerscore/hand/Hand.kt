package com.example.pokerscore.hand

import com.example.pokerscore.card.Card

open class Hand {
    val cards: List<Card>
    open val size = 0

    constructor(cards: List<Card>) {
        this.cards = cards
    }

    fun revealCards() {
        cards.map { it.reveal() }
    }

    fun toStringCards(): String {
        return "[${cards.joinToString("-")}]"
    }
}