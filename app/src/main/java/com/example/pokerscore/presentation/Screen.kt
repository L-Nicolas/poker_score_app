package com.example.pokerscore.presentation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Game : Screen("game_screen")
    object History : Screen("history_screen")
}