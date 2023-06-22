package com.example.pokerscore.board

import com.example.pokerscore.card.Card
import com.example.pokerscore.player.Player
/*
class Turn {
}

inner class Tour(
    val players: List<Player>,
    val communityCards: List<String>
) {
    private val playerRoles: MutableMap<Player, Role> = mutableMapOf()

    init {
        assignRoles()
    }

    private fun assignRoles() {
        val roleOrder = mutableListOf<Role>()

        // Ajout des rôles dans l'ordre
        if (players.size >= 3) {
            roleOrder.add(Role.DEALER)
            roleOrder.add(Role.SMALL_BLIND)
            roleOrder.add(Role.BIG_BLIND)
        }

        // Attribuer les rôles aux joueurs de manière circulaire
        for (i in players.indices) {
            val player = players[i]
            val role = roleOrder[i % roleOrder.size]
            playerRoles[player] = role
        }
    }

    fun getPlayerNames(): List<String> {
        return players.map { it.name }
    }

    fun getPlayerRoles(): Map<String, Role> {
        return playerRoles.mapKeys { it.key.name }
    }
}*/
/*
class Turn {
    val players: MutableList<Player> = mutableListOf()
    private var currentPlayerIndex: Int = 0
    private var currentRole: Role = Role.DEALER

    //private val tours: MutableList<Party.Tour> = mutableListOf()
    private var currentTourIndex: Int = 0

    private var potAmount: Int = 0

    private var communityCards: MutableList<Card> = mutableListOf()

    private var state: Board.BoardState = Board.BoardState.UPCOMING

    init {
    }

    fun fillBoard() {
        val deck = Deck()
        val card = deck.drawCard()
        communityCards.add(card)
    }
}*/