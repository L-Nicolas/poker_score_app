package com.example.pokerscore

import com.example.pokerscore.board.InterfaceBoard

class Party(
    val board: InterfaceBoard,
    val entryChipCount: Int = 1000,
) {
    private var state: PartyState = UpcomingPartyState(this)

    fun join(name: String) {
        state.join(name)
    }

    fun start() {
        state.start()
    }

    fun end() {
        state.end()
    }

    fun getState(): PartyState {
        return state
    }

    fun setState(state: PartyState) {
        this.state = state
    }

}