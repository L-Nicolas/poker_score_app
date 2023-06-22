package com.example.pokerscore.domaine.repository

import com.example.pokerscore.player.Player
import android.content.Context
import android.content.SharedPreferences
import com.example.pokerscore.player.PlayerHistory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlayerRepository(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val gson: Gson = Gson()

    init {
        sharedPreferences = context.getSharedPreferences("PlayerRepository", Context.MODE_PRIVATE)
    }

    fun getPlayers(): List<PlayerHistory> {
        val playersJson = sharedPreferences.getString("players", null)
        return if (playersJson != null) {
            val type = object : TypeToken<List<PlayerHistory>>() {}.type
            gson.fromJson(playersJson, type)
        } else {
            emptyList()
        }
    }

    fun savePlayers(players: List<PlayerHistory>) {
        val playersJson = gson.toJson(players)
        sharedPreferences.edit().putString("players", playersJson).apply()
    }
}
