package com.example.pokerscore.board

import com.example.pokerscore.card.Deck
import com.example.pokerscore.card.Card
import com.example.pokerscore.hand.HandPoker
import com.example.pokerscore.hand.HandState
import com.example.pokerscore.player.PlayerInGame
import com.example.pokerscore.player.PlayerState
import com.example.pokerscore.player.RoleOnBoard

class BoardPoker private constructor(val deck: Deck, val communityCards: CommunityCards) : InterfaceBoard {
    val MIN_PLAYER_COUNT = 2
    val MAX_PLAYER_COUNT = 6
    override val players: MutableList<PlayerInGame> = mutableListOf()

    var smallBlind: Int = 10
    var bigBlind: Int = 20
    val score: PokerScore = PokerScore()

    private val trashCards = mutableListOf<Card>()

    var pot: Int = 0
    private var currentIndexPlayer: Int = 0
    private var turnState: BoardState = InitialState(this)

    class Builder(private val deck: Deck, private val communityCards: CommunityCards) {
        private val boardPoker: BoardPoker = BoardPoker(deck, communityCards)

        fun withSmallBlind(smallBlind: Int): Builder {
            boardPoker.smallBlind = smallBlind
            return this
        }

        fun withBigBlind(bigBlind: Int): Builder {
            boardPoker.bigBlind = bigBlind
            return this
        }

        fun build(): BoardPoker {
            return boardPoker
        }
    }

    companion object {
        fun builder(deck: Deck, communityCards: CommunityCards): Builder {
            return Builder(deck, communityCards)
        }
    }

    override fun start() {
        if(players.size < MIN_PLAYER_COUNT) {
            throw IllegalStateException("Le nombre de joueurs doit être supérieur ou égal à $MIN_PLAYER_COUNT.")
        }
        loopGame()
        end()
    }

    override fun end() {
        // TODO
        println("Fin de la partie")
    }

    private fun loopGame() {
        while(conditionToContinueGame()){
            loopBoard()
            println("conditionToContinueGame : ${conditionToContinueGame()}")
        }
        //vérifier
        turnState = EndState(this)
    }

    private fun loopBoard() {
        while (conditionToContinueTurnBoard()) {
            turnState.action()
            println("conditionToContinueTurnBoard : ${conditionToContinueTurnBoard()}")
        }
    }

    override fun addPlayer(player: PlayerInGame) {
        if (players.size >= MAX_PLAYER_COUNT) {
            throw IllegalStateException("Le nombre de joueurs ne peut pas dépasser $MAX_PLAYER_COUNT.")
        }
        players.add(player)
    }

    override fun reset() {
        deck.reset()
        players.map { player ->
            player.clearHand()
            player.setState(PlayerState.NONE)
        }
        communityCards.clear()
        trashCards.clear()
        resetPotAmount()
    }

    fun fillBoard(number: Int) {
        cardToTrash(deck.drawCard())
        for (i in 0 until number) {
            val card = deck.drawCard()
            card.flip()
            communityCards.addCard(card)
        }
    }

    fun fillPot() {
        val amount = getAllBet()
        pot += amount
        resetAllBet()
    }

    private fun resetAllBet() {
        players.map { player ->
            player.resetBet()
        }
    }

    private fun getAllBet(): Int {
        return players.sumBy { it.getBet() }
    }

    private fun cardToTrash(card: Card) {
        trashCards.add(card)
    }

    fun printBoard() {
        println()
        println("---------------------------")
        println("---------- Board ----------")
        println("---------------------------")
        printTrashCards()
        communityCards.print()
        printPotAmount()
        printPlayers()
        turnState.printState()
    }
    fun findFirstPlayer(): PlayerInGame {
        val bigBlindIndex = players.indexOfFirst { it.getRoleOnBoard() == RoleOnBoard.BIG_BLIND }
        val nextPlayerIndex = (bigBlindIndex + 1) % players.size
        val nextPlayer = players[nextPlayerIndex]
        if (nextPlayer != null) {
            setCurrentPlayer(nextPlayer)
        }
        return nextPlayer
    }

