package com.example.pokerscore

import com.example.pokerscore.card.Card
import com.example.pokerscore.card.Deck
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DeckTest {
    @Test
    fun `drawCard() should return a card`() {
        val deck = Deck()
        val card = deck.drawCard()
        assertTrue(card is Card)
    }

    @Test
    fun `drawCard() should remove the card from the deck`() {
        val deck = Deck()
        val card = deck.drawCard()
        assertFalse(card in deck.cards)
    }

    @Test
    fun `drawCard() should return a random card`() {
        val deck = Deck()
        val card1 = deck.drawCard()
        val card2 = deck.drawCard()
        assertNotEquals(card1, card2)
    }
}