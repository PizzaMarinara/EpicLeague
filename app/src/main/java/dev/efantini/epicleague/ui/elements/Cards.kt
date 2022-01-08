package dev.efantini.epicleague.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.efantini.epicleague.ui.states.DeckItemUiState
import dev.efantini.epicleague.ui.states.PlayerItemUiState
import dev.efantini.epicleague.ui.states.TournamentItemUiState
import dev.efantini.epicleague.ui.states.TournamentPlayerItemUiState
import dev.efantini.epicleague.ui.theme.DEFAULT_CARD_HEIGHT
import dev.efantini.epicleague.ui.theme.DEFAULT_CARD_SHAPE
import dev.efantini.epicleague.ui.theme.Dark4
import dev.efantini.epicleague.ui.theme.KarlaFontFamily
import dev.efantini.epicleague.ui.theme.Light4

@Composable
fun PlayerCard(
    item: PlayerItemUiState,
) {
    val isLightTheme = MaterialTheme.colors.isLight
    Card(
        elevation = DEFAULT_CARD_HEIGHT,
        shape = DEFAULT_CARD_SHAPE,
        backgroundColor = if (isLightTheme) { Light4 } else { Dark4 }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(15.dp))
            Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Text(
                    text = item.player.fullName,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black),
                    fontFamily = KarlaFontFamily
                )
            }
        }
    }
}

@Composable
fun DeckCard(
    item: DeckItemUiState,
) {
    val isLightTheme = MaterialTheme.colors.isLight
    Card(
        elevation = DEFAULT_CARD_HEIGHT,
        shape = DEFAULT_CARD_SHAPE,
        backgroundColor = if (isLightTheme) { Light4 } else { Dark4 }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(15.dp))
            Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Text(
                    text = item.deck.name,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black),
                    fontFamily = KarlaFontFamily
                )
            }
        }
    }
}

@Composable
fun TournamentCard(
    item: TournamentItemUiState,
) {
    val isLightTheme = MaterialTheme.colors.isLight
    Card(
        elevation = DEFAULT_CARD_HEIGHT,
        shape = DEFAULT_CARD_SHAPE,
        backgroundColor = if (isLightTheme) { Light4 } else { Dark4 }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(15.dp))
            Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Text(
                    text = item.tournament.name,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black),
                    fontFamily = KarlaFontFamily
                )
            }
        }
    }
}

@Composable
fun TournamentPlayerCard(
    item: TournamentPlayerItemUiState,
) {
    val isLightTheme = MaterialTheme.colors.isLight
    Card(
        elevation = DEFAULT_CARD_HEIGHT,
        shape = DEFAULT_CARD_SHAPE,
        backgroundColor = if (isLightTheme) { Light4 } else { Dark4 }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(15.dp))
            Column(Modifier.padding(top = 5.dp, bottom = 5.dp)) {
                Text(
                    text = item.tournamentPlayer.player.target.fullName,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black),
                    fontFamily = KarlaFontFamily
                )
            }
        }
    }
}

@Composable
fun DialogCard(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Card(
            elevation = DEFAULT_CARD_HEIGHT,
            shape = DEFAULT_CARD_SHAPE
        ) {
            Surface(modifier = Modifier.padding(15.dp)) {
                content()
            }
        }
    }
}
