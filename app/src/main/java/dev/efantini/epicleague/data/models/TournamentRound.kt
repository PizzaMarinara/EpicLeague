package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class TournamentRound(
    @Id var id: Long = 0,
    var turnNumber: Int = 0
) {
    lateinit var tournament: ToOne<Tournament>
    @Backlink(to = "tournamentRound")
    lateinit var tournamentMatches: ToMany<TournamentMatch>
}