    fun setCurrentPlayer(player: PlayerInGame) {
        currentIndexPlayer = players.indexOf(player)
    }

    private fun changeCurrentPlayer() {
        currentIndexPlayer = (currentIndexPlayer + 1) % players.size
    }

    fun getCurrentPlayer(): PlayerInGame {
        return players[currentIndexPlayer]
    }

    private fun printPotAmount() {
        println("Pot : ${getPotAmount()}")
    }

    private fun printPlayers() {
        println("Player :")
        players.forEach { player ->
            println(player.toStringWithHand())
        }
    }

    private fun printTrashCards() {
        println("Trash cards :")
        trashCards.forEach { card ->
            print(card)
        }
        println()
    }

    private fun waitingInputChooseAction(): String {
        val input = readLine()
        return input ?: ""
    }

    fun getPotAmount(): Int {
        return pot
    }

    private fun resetPotAmount() {
        pot = 0
    }

    fun turnLoop() {
        while (conditionToContinueTurnPlayer()) {
            printBoard()
            determineStateHandCurrentPlayer()
            val player = getCurrentPlayer()
            if (player.getState() != PlayerState.FOLD && player.getState() != PlayerState.ALL_IN) {
                if (isLastPlayerOfTurn()) {
                    players[currentIndexPlayer].setHaveToTalk(false)
                    fillPot()
                } else {
                    println("${player.name}, choisissez une action :")
                    actionPossible().forEach { action ->
                        action.print()
                    }
                    val action = waitingInputChooseAction()
                    actionPossible()[action.toInt()].execute()
                }
            } else {
                players[currentIndexPlayer].setHaveToTalk(false)
            }
            changeCurrentPlayer()
        }
        fillPot()
    }

    private fun isLastPlayerOfTurn(): Boolean {
        return players.filter { player -> player.getState() != PlayerState.FOLD }.size == 1
    }

    private fun isLastPlayerOnBoard(): Boolean {
        return players.filter { player -> player.getState() != PlayerState.FOLD }.size == 1
    }

    fun getPlayerInGame(): List<PlayerInGame> {
        return players.filter { player -> player.getState() != PlayerState.FOLD }
    }

    fun getPlayerThatCanTalk(): List<PlayerInGame> {
        return players.filter { player -> player.getState() != PlayerState.FOLD && player.getState() != PlayerState.ALL_IN }
    }

    fun revealCards() {
        getPlayerInGame().map { player -> player.getHand()?.revealCards() }
    }

    fun resetStatePlayer() {
        getPlayerThatCanTalk().map { player -> player.setState(PlayerState.NONE) }
        allPlayerHaveToTalk()
    }

    fun allPlayerHaveToTalk() {
        getPlayerThatCanTalk().map { player -> player.setHaveToTalk(true) }
    }

    private fun determineStateHandCurrentPlayer() {
        val currentPlayer = getCurrentPlayer()
        val currentPlayerBet = currentPlayer.getBet()
        if (isPlayerBiggestBet(currentPlayer) && currentPlayerBet > 0) {
            currentPlayer.setStateHand(HandState.BIGGESTBET)
        } else if (isPlayerNotBiggestBet(currentPlayer) && currentPlayerBet >= 0){
            currentPlayer.setStateHand(HandState.NOTBIGGESTBET)
        } else if (isPlayerNotBiggestBet(currentPlayer) && currentPlayerBet == 0 && !players.any{ player -> player.getRoleOnBoard() != RoleOnBoard.BIG_BLIND && player.getRoleOnBoard() != RoleOnBoard.SMALL_BLIND && player.getBet() > 0}) {
            currentPlayer.setStateHand(HandState.FIRSTTOBET)
        } else {
            currentPlayer.setStateHand(HandState.FIRSTTOBET)
        }
    }

