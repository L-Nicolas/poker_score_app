package com.example.pokerscore.presentation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokerscore.ui.theme.PokerScoreTheme

@Composable
fun GameScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Créer une partie",
            //style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        repeat(6) {
            PlayerInput()
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier.padding(vertical = 16.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Lancer la partie", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerInput() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nom du joueur:")
        OutlinedTextField(
            value = "",
            onValueChange = { /*TODO*/ },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    PokerScoreTheme {
        GameScreen(navController = rememberNavController())
    }
}
