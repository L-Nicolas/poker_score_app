package com.example.pokerscore.player

class PlayerHistory(
    override val name: String,
    val score: Int,
) : Player(name = name) {
    override fun toString(): String {
        return "$name: $score"
    }
}