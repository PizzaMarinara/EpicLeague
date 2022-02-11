package dev.efantini.epicleague

import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentMatch
import dev.efantini.epicleague.data.models.TournamentPlayer
import kotlin.math.sqrt
import kotlin.random.Random
import org.junit.Before
import org.junit.Test

class TournamentSystemsComparingTest {

    var torneiList = mutableListOf<Tournament>()

    enum class RankingType {
        POINTS_SUM, CLASSIFICATION, INCENTER
    }

    private val AVERAGE_PLAYER_NUMBER = 16 // At least 4!
    private val MAX_PLAYERS = AVERAGE_PLAYER_NUMBER + (AVERAGE_PLAYER_NUMBER).floorDiv(2)
    private val TOURNAMENT_NUMBER = 12

    @Before
    fun setUp() {

        println("AVERAGE PLAYERS: $AVERAGE_PLAYER_NUMBER")
        println("N° OF TOURNAMENTS: $TOURNAMENT_NUMBER")

        for (j in 1..TOURNAMENT_NUMBER) {
            val torneo = Tournament(name = "Just a test Tournament $j").apply {
                leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
                pointsToLast = true
            }
            torneo.also {
                val check = Random.nextInt(1, 101)
                val actualPlayerNumber = when (check) {
                    in 1..30 -> AVERAGE_PLAYER_NUMBER
                    in 31..45 -> AVERAGE_PLAYER_NUMBER + ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 3)
                    in 46..60 -> AVERAGE_PLAYER_NUMBER - ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 3)
                    in 61..70 -> AVERAGE_PLAYER_NUMBER + ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 2)
                    in 71..80 -> AVERAGE_PLAYER_NUMBER - ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 2)
                    in 81..85 -> AVERAGE_PLAYER_NUMBER + ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 1)
                    in 86..90 -> AVERAGE_PLAYER_NUMBER - ((AVERAGE_PLAYER_NUMBER).floorDiv(2) - 1)
                    in 91..95 -> AVERAGE_PLAYER_NUMBER + (AVERAGE_PLAYER_NUMBER).floorDiv(2)
                    in 96..100 -> AVERAGE_PLAYER_NUMBER - (AVERAGE_PLAYER_NUMBER).floorDiv(2)
                    else -> AVERAGE_PLAYER_NUMBER
                }

                val playerIdsList = mutableListOf<Int>()
                for (k in 1..MAX_PLAYERS) {
                    playerIdsList.add(k)
                }
                for (k in 1..actualPlayerNumber) {
                    playerIdsList.shuffle()
                    val idRandomized = playerIdsList.removeLast()
                    it.tournamentPlayers.add(
                        TournamentPlayer().apply {
                            id = idRandomized.toLong()
                            tournament.target = torneo
                            player.target = Player(
                                id = idRandomized.toLong(),
                                firstName = "Player",
                                lastName = idRandomized.toString().padStart(2, '0')
                            )
                        }
                    )
                }
            }
            torneiList.add(torneo)
        }
    }

    @Test
    fun `rounds are paired correctly`() {
        torneiList.forEachIndexed { index, torneo ->
            while (!torneo.isEnded()) {
                torneo.pairNewRound()
                torneo.tournamentRounds.last().tournamentMatches.forEach {
                    if (it.tournamentPlayer2.isNull) {
                        it.player1Points = 2
                        it.player2Points = 0
                    } else {
                        val winner = Random.nextInt(1, 12)
                        val loserPoints = Random.nextInt(0, 2)
                        when (winner) {
                            in 1..5 -> {
                                it.player1Points = 2
                                it.player2Points = loserPoints
                            }
                            in 6..10 -> {
                                it.player1Points = loserPoints
                                it.player2Points = 2
                            }
                            11 -> {
                                it.player1Points = 1
                                it.player2Points = 1
                            }
                        }
                    }
                    // printMatch(it)
                }
                torneo.tournamentRounds.last().isFinished = true
                // printStandingsShort(torneo)
            }
            println("----")
            println("-Tournament ${index + 1} - N° of players: ${torneo.tournamentPlayers.size}")
            printChampionshipPoints(torneiList, RankingType.POINTS_SUM)
            printChampionshipPoints(torneiList, RankingType.CLASSIFICATION)
            printChampionshipPoints(torneiList, RankingType.INCENTER)
        }
        // printChampionshipPoints(torneiList, true)
        // printChampionshipPoints(torneiList, false)
    }

    private fun printMatch(match: TournamentMatch) {
        val p1Id =
            if (match.tournamentPlayer1.target?.id == null)
                "BYE"
            else
                match.tournamentPlayer1.target?.id.toString()
        val p2Id =
            if (match.tournamentPlayer2.target?.id == null)
                "BYE"
            else
                match.tournamentPlayer2.target?.id.toString()

        println("$p1Id (${match.player1Points}) VS $p2Id (${match.player2Points})")
    }

    private fun printStandings(tournament: Tournament) {
        tournament.getStandings().forEach {
            println("----")
            println(it.player.target.fullName)
            println("Points:" + it.getTournamentPoints())
            println("OMW:" + it.getOpponentsWinPerc())
            println("GW:" + it.getGameWinPerc())
            println("OGW:" + it.getOpponentsGameWinPerc())
            println("----")
        }
    }

    private fun printStandingsShort(tournament: Tournament) {
        tournament.getStandings().forEach {
            println(
                it.player.target.fullName +
                    " " + it.getTournamentPoints() +
                    " " + it.getOpponentsWinPerc() +
                    " " + it.getGameWinPerc() +
                    " " + it.getOpponentsGameWinPerc()
            )
        }
    }

    private fun printEndMatches(tournament: Tournament) {
        tournament.tournamentRounds.forEach { tournamentRound ->
            assert(tournamentRound.tournamentMatches.size > 0)
            tournamentRound.tournamentMatches.forEach { tournamentMatch ->
                val player1 = tournamentMatch.tournamentPlayer1.target
                    ?.player?.target?.fullName ?: "BYE"
                val player2 = tournamentMatch.tournamentPlayer2.target
                    ?.player?.target?.fullName ?: "BYE"
                val player1P = tournamentMatch.player1Points
                val player2P = tournamentMatch.player2Points
                println("---")
                println("Round: " + tournamentRound.turnNumber)
                println("Match: " + tournamentMatch.matchNumber)
                println("$player1 ($player1P) VS $player2 ($player2P)")
                println("---")
            }
        }
    }

    private fun printChampionshipPoints(
        torneiList: MutableList<Tournament>,
        rankingType: RankingType = RankingType.INCENTER
    ) {
        println("----")
        val rankingsLists = mutableListOf<List<Pair<TournamentPlayer, Double>>>()
        when (rankingType) {
            RankingType.CLASSIFICATION -> {
                torneiList.filter { it.isEnded() }.forEach {
                    rankingsLists.add(
                        calculateClassificationForRankings(
                            it.getStandings(),
                            it.leaguePointsAssigned,
                            it.pointsToLast
                        )
                    )
                }
            }
            RankingType.POINTS_SUM -> {
                torneiList.filter { it.isEnded() }.forEach {
                    rankingsLists.add(
                        calculatePointsSumForRankings(
                            it.getStandings()
                        )
                    )
                }
            }
            RankingType.INCENTER -> {
                torneiList.filter { it.isEnded() }.forEach {
                    rankingsLists.add(calculateIncenterForRankings(it.getStandings()))
                }
            }
        }

        val finalResult = mutableListOf<Pair<TournamentPlayer, Double>>()

        rankingsLists.forEach { rankList ->
            rankList.forEach { tournamentResult ->
                val previousResult = finalResult.firstOrNull {
                    it.first.player.targetId == tournamentResult.first.player.targetId
                }
                if (previousResult == null) {
                    finalResult.add(tournamentResult)
                } else {
                    finalResult[finalResult.indexOf(previousResult)] =
                        previousResult
                            .copy(second = previousResult.second + tournamentResult.second)
                }
            }
        }
        finalResult.sortByDescending {
            it.second
        }
        finalResult.forEach { finalScore ->
            print(
                finalScore.first.player.target.fullName + " " +
                    "P(" +
                    rankingsLists.filter {
                        it.find {
                            pair ->
                            pair.first.player.targetId == finalScore.first.player.targetId
                        } != null
                    }.size.toString().padStart(2, '0') + ")" +
                    " "
            )
            printRankingsForPlayer(finalScore.first, rankingsLists, true)
            print(" = ${finalScore.second}")
            print("\n")
        }
    }

    private fun printRankingsForPlayer(
        player: TournamentPlayer,
        rankingsLists: MutableList<List<Pair<TournamentPlayer, Double>>>,
        printNP: Boolean = false
    ) {
        rankingsLists.forEachIndexed { index, ranking ->
            val myRank = ranking.firstOrNull { tournamentResult ->
                tournamentResult.first.player.targetId == player.player.targetId
            }
            if (myRank != null) {
                print(
                    " T${(index + 1).toString().padStart(2, '0')}" +
                        "[" + ranking.size.toString().padStart(2, '0') + "]:" +
                        (ranking.indexOf(myRank) + 1).toString().padStart(2, '0')
                )
            } else {
                if (printNP) {
                    print(
                        " T${(index + 1).toString().padStart(2, '0')}" +
                            "[" + ranking.size.toString().padStart(2, '0') + "]:NP"
                    )
                }
            }
        }
    }

    private fun <T> calculateIncenterForRankings(elements: List<T>): List<Pair<T, Double>> {
        val listRankings = arrayListOf<Pair<T, Double>>()
        elements.forEach {
            listRankings.add(Pair(it, 0.0))
        }
        for (k in elements.size - 1 downTo 0) {
            val pairElement = when (elements.size - k) {
                1 -> Pair(elements[k], 1.0)
                2 -> Pair(elements[k], sqrt(2.0) + 1)
                3 -> Pair(elements[k], (sqrt(2.0) + 1) * (sqrt(3.0) + 2) - (sqrt(3.0) + 1))
                else -> Pair(
                    elements[k],
                    (
                        (3 * listRankings[k + 1].second) -
                            (3 * listRankings[k + 2].second) +
                            listRankings[k + 3].second
                        )
                )
            }
            listRankings[k] = pairElement
        }
        return listRankings
    }

    private fun <T> calculateClassificationForRankings(
        elements: List<T>,
        leaguePointsAssigned: String,
        pointsToLast: Boolean
    ): List<Pair<T, Double>> {
        val listRankings = arrayListOf<Pair<T, Double>>()
        val pointsList = Tournament.convertStringToPoints(leaguePointsAssigned)
        elements.forEachIndexed { index, element ->
            if (index < pointsList.size) {
                listRankings.add(Pair(element, pointsList[index].toDouble()))
            } else {
                if (pointsToLast) {
                    listRankings.add(Pair(element, pointsList.last().toDouble()))
                } else {
                    listRankings.add(Pair(element, 0.0))
                }
            }
        }
        return listRankings
    }

    private fun calculatePointsSumForRankings(
        elements: List<TournamentPlayer>
    ): List<Pair<TournamentPlayer, Double>> {
        val listRankings = arrayListOf<Pair<TournamentPlayer, Double>>()
        elements.forEachIndexed { index, element ->
            listRankings.add(Pair(element, element.getTournamentPoints().toDouble()))
        }
        return listRankings
    }
}
