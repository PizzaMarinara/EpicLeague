package dev.efantini.epicleague.data.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class TournamentPlayer(
    @Id var id: Long = 0
) {
    lateinit var player: ToOne<Player>
    lateinit var deck: ToOne<Deck>
    lateinit var tournament: ToOne<Tournament>

    fun getMatches(): List<TournamentMatch> {
        val playerMatches = mutableListOf<TournamentMatch>()
        tournament.target.tournamentRounds
            .sortedByDescending { it.turnNumber }.forEach { round ->
                round.tournamentMatches.forEach { match ->
                    if (match.tournamentPlayer1.target == this ||
                        match.tournamentPlayer2.target == this
                    ) {
                        playerMatches.add(match)
                    }
                }
            }
        return playerMatches
    }

    fun getTournamentPoints(): Int {
        var accumulatedPoints = 0
        getMatches().forEach {
            accumulatedPoints += when (it.getWinner()) {
                this -> tournament.target.winPoints
                null -> tournament.target.drawPoints
                else -> tournament.target.losePoints
            }
        }
        return accumulatedPoints
    }

    fun getWinPercentage(): Double {
        return(
            getTournamentPoints().toDouble() /
                (getMatches().size * tournament.target.winPoints).toDouble()
            )
    }

    fun getOpponentsWinPerc(): Double {
        return getMatches().mapNotNull {
            it.getOpponent(this)
        }.map {
            if (it.getWinPercentage() < tournament.target.floorWinPercentage)
                tournament.target.floorWinPercentage
            else
                it.getWinPercentage()
        }.average()
    }

    fun getGameWinPerc(): Double {
        val gamesWonTotalPair = getMatches().mapNotNull {
            if (it.getOpponent(this) == null)
                null
            else
                it
        }.fold(Pair(0, 0)) { counts, match ->
            Pair(
                counts.first + match.getPlayerPoints(this),
                counts.second + match.player1Points + match.player2Points
            )
        }

        return if (gamesWonTotalPair.second == 0)
            0.0
        else
            (gamesWonTotalPair.first.toDouble() / gamesWonTotalPair.second.toDouble())
    }

    fun getOpponentsGameWinPerc(): Double {
        return getMatches().mapNotNull {
            it.getOpponent(this)
        }.map {
            if (it.getGameWinPerc() < tournament.target.floorWinPercentage)
                tournament.target.floorWinPercentage
            else
                it.getGameWinPerc()
        }.average()
    }
}
