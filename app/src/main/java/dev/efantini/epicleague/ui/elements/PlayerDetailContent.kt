package dev.efantini.epicleague.ui.elements

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
import androidx.hilt.navigation.compose.hiltViewModel
import dev.efantini.epicleague.R
import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.ui.theme.DEFAULT_LIST_ELEMENT_SPACING
import dev.efantini.epicleague.ui.theme.DEFAULT_SIDE_PADDING
import dev.efantini.epicleague.ui.viewmodels.PlayerDetailViewModel

@Composable
fun PlayerDetailContent(
    playerDetailViewModel: PlayerDetailViewModel = hiltViewModel()
) {
    val player = playerDetailViewModel.playerDetailContentUiState.player
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(player?.fullName ?: "")
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(DEFAULT_LIST_ELEMENT_SPACING),
                    ) {
                        items(playerDetailViewModel.playerDetailContentUiState.decks) {
                            Box {
                                DeckCard(it)
                            }
                        }
                        item {
                            // Spacer(modifier = Modifier.height(DEFAULT_FLOATING_BUTTON_HEIGHT))
                        }
                    }
                }
            }
            if (isEditing) {
                DialogCard(
                    onDismissRequest = { isEditing = false },
                    content = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            var deckNameText by rememberSaveable { mutableStateOf("") }
                            OutlinedTextField(
                                value = deckNameText,
                                onValueChange = { deckNameText = it },
                                label = { Text(stringResource(R.string.deck_name)) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                )
                            )
                            Button(
                                onClick = {
                                    isEditing = false
                                    playerDetailViewModel.putDecks(
                                        listOf(
                                            Deck(
                                                name = deckNameText.trim(),
                                            ).also { it.player.target = player }
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
