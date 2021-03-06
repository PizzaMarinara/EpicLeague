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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dev.efantini.epicleague.R
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.ui.theme.DEFAULT_SIDE_PADDING
import dev.efantini.epicleague.ui.theme.LocalSpacing
import dev.efantini.epicleague.ui.viewmodels.TournamentDetailViewModel

@Composable
fun TournamentDetailContent(
    tournamentDetailViewModel: TournamentDetailViewModel = hiltViewModel()
) {
    val state = tournamentDetailViewModel.tournamentDetailContentUiState
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
                    val newTournamentString = stringResource(id = R.string.new_tournament)
                    var tournamentName = state.tournament.name.ifBlank {
                        newTournamentString
                    }

                    OutlinedTextField(
                        value = tournamentName,
                        onValueChange = {
                            tournamentName = it
                            if (tournamentName.isBlank()) {
                                tournamentDetailViewModel
                                    .saveTournamentName(newTournamentString)
                            } else {
                                tournamentDetailViewModel
                                    .saveTournamentName(tournamentName.trim())
                            }
                        },
                        singleLine = true
                    )
                    Text(
                        text = state.tournament.name.ifBlank {
                            newTournamentString
                        }
                    )
                    // CustomTextField()
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.default),
                    ) {
                        items(state.tournamentPlayers) {
                            Box {
                                TournamentPlayerCard(it)
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
                            var expanded by rememberSaveable { mutableStateOf(false) }
                            var selectedPlayer: Player? by rememberSaveable { mutableStateOf(null) }
                            val openDialog = rememberSaveable { mutableStateOf(false) }
                            val playersAvailable = state.availablePlayers.isNotEmpty()
                            val icon = if (expanded)
                                Icons.Filled.KeyboardArrowUp
                            else
                                Icons.Filled.KeyboardArrowDown

                            OutlinedTextField(
                                value = selectedPlayer?.fullName ?: "",
                                onValueChange = { },
                                enabled = false,
                                modifier = if (playersAvailable) {
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable { expanded = !expanded }
                                } else {
                                    Modifier.fillMaxWidth()
                                },
                                label = {
                                    if (playersAvailable) {
                                        Text(stringResource(R.string.select_player))
                                    } else {
                                        Text(stringResource(R.string.no_more_players))
                                    }
                                },
                                trailingIcon = {
                                    if (playersAvailable) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = "contentDescription"
                                        )
                                    }
                                }
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                state.availablePlayers.forEach { player ->
                                    DropdownMenuItem(onClick = {
                                        selectedPlayer = player
                                        expanded = false
                                    }) {
                                        Text(text = player.fullName)
                                    }
                                }
                            }
                            if (playersAvailable) {
                                Button(
                                    onClick = {
                                        if (selectedPlayer == null) {
                                            openDialog.value = true
                                        } else {
                                            isEditing = false
                                            tournamentDetailViewModel.putTournamentPlayers(
                                                listOf(
                                                    TournamentPlayer().also {
                                                        it.player.target = selectedPlayer
                                                    }
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    Text(stringResource(id = R.string.confirm))
                                }
                            }
                            if (openDialog.value) {
                                AlertDialog(
                                    onDismissRequest = {
                                        openDialog.value = false
                                    },
                                    title = {
                                        Text(text = stringResource(R.string.warning))
                                    },
                                    text = {
                                        Text(stringResource(R.string.please_select_entry))
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                openDialog.value = false
                                            }
                                        ) {
                                            Text(stringResource(R.string.ok))
                                        }
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
