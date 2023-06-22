package com.example.pokerscore.board

import com.example.pokerscore.player.PlayerInGame

interface InterfaceBoard {
    fun start()
    fun end()
    fun addPlayer(player: PlayerInGame)
    fun reset()
}