package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.efantini.epicleague.R
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.ui.navigation.NavigationItem
import dev.efantini.epicleague.ui.theme.DEFAULT_LIST_ELEMENT_SPACING
import dev.efantini.epicleague.ui.theme.DEFAULT_SIDE_PADDING
import dev.efantini.epicleague.ui.viewmodels.PlayerListViewModel

@Preview
@Composable
fun PlayerListContent(
    playerListViewModel: PlayerListViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    var isEditing by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            AddFloatingActionButton(onClick = { isEditing = true })
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Surface(
                modifier = Modifier
                    .padding(DEFAULT_SIDE_PADDING)
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(DEFAULT_LIST_ELEMENT_SPACING),
                ) {
                    items(playerListViewModel.playerListContentUiState.playerItems) {
                        Box(
                            Modifier.clickable(onClick = {
                                navController.navigate(
                                    NavigationItem.PlayerDetail.route +
                                        "/" + it.player.id.toString()
                                )
                            })
                        ) {
                            PlayerCard(it)
                        }
                    }
                    item {
                        // Spacer(modifier = Modifier.height(DEFAULT_FLOATING_BUTTON_HEIGHT))
                    }
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
                                    playerListViewModel.putPlayers(
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
