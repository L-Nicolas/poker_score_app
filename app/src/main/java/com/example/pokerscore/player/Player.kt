package com.example.pokerscore.player

open class Player(
    open val name: String,
) {
    override fun toString(): String {
        return name
    }
}