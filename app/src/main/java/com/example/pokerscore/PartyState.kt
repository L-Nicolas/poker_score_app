package com.example.pokerscore

import com.example.pokerscore.player.PlayerInGame

interface PartyState {
    val party: Party

    fun start()
    fun end()
    fun join(name: String)
}

class UpcomingPartyState(override val party: Party) : PartyState {
    override fun start() {
        party.board.start()
        party.setState(OngoingPartyState(party))
    }

    override fun end() {
        throw IllegalStateException("La partie n'est pas commencée.")
    }

    override fun join(name: String) {
        party.board.addPlayer(PlayerInGame(name, party.entryChipCount))
    }
}

class OngoingPartyState(override val party: Party) : PartyState {
    override fun start() {
        throw IllegalStateException("La partie est déjà commencée.")
    }

    override fun end() {
        party.board.end()
    }

    override fun join(name: String) {
        throw IllegalStateException("La partie est déjà commencée.")
    }
}

class EndedPartyState(override val party: Party) : PartyState {
    override fun start() {
        throw IllegalStateException("La partie est déjà terminée.")
    }

    override fun end() {
        throw IllegalStateException("La partie est déjà terminée.")
    }

    override fun join(name: String) {
        throw IllegalStateException("La partie est déjà terminée.")
    }
}