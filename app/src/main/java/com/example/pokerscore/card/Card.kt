package com.example.pokerscore.card

open class Card {
    val suit: Suit
    val figure: Figure
    private var faceUp = false

    constructor(suit: Suit, figure: Figure) {
        this.suit = suit
        this.figure = figure
    }

    fun isFaceUp(): Boolean {
        return faceUp
    }

    fun flip() {
        faceUp = !faceUp
    }

    fun reveal() {
        faceUp = true
    }

    open fun getValue(): Int {
        return figure.ordinal + 2
    }

    override fun toString(): String {
        if(!faceUp) return "\uD83C\uDCA0"
        return "${figure.symbol}${suit.symbol}"
    }
}