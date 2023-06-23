package com.example.pokerscore.board

import com.example.pokerscore.player.PlayerState

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
        board.getCurrentPlayer().setHaveToTalk(true)
        board.getCurrentPlayer().bet(10)
    }
}

class Check(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Check")
        board.getCurrentPlayer().setHaveToTalk(false)
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
        board.getCurrentPlayer().setHaveToTalk(false)
    }
}

class Fold(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        board.getCurrentPlayer().setState(PlayerState.FOLD)
        board.getCurrentPlayer().setHaveToTalk(false)
    }
}

class Raise(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("Raise")
        board.getCurrentPlayer().setHaveToTalk(true)
        board.getCurrentPlayer().bet(20)
    }
}

class AllIn(override val board: BoardPoker) : ActionPlayer {
    override fun action() {
        println("AllIn")
        board.getCurrentPlayer().bet(board.getCurrentPlayer().getChipCount())
        board.getCurrentPlayer().setState(PlayerState.ALL_IN)
        board.getCurrentPlayer().setHaveToTalk(false)
    }
}

class PlayerActionInvoker {
    private val actions: MutableList<ActionPlayer> = mutableListOf()

    fun addAction(action: ActionPlayer) {
        actions.add(action)
    }

    fun clearActions() {
        actions.clear()
    }

    fun executeActions() {
        actions.forEach { command ->
            command.execute()
        }
        actions.clear()
    }
}