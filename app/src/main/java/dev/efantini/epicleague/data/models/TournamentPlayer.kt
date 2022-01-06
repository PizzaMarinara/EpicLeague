package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class TournamentPlayer(
    @Id var id: Long = 0,
) {
    lateinit var player: ToOne<Player>
    lateinit var deck: ToOne<Deck>
    lateinit var tournament: ToOne<Tournament>
}
