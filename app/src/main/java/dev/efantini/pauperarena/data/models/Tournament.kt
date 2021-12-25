package dev.efantini.pauperarena.data.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class Tournament(
    @Id var id: Long = 0,
    var pointsAssigned: String = "",
    var date: String = ""
) {
    @Backlink(to = "tournament")
    lateinit var rounds: ToMany<TournamentRound>
    @Backlink(to = "tournament")
    lateinit var players: ToMany<TournamentPlayer>

    fun convertPointsToString(points: ArrayList<Int>): String {
        return points.joinToString(separator = "-")
    }

    fun convertStringToPoints(string: String): List<Int> {
        return string.split("-").map { it.toInt() }
    }
}
