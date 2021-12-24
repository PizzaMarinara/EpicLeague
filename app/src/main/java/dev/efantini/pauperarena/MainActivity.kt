package dev.efantini.pauperarena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.efantini.pauperarena.ui.states.DeckItemUiState
import dev.efantini.pauperarena.ui.theme.PauperArenaTheme
import dev.efantini.pauperarena.ui.viewmodels.DeckViewModel
import dev.efantini.pauperarena.ui.viewmodels.DeckViewModelFactory

class MainActivity : ComponentActivity() {

    private val deckViewModel: DeckViewModel by viewModels { DeckViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                                items(deckViewModel.uiState.deckItems) {
                                    ScoreCard(item = it)
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
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ScoreCard(
    item: DeckItemUiState,
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Column(Modifier.padding(top = 0.dp, bottom = 0.dp)) {
                Text(
                    text = item.deck.name,
                    style = MaterialTheme.typography.h6.copy(color = Color.Black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PauperArenaTheme {
        Greeting("Android")
    }
}
