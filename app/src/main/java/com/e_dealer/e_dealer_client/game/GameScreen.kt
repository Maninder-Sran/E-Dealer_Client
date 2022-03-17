package com.e_dealer.e_dealer_client.game

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

val savedGameData = mutableStateOf(false)
var gameLs = List<Game>(1) { Game("*", "*",0) };

@Composable
fun GameScreen(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        var gameState by remember { mutableStateOf(listOf(Game())) }

        if (savedGameData.value){
            gameState = gameLs
            savedGameData.value = false
        }

        GameList(gameState)
    }
}

@Composable
fun GameList(gameLs : List<Game>){
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(gameLs[0]) }

    LazyColumn(
        Modifier
            .fillMaxHeight()
            .selectableGroup()
    ) {
        if (gameLs.isNotEmpty()) {
            items(gameLs.size) { index ->
                val modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (gameLs[index] == selectedOption),
                        onClick = { onOptionSelected(gameLs[index]) },
                        role = Role.RadioButton
                    )

                GameListItem(gameLs[index], selectedOption, onOptionSelected)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListItem(game: Game, selectedOption: Game, onOptionSelected: (Game) -> Unit){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RectangleShape
    )
    {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectable(
                    selected = (game == selectedOption),
                    onClick = { onOptionSelected(game) },
                    role = Role.RadioButton
                )

        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            )
            {
                Text(
                    text = game.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "Max player count: " + game.maxPlayerCnt.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
            RadioButton(
                selected = (game == selectedOption),
                onClick = null )
        }
    }
    }
}