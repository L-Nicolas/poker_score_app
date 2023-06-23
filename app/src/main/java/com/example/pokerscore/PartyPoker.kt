package com.example.pokerscore

import com.example.pokerscore.board.InterfaceBoard

class PartyPoker(
    override val board: InterfaceBoard
) : Party() {

    companion object {
        private var instance: PartyPoker? = null

        fun getInstance(board: InterfaceBoard): PartyPoker {
            if (instance == null) {
                instance = PartyPoker(board)
            }
            return instance!!
        }
    }

    override fun join(name: String) {
        state.join(name)
    }

    override fun start() {
        state.start()
    }

    override fun end() {
        state.end()
    }
}
