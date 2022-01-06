package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.efantini.epicleague.R
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.ui.navigation.BOTTOMNAVBAR_HEIGHT
import dev.efantini.epicleague.ui.viewmodels.PlayerViewModel

@Preview
@Composable
fun PlayersListContent(playerViewModel: PlayerViewModel = hiltViewModel()) {

    var isEditing by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .height(36.dp)
                    .offset(
                        x = 0.dp,
                        y = BOTTOMNAVBAR_HEIGHT.value
                            .times(-1)
                            .plus(5).dp
                    ),
                onClick = { isEditing = true },
                icon = { Icon(Icons.Default.Add, "") },
                text = {
                    Text(
                        stringResource(id = R.string.add_player),
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(playerViewModel.playersListContentUiState.playerItems) {
                    PlayerCard(it)
                }
                item {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
            if (isEditing) {
                DialogCard(
                    onDismissRequest = { isEditing = false },
                    content = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            var firstNameText by rememberSaveable { mutableStateOf("") }
                            var lastNameText by rememberSaveable { mutableStateOf("") }
                            OutlinedTextField(
                                value = firstNameText,
                                onValueChange = { firstNameText = it },
                                label = { Text(stringResource(R.string.player_firstName)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )
                            OutlinedTextField(
                                value = lastNameText,
                                onValueChange = { lastNameText = it },
                                label = { Text(stringResource(R.string.player_lastName)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )
                            Button(
                                onClick = {
                                    isEditing = false
                                    playerViewModel.putPlayers(
                                        listOf(
                                            Player(
                                                firstName = firstNameText.trim(),
                                                lastName = lastNameText.trim()
                                            )
                                        )
                                    )
                                }
                            ) {
                                Text(stringResource(id = R.string.confirm))
                            }
                        }
                    }
                )
            }
        }
    }
}
