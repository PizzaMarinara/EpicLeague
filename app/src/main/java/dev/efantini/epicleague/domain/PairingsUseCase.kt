package dev.efantini.epicleague.domain

object PairingsUseCase {
    fun getNumberOfRounds(players: Int): Int = when (players) {
        in 2..4 -> 2
        in 5..8 -> 3
        in 9..16 -> 4
        in 17..32 -> 5
        in 33..64 -> 6
        in 65..128 -> 7
        in 129..212 -> 8
        in 213..385 -> 9
        else -> 10
    }
}
