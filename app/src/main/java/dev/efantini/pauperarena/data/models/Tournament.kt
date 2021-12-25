package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Tournament(
    @Id var id: Long = 0,
    val participants: List<TournamentPlayer>,
    val rounds: List<TournamentRound>,
    val pointsAssigned: String,
    val date: String,
    val seasonId: Long?
) {
    fun convertPointsToString(points: ArrayList<Int>): String {
        return points.joinToString(separator = "-")
    }

    fun convertStringToPoints(string: String): List<Int> {
        return string.split("-").map { it.toInt() }
    }
}