    private fun isPlayerNotBiggestBet(player: PlayerInGame): Boolean {
        val biggestBet = getBiggestBet()
        val playerBet = player.getBet()
        return playerBet < biggestBet
    }

    private fun isPlayerBiggestBet(player: PlayerInGame): Boolean {
        val biggestBet = getBiggestBet()
        val playerBet = player.getBet()
        return playerBet == biggestBet
    }

    fun getBiggestBet(): Int {
        var biggestBet = 0
        players.forEach { player ->
            val playerBet = player.getBet()
            if (playerBet > biggestBet) {
                biggestBet = playerBet
            }
        }
        return biggestBet
    }

    private fun actionPossible(): List<ActionPlayer> {
        val actions = mutableListOf<ActionPlayer>()
        val currentPlayer = getCurrentPlayer()
        val statePlayer = currentPlayer.getState()
        val stateHand = currentPlayer.getStateHand()

        println("State player : $statePlayer")

        if(statePlayer == PlayerState.ALL_IN || statePlayer == PlayerState.FOLD) {
            return actions
        } else {
            when (stateHand) {
                HandState.FIRSTTOBET -> {
                    actions.add(Check(this))
                    actions.add(Bet(this))
                    actions.add(Raise(this))
                    actions.add(AllIn(this))
                    actions.add(Fold(this))
                    return actions
                }
                HandState.BIGGESTBET -> {
                    actions.add(Check(this))
                    actions.add(Fold(this))
                    return actions
                }
                HandState.NOTBIGGESTBET -> {
                    actions.add(Call(this))
                    actions.add(Raise(this))
                    actions.add(AllIn(this))
                    actions.add(Fold(this))
                    return actions
                }
                else -> {
                    throw IllegalStateException("Le joueur n'est pas dans un état de main valide.")}
            }
        }
        return actions
    }

    private fun conditionToContinueGame(): Boolean {
        return players.filter { player -> player.getChipCount() > 0 }.size > 1
    }

    private fun conditionToContinueTurnBoard(): Boolean {
        return players.filter { player -> player.getState() != PlayerState.FOLD }.size > 1
    }

    private fun conditionToContinueTurnPlayer(): Boolean {
        return players.any { player -> player.haveToTalk() /*&& player.getState() != StatePlayer.FOLD*/ } /*|| ( players.filter { player -> player.getState() != StatePlayer.FOLD }.size > 1 && turnState.javaClass != FirstState::class.java)*/
    }

    fun firstDeal() {
        players.forEach { player ->
            val listCards = mutableListOf<Card>()
            for (i in 0 until 2) {
                val card = deck.drawCard()
                listCards.add(card)
            }
            player.setHand(HandPoker(listCards))
        }
    }

    fun changeState(state: BoardState) {
        this.turnState = state
    }

    fun attributRole() {
        attributDealer()
        attributSmallBlind()
        attributBigBlind()
    }

    private fun attributDealer() {
        players.random().changeRoleOnBoard(RoleOnBoard.DEALER)
    }

    private fun attributSmallBlind() {
        val dealer = players.find { player -> player.getRoleOnBoard() == RoleOnBoard.DEALER }
        val indexDealer = players.indexOf(dealer)
        val indexSmallBlind = if (indexDealer == players.size - 1) 0 else indexDealer + 1
        players[indexSmallBlind].changeRoleOnBoard(RoleOnBoard.SMALL_BLIND)
        players[indexSmallBlind].bet(smallBlind)
    }

    private fun attributBigBlind() {
        val dealer = players.find { player -> player.getRoleOnBoard() == RoleOnBoard.DEALER }
        val indexDealer = players.indexOf(dealer)
        val indexBigBlind = if (indexDealer >= players.size - 2) 0 else indexDealer + 2
        players[indexBigBlind].changeRoleOnBoard(RoleOnBoard.BIG_BLIND)
        players[indexBigBlind].bet(bigBlind)
    }
}