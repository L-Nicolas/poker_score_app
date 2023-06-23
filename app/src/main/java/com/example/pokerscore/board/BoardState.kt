package com.example.pokerscore.board

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
        val nextPlayer = board.findFirstPlayer()
        if (nextPlayer != null) {
            board.setCurrentPlayer(nextPlayer)
        }
        board.firstDeal()
        board.turnLoop()
        board.changeState(FlopState(board))
    }
}

class FirstState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.reset()
        board.firstDeal()
        board.turnLoop()
        board.changeState(FlopState(board))
    }
}

class FlopState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(3)
        board.resetStatePlayer()
        board.turnLoop()
        board.changeState(RiverTurnState(board))
    }
}

class RiverTurnState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(1)
        board.resetStatePlayer()
        board.turnLoop()
        board.changeState(TurnState(board))
    }
}

class TurnState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.fillBoard(1)
        board.resetStatePlayer()
        board.turnLoop()
        board.changeState(EndState(board))
    }
}

class EndState(override val board: BoardPoker) : BoardState {
    override fun action() {
        board.revealCards()
        board.score.calculatePokerGains(board)
        //board.printBoard()
        print("fin de la main")
        board.changeState(FirstState(board))
    }
}