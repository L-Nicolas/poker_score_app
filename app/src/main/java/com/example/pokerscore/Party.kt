package com.example.pokerscore

import com.example.pokerscore.board.InterfaceBoard

abstract class Party {
    abstract val board: InterfaceBoard
    var state: PartyState = UpcomingPartyState(this)

    abstract fun join(name: String)
    abstract fun start()
    abstract fun end()
}