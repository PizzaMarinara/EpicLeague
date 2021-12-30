package dev.efantini.pauperarena.ui.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.efantini.pauperarena.ui.theme.PauperArenaTheme
import dev.efantini.pauperarena.ui.viewmodels.PlayerViewModel

@Composable
fun PlayersListContent(playerViewModel: PlayerViewModel = hiltViewModel()) {

    PauperArenaTheme {
        Scaffold(
            content = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    color = MaterialTheme.colors.primaryVariant
                ) {
                    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                        items(playerViewModel.playersListContentUiState.playerItems) {
                            PlayerCard(it)
                        }
                        item {
                            Spacer(modifier = Modifier.height(56.dp))
                        }
                    }
                }
            }
        )
    }
}
