package com.e_dealer.e_dealer_client.score

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.e_dealer.e_dealer_client.R

private var context : FragmentActivity? = null
val createPlayer = mutableStateOf(false)
val updateScore = mutableStateOf(true)
val savedStateData = mutableStateOf(false)
var playerLs = List(1) { Player("NULL", 0.0) };

@Composable
fun ScoreScreen(activity: FragmentActivity?) {

    if (activity != null) {
        context = activity
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        var playersState by remember { mutableStateOf(listOf(Player())) }

        if (savedStateData.value){
            playersState = playerLs
            savedStateData.value = false
        }

        if (createPlayer.value) {
            PlayerCreationDialog { item ->
                val tempList = ArrayList<Player>(1)
                val add = tempList.add(item)
                playersState = playersState + listOf(item)
                playerLs = playersState
            }
            createPlayer.value = false
        }
        if (updateScore.value){
            updateScore.value = false
            PlayerList(playerLs = playersState)
        }
        
        PlayerList(playerLs = playersState)
    }
}

@Composable
fun PlayerCreationDialog(onPlayerAdded: (Player) -> Unit){
    val builder : AlertDialog.Builder = android.app.AlertDialog.Builder(context)
    builder.setTitle("New Player")

    val input = EditText(context)

    input.setHint("Enter players name")
    input.inputType = InputType.TYPE_CLASS_TEXT
    builder.setView(input)

    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
        // Here you get get input text from the Edittext
        var name = input.text.toString()
        onPlayerAdded(Player(name, 0.0))
    })
    builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

    builder.show()
}

@Composable
fun PlayerList(playerLs : List<Player>){

    LazyColumn(Modifier.fillMaxHeight()) {
        if (playerLs.isNotEmpty()) {
            items(playerLs.size) { index ->
                if (playerLs[index].name != "NULL" && playerLs[index].name != "") {
                    PlayerListItem(playerLs[index])
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListItem(player : Player) {
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
                Text(
                    text = player.score.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        player.score = player.score?.plus(1)
                        updateScore.value = true
                    }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = ""
                    )
                }
                Button(
                    onClick = {
                        if (player.score!! > 0) {
                            player.score = player.score?.minus(1)
                        }
                        updateScore.value = true
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_minus),
                        ""
                    )
                }
            }
        }
    }
}

