package com.example.pokerscore

import com.example.pokerscore.board.BoardPoker
import com.example.pokerscore.board.CommunityCards
import com.example.pokerscore.card.Deck

class App {
    fun main() {
        val playersName = listOf("Player 1","Player 2","Player 3")

        val deck = Deck()
        val communityCards = CommunityCards()
        val boardPoker = BoardPoker.builder(deck, communityCards)
            .withSmallBlind(10)
            .withBigBlind(20)
            .build()

        val party = PartyPoker(boardPoker)

        playersName.forEach { party.join(it) }
        party.start()
    }
}

fun main() {
    App().main()
}