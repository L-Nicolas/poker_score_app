package com.example.pokerscore.card

enum class Figure(val symbol: String) {
    TWO("2"), // Deux
    THREE("3"), // Trois
    FOUR("4"), // Quatre
    FIVE("5"), // Cinq
    SIX("6"), // Six
    SEVEN("7"), // Sept
    EIGHT("8"), // Huit
    NINE("9"), // Neuf
    TEN("10"), // Dix
    JACK("J"), // Valet
    QUEEN("Q"), // Dame
    KING("K"), // Roi
    ACE("A") // As
}
/*
sealed class Figure {
    object TWO : Figure("2")
    object THREE : Figure("3")
    object FOUR : Figure("4")
    object FIVE : Figure("5")
    object SIX : Figure("6")
    object SEVEN : Figure("7")
    object EIGHT : Figure("8")
    object NINE : Figure("9")
    object TEN : Figure("10")
    object JACK : Figure("J")
    object QUEEN : Figure("Q")
    object KING : Figure("K")
    object ACE : Figure("A")
}*/