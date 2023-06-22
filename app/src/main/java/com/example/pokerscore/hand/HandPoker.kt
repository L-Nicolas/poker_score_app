package com.example.pokerscore.hand

import com.example.pokerscore.card.Card

class HandPoker(cards: List<Card>) : Hand(cards) {
    override val size = 2
}