package com.example.pokerscore.player

import com.example.pokerscore.hand.Hand
import com.example.pokerscore.hand.HandState

class PlayerInGame(
    override val name: String,
    private var chipCount: Int,
    private var bet: Int = 0,
    private var hand: Hand? = null,
    private var haveToTalk: Boolean = true,
    private var state: PlayerState = PlayerState.NONE,
    private var stateHand: HandState = HandState.NONE,
    private var roleOnBoard: RoleOnBoard = RoleOnBoard.NONE,
) : Player(name = name){

    fun haveToTalk(): Boolean {
        return haveToTalk
    }

    fun setHaveToTalk(haveToTalk: Boolean) {
        this.haveToTalk = haveToTalk
    }

    fun getBet(): Int {
        return bet
    }

    fun resetBet() {
        bet = 0
    }

    fun bet(amount: Int) {
        if(chipCount < amount){
            bet = chipCount
            chipCount = 0
        } else {
            bet += amount
            removeChips(amount)
        }
    }

    fun getChipCount(): Int {
        return chipCount
    }

    fun addChips(amount: Int) {
        chipCount += amount
    }

    fun removeChips(amount: Int) {
        chipCount -= amount
    }

    fun getRoleOnBoard(): RoleOnBoard {
        return roleOnBoard
    }

    fun changeRoleOnBoard(role: RoleOnBoard) {
        this.roleOnBoard = role
    }

    fun getState(): PlayerState {
        return state
    }

    fun setState(state: PlayerState) {
        this.state = state
    }

    fun getStateHand(): HandState {
        return stateHand
    }

    fun setStateHand(stateHand: HandState) {
        this.stateHand = stateHand
    }

    fun getHand(): Hand? {
        return hand
    }

    fun setHand(hand: Hand) {
        this.hand = hand
    }

    fun clearHand() {
        hand = null
    }

    fun toStringWithHand(): String {
        return "$state | $roleOnBoard : $name ($chipCount $) - ${hand?.toStringCards()} - : $bet $"
    }
}

enum class RoleOnBoard {
    NONE, // Aucun rôle
    DEALER, // Donneur
    SMALL_BLIND, // Petite blind
    BIG_BLIND // Grosse blind
}