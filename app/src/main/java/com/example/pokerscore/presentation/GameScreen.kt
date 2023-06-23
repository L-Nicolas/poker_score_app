package com.example.pokerscore.presentation

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokerscore.Party
import com.example.pokerscore.PartyPoker
import com.example.pokerscore.board.BoardPoker
import com.example.pokerscore.board.CommunityCards
import com.example.pokerscore.card.Deck
import com.example.pokerscore.ui.theme.PokerScoreTheme

@Composable
fun GameScreen(
    navController: NavController
) {
    var playerNames by remember { mutableStateOf(mutableStateListOf<String>()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Créer une partie",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        repeat(6) {
            PlayerInput { playerName ->
                playerNames.add(playerName)
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        Button(
            onClick = {
                val deck = Deck()
                val communityCards = CommunityCards()
                val boardPoker = BoardPoker.builder(deck, communityCards)
                    .withSmallBlind(10)
                    .withBigBlind(20)
                    .build()
                val party = PartyPoker(boardPoker)

                playerNames.forEach { party.join(it) }

                navController.navigate(Screen.Board.route)
            },
            modifier = Modifier.padding(vertical = 16.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Lancer la partie", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInput(onPlayerNameEntered: (String) -> Unit) {
    var playerName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = playerName,
            onValueChange = { playerName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Nom du joueur") }
        )
    }

    // Call the callback function when the player name is entered
    onPlayerNameEntered(playerName)
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    PokerScoreTheme {
        GameScreen(navController = rememberNavController())
    }
}
