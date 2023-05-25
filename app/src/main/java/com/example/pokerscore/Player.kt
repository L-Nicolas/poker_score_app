package com.example.pokerscore

data class Player(
    val name: String,
    val chipCount: Int,
    val cards: List<String>
) {
    fun addChips(amount: Int): Player {
        return copy(chipCount = chipCount + amount)
    }
}
