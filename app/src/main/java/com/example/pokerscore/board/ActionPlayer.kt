package com.example.pokerscore.board

import com.example.pokerscore.player.StatePlayer

interface ActionPlayer {
    val board: BoardPoker

    fun action()
    fun print() {
        println("${this.javaClass.simpleName}")
    }

    fun execute() {
        action()
    }
}

class Bet(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Bet")
        board.getCurrentPlayer().haveToTalk = true
        board.getCurrentPlayer().bet(10)
    }
}

class Check(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Check")
        board.getCurrentPlayer().haveToTalk = false
    }
}

class Call(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Call")
        val currentPlayer = board.getCurrentPlayer()
        val currentPlayerBet = currentPlayer.getBet()
        var amountToCall = board.getBiggestBet() - currentPlayerBet
        if (amountToCall <= 0) {
            throw IllegalStateException("Le montant à miser doit être supérieur à 0.")
        }
        if (amountToCall > currentPlayer.getChipCount()) {
            currentPlayer.bet(amountToCall)
        } else {
            currentPlayer.bet(amountToCall)
            amountToCall = board.getBiggestBet()
        }
        board.getCurrentPlayer().haveToTalk = false
    }
}

class Fold(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        board.getCurrentPlayer().setState(StatePlayer.FOLD)
        board.getCurrentPlayer().haveToTalk = false
    }
}

class Raise(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Raise")
        board.getCurrentPlayer().haveToTalk = true
        board.getCurrentPlayer().bet(20)
    }
}

class AllIn(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("AllIn")
        board.getCurrentPlayer().bet(board.getCurrentPlayer().getChipCount())
        board.getCurrentPlayer().setState(StatePlayer.ALL_IN)
        board.getCurrentPlayer().haveToTalk = false
    }
}