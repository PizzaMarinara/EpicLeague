package dev.efantini.maxweightmatch

import dev.efantini.epicleague.data.models.Deck
import dev.efantini.epicleague.data.models.Player
import dev.efantini.epicleague.data.models.Tournament
import dev.efantini.epicleague.data.models.TournamentPlayer
import dev.efantini.epicleague.domain.WeightedMaximumMatchingAlgo
import org.junit.Before
import org.junit.Test

class GenerateGraphEdgesTest {

    lateinit var torneo: Tournament

    @Before
    fun setUp() {
        val p1 = Player(id = 1, firstName = "Andy", lastName = "Atkinson")
        val p2 = Player(id = 2, firstName = "Bobby", lastName = "Bonanza")
        val p3 = Player(id = 3, firstName = "Candy", lastName = "Connor")
        val p4 = Player(id = 4, firstName = "Desmond", lastName = "Davidson")
        val p5 = Player(id = 5, firstName = "Emily", lastName = "Evans")
        val p6 = Player(id = 6, firstName = "Frank", lastName = "Fogarty")

        val d1 = Deck(id = 1, name = "MonoU Faeries")
        val d2 = Deck(id = 2, name = "Affinity")
        val d3 = Deck(id = 3, name = "UB Faeries")
        val d4 = Deck(id = 4, name = "Jund Madness")
        val d5 = Deck(id = 5, name = "MonoU Delver")
        val d6 = Deck(id = 6, name = "UB Faeries")

        torneo = Tournament(name = "Just a test Tournament").apply {
            leaguePointsAssigned = "10-8-6-5-2-2-2-2-1"
            pointsToLast = true
        }

        val tp1 = TournamentPlayer().apply {
            id = 1
            tournament.target = torneo
            player.target = p1
            deck.target = d1
        }
        val tp2 = TournamentPlayer().apply {
            id = 2
            tournament.target = torneo
            player.target = p2
            deck.target = d2
        }
        val tp3 = TournamentPlayer().apply {
            id = 3
            tournament.target = torneo
            player.target = p3
            deck.target = d3
        }
        val tp4 = TournamentPlayer().apply {
            id = 4
            tournament.target = torneo
            player.target = p4
            deck.target = d4
        }
        val tp5 = TournamentPlayer().apply {
            id = 5
            tournament.target = torneo
            player.target = p5
            deck.target = d5
        }
        val tp6 = TournamentPlayer().apply {
            id = 6
            tournament.target = torneo
            player.target = p6
            deck.target = d6
        }

        torneo.apply {
            tournamentPlayers.add(tp1)
            tournamentPlayers.add(tp2)
            tournamentPlayers.add(tp3)
            tournamentPlayers.add(tp4)
            tournamentPlayers.add(tp5)
            tournamentPlayers.add(tp6)
        }
    }

    @Test
    fun `graph edges are returned correctly`() {
        val edges = WeightedMaximumMatchingAlgo.getGraphEdges(torneo.getStandings())
        println("---")
        edges.forEachIndexed { index, it ->
            println(
                "Edge[" + index + "]: " +
                    it.node1 + " to " + it.node2 + " - Weight: " + it.weight
            )
        }
        println("---")

        val nedges = edges.count()
        var nvertices = 0
        edges.forEach { graphEdge ->
            if (graphEdge.node1 >= 0 &&
                graphEdge.node2 >= 0 &&
                graphEdge.node1 != graphEdge.node2
            ) {
                if (graphEdge.node1 >= nvertices)
                    nvertices = (graphEdge.node1 + 1).toInt()
                if (graphEdge.node2 >= nvertices)
                    nvertices = (graphEdge.node2 + 1).toInt()
            }
        }
        println("N. Edges: " + nedges)
        println("N. Vertices: " + nvertices)

        println("---")
        val endpoints = mutableListOf<Long>()
        edges.forEach {
            endpoints.add(it.node1)
            endpoints.add(it.node2)
        }
        endpoints.forEachIndexed { index, it ->
            println(
                "Edge[" + index + "]: " +
                    it
            )
        }

        println("---")
        val neighbend = mutableListOf<MutableList<Int>>()
        edges.forEachIndexed { k, graphEdge ->
            if (neighbend.size <= graphEdge.node1.toInt()) {
                neighbend.add(graphEdge.node1.toInt(), mutableListOf())
            }
            neighbend[graphEdge.node1.toInt()].add((2 * k) + 1)
            if (neighbend.size <= graphEdge.node2.toInt()) {
                neighbend.add(graphEdge.node2.toInt(), mutableListOf())
            }
            neighbend[graphEdge.node2.toInt()].add((2 * k))
        }

        neighbend.forEachIndexed { index, it ->
            it.forEach {
                println(
                    "Neighbend[" + index + "]: " +
                        it
                )
            }
        }
    }
}
