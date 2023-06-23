package com.example.pokerscore.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokerscore.PartyPoker
import com.example.pokerscore.board.BoardPoker
import com.example.pokerscore.board.CommunityCards
import com.example.pokerscore.card.Deck
import com.example.pokerscore.player.PlayerInGame
import com.example.pokerscore.ui.theme.PokerScoreTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardScreen(
    navController: NavController,
) {

    val playerNames = listOf("Player 1", "Player 2", "Player 3")
    val deck = Deck()
    val communityCards = CommunityCards()
    val boardPoker = BoardPoker.builder(deck, communityCards)
        .withSmallBlind(10)
        .withBigBlind(20)
        .build()
    val party = PartyPoker.getInstance(boardPoker)

    playerNames.forEach { party.join(it) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PlayerCards(party.board.players)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = { print(it) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Mise") }
            )
        }
        ButtonRow()
    }
}

@Composable
fun PlayerCards(players: List<PlayerInGame>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            players.forEach { player ->
                PlayerCard(player)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            PotCard(pot = 0)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            players.forEach { player ->
                PlayerCard(player)
            }
        }
    }
}

@Composable
fun PlayerCard(player: PlayerInGame) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .padding(8.dp)
            .background(Color.White)
            .border(width = 2.dp, color = Color.Green, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = player.name, style = MaterialTheme.typography.labelLarge)
            Text(text = "Jetons: ${player.getChipCount()}", style = MaterialTheme.typography.labelLarge)
            Text(text = player.getState().name, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun PotCard(pot: Int) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(60.dp)
            .padding(8.dp)
            .background(Color.Gray)
            .border(width = 2.dp, color = Color.Green, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Pot: $pot", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun ButtonRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { print("Check") },
            modifier = Modifier
                .height(40.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Check", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            onClick = { print("Fold") },
            modifier = Modifier
                .height(40.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Fold", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            onClick = { print("Call") },
            modifier = Modifier
                .height(40.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Call", color = MaterialTheme.colorScheme.onPrimary)
        }
        Button(
            onClick = { print("Raise") },
            modifier = Modifier
                .height(40.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Raise", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoardScreenPreview() {
    PokerScoreTheme {
        BoardScreen(navController = rememberNavController())
    }
}