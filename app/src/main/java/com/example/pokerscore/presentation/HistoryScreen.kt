package com.example.pokerscore.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokerscore.player.PlayerHistory
import com.example.pokerscore.ui.theme.PokerScoreTheme
import androidx.compose.ui.platform.LocalContext


@Composable
fun HistoryScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    println("context : $context")

    var players = listOf(
        PlayerHistory("John Doe", 1000),
        PlayerHistory("Jane Smith", 500),
        PlayerHistory("Mike Johnson", 750)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Historique des parties",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 18.dp)
            )
        }

        items(players) { player ->
            PlayerCard(player)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Composable
fun PlayerCard(player: PlayerHistory) {
    Card(
        shape = RoundedCornerShape(8.dp),
        //modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Nom du joueur : ${player.name}",
            )
            Text(
                text = "Gains : ${player.score}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    PokerScoreTheme {
        HistoryScreen(navController = rememberNavController())
    }
}
