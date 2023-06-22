package com.example.pokerscore.card

data class Deck(val cards: MutableList<Card> = mutableListOf()) {
    init {
        fill()
    }

    private fun fill() {
        val suits = Suit.values().map { it.name }
        val values = Figure.values().map { it.name }
        suits.forEach { suit ->
            values.forEach { value ->
                cards.add(Card(Suit.valueOf(suit), Figure.valueOf(value)))
            }
        }
    }

    fun drawCard(): Card {
        val card = cards.random()
        cards.remove(card)
        return card
    }

    fun suffle() {
        cards.shuffle()
    }

    fun reset() {
        cards.clear()
        fill()
    }
}