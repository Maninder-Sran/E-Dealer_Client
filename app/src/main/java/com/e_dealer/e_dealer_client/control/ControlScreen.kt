package com.e_dealer.e_dealer_client.control

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.e_dealer.e_dealer_client.score.Player
import com.e_dealer.e_dealer_client.score.updateScore
import com.e_dealer.e_dealer_client.R
import com.google.firebase.database.FirebaseDatabase

val shuffleDeck = mutableStateOf(false)
val dealCard = mutableStateOf(false)
val playerIndex = mutableStateOf("")
val savedStateData = mutableStateOf(false)
var playerLs = List<Player>(1) { Player("NULL", 0.0) };

@Composable
fun ControlScreen() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        var playersState by remember { mutableStateOf(listOf(Player())) }

        if (savedStateData.value){
            playersState = playerLs
            savedStateData.value = false
        }

        if (dealCard.value){
            dealCard.value = false

            val database = FirebaseDatabase.getInstance()
            val playerLsRef = database.reference

            playerLsRef.child("Deal Card").setValue(playerIndex.value).addOnSuccessListener {

                Log.i("firebase", "Got value ${playerIndex.value.toString()}")
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }

        if (shuffleDeck.value){
            shuffleDeck.value = false

            val database = FirebaseDatabase.getInstance()
            val playerLsRef = database.reference

            playerLsRef.child("Shuffle Deck").setValue(shuffleDeck.value).addOnSuccessListener {
                Log.i("firebase", "Got value ${shuffleDeck.value.toString()}")
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        }

        ControlList(playerLs = playersState)
    }
}

@Composable
fun ControlList(playerLs : List<Player>){
    LazyColumn(Modifier.fillMaxHeight()) {
        if (playerLs.isNotEmpty()) {
            items(playerLs.size) { index ->
                if (playerLs[index].name != "NULL" && playerLs[index].name != "") {
                    ControlListItem(playerLs[index])
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlListItem(player: Player){
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RectangleShape
    )
    {
        Row {
            Column(
                modifier = Modifier.padding(8.dp)
            )
            {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        dealCard.value = true
                        playerIndex.value = player.name
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_deal_card),
                        contentDescription = ""
                    )
                }
            }
        }
    }
    
}