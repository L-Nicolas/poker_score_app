package com.example.pokerscore.board

import com.example.pokerscore.player.RoleOnBoard

interface BoardState {
    val board: BoardPoker

    fun action()
    fun printState() {
        println("BoardState : ${this.javaClass.simpleName}")
    }
}

class InitialState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.reset()
        board.attributRole()
        val bigBlindIndex = board.players.indexOfFirst { it.getRoleOnBoard() == RoleOnBoard.BIG_BLIND }
        val nextPlayerIndex = (bigBlindIndex + 1) % board.players.size
        val nextPlayer = board.players[nextPlayerIndex]
        if (nextPlayer != null) {
            board.setCurrentPlayer(nextPlayer)
        }
        board.firstDeal()
        board.turnLoop()
        board.fillPot()
        board.changeState(FirstState(board))
    }
}

class FirstState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.reset()
        board.attributRole()
        val bigBlindIndex = board.players.indexOfFirst { it.getRoleOnBoard() == RoleOnBoard.BIG_BLIND }
        val nextPlayerIndex = (bigBlindIndex + 1) % board.players.size
        val nextPlayer = board.players[nextPlayerIndex]
        if (nextPlayer != null) {
            board.setCurrentPlayer(nextPlayer)
        }
        board.firstDeal()
        board.turnLoop()
        board.fillPot()
        board.changeState(FlopState(board))
    }
}

class FlopState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(3)
        board.resetStatePlayer()
        board.allPlayerHaveToTalk()
        board.turnLoop()
        board.fillPot()
        board.changeState(RiverTurnState(board))
    }
}

class RiverTurnState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(1)
        board.resetStatePlayer()
        board.allPlayerHaveToTalk()
        board.turnLoop()
        board.fillPot()
        board.changeState(TurnState(board))
    }
}

class TurnState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(1)
        board.resetStatePlayer()
        board.allPlayerHaveToTalk()
        board.turnLoop()
        board.fillPot()
        board.changeState(EndState(board))
    }
}

class EndState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.revealCards()
        board.score.calculatePokerGains(board)
        board.printBoard()
        print("fin de la main")
        //board.changeState(FirstState(board))
    }
}