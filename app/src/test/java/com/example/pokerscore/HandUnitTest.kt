package com.example.pokerscore

import com.example.pokerscore.card.Card
import com.example.pokerscore.card.CardPoker
import com.example.pokerscore.card.Figure
import com.example.pokerscore.card.Suit
import com.example.pokerscore.hand.Hand
import org.junit.Test

class HandUnitTest {
    @Test
    fun `cards should be reveald`() {
        val listCardsPlayer1 = listOf(
            CardPoker(Suit.CLUBS, Figure.QUEEN),
            CardPoker(Suit.HEARTS, Figure.QUEEN),
        )
        val hand = Hand(listCardsPlayer1)
        hand.revealCards()
        assert(hand.cards[0].isFaceUp())
        assert(hand.cards[1].isFaceUp())
    }

    @Test
    fun `should contain 2 cards`() {
        val listCardsPlayer1 = listOf(
            CardPoker(Suit.CLUBS, Figure.QUEEN),
            CardPoker(Suit.HEARTS, Figure.QUEEN),
        )
        val hand = Hand(listCardsPlayer1)
        assert(hand.cards.size == 2)
    }
}