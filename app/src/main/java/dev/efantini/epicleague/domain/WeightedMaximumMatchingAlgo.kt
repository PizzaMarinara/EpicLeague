package dev.efantini.epicleague.domain

import dev.efantini.epicleague.data.models.TournamentPlayer
import kotlin.math.max
import kotlin.math.min

object WeightedMaximumMatchingAlgo {

    private fun weightTwoPlayers(
        highScore: Long,
        player1: TournamentPlayer,
        player2: TournamentPlayer
    ): Long {

        var weight: Long = 0

        /**
         * If two players haven't played, system will give the highest weight.
         */
        if (!player1.getOpponentsPlayed().contains(player2))
            weight += quality(highScore, highScore) + 1

        /**
         * Determine a score of the pairing based on the players' current scores.
         */
        val best = max(player1.getTournamentPoints(), player2.getTournamentPoints()).toLong()
        val worst = min(player1.getTournamentPoints(), player2.getTournamentPoints()).toLong()
        val spread = best - worst
        val closeness = highScore - spread
        weight += quality(best, closeness)

        return weight
    }

    private fun quality(importance: Long, closeness: Long): Long {
        return (importance + 1) * (closeness + 1)
    }

    fun getGraphEdges(tournamentPlayers: List<TournamentPlayer>): List<GraphEdge> {

        val edges = mutableListOf<GraphEdge>()

        val highScore = tournamentPlayers.maxOf { tournamentPlayer ->
            tournamentPlayer.getTournamentPoints()
        }
        tournamentPlayers.forEachIndexed { i, tournamentPlayer1 ->
            for (j in i + 1 until tournamentPlayers.size) {
                edges.add(
                    GraphEdge(
                        i.toLong(),
                        j.toLong(),
                        weightTwoPlayers(
                            highScore.toLong(),
                            tournamentPlayer1,
                            tournamentPlayers[j]
                        )
                    )
                )
            }
        }

        return edges
    }

    fun maxWeightMatching(edges: List<GraphEdge>): List<Pair<Long, Long>> {
        /**
         * Deal swiftly with empty graphs.
         */
        if (edges.isEmpty())
            return listOf()

        /**
         * Count vertices and edges
         */
        val nedges = edges.count()
        var nvertex = 0
        edges.forEach { graphEdge ->
            if (graphEdge.node1 >= 0 &&
                graphEdge.node2 >= 0 &&
                graphEdge.node1 != graphEdge.node2
            ) {
                if (graphEdge.node1 >= nvertex)
                    nvertex = (graphEdge.node1 + 1).toInt()
                if (graphEdge.node2 >= nvertex)
                    nvertex = (graphEdge.node2 + 1).toInt()
            }
        }
        val maxweight = max(0, edges.maxOf { it.weight })

        val endpoint = mutableListOf<Long>()
        edges.forEach {
            endpoint.add(it.node1)
            endpoint.add(it.node2)
        }

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

        val mate = mutableListOf<Long>()
        for (i in 0 until nvertex) {
            mate.add(-1)
        }

        val label = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            label.add(0)
        }
        for (i in 0 until nvertex) {
            label.add(0)
        }

        val labelend = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            labelend.add(-1)
        }
        for (i in 0 until nvertex) {
            labelend.add(-1)
        }

        val inblossom = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            inblossom.add(i)
        }

        val blossomparent = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            blossomparent.add(-1)
        }
        for (i in 0 until nvertex) {
            blossomparent.add(-1)
        }

        val blossomchilds = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossomchilds.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossomchilds.add(mutableListOf())
        }

        val blossombase = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            blossombase.add(i)
        }
        for (i in 0 until nvertex) {
            blossombase.add(-1)
        }

        val blossomendps = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossomendps.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossomendps.add(mutableListOf())
        }

        val bestedge = mutableListOf<Int>()
        for (i in 0 until nvertex) {
            bestedge.add(-1)
        }
        for (i in 0 until nvertex) {
            bestedge.add(-1)
        }

        val blossombestedges = mutableListOf<MutableList<Int>>()
        for (i in 0 until nvertex) {
            blossombestedges.add(mutableListOf())
        }
        for (i in 0 until nvertex) {
            blossombestedges.add(mutableListOf())
        }

        val unusedblossoms = mutableListOf<Int>()
        for (i in nvertex until nvertex * 2) {
            unusedblossoms.add(i)
        }

        val dualvar = mutableListOf<Long>()
        for (i in 0 until nvertex) {
            dualvar.add(maxweight)
        }
        for (i in 0 until nvertex) {
            dualvar.add(0)
        }

        val allowedge = mutableListOf<Boolean>()
        for (i in 0 until nedges) {
            allowedge.add(false)
        }

        val queue = mutableListOf<Any>()

        fun slack(k: Int): Long {
            return (
                dualvar[edges[k].node1.toInt()] +
                    dualvar[edges[k].node2.toInt()] -
                    2 * edges[k].weight
                )
        }

        fun blossomLeaves(b: Int): MutableList<Int> {
            return if (b < nvertex) {
                mutableListOf(b)
            } else {
                blossomchilds[b].flatMap { t ->
                    if (t < nvertex) {
                        mutableListOf(b)
                    } else {
                        blossomLeaves(t)
                    }
                }.toMutableList()
            }
        }

        fun assignLabel(w: Int, t: Int, p: Int) {
            val b = inblossom[w]
            if (label[w] == 0 && label[b] == 0) {
                label[w] = t
                label[b] = t
                labelend[w] = p
                labelend[b] = p
                bestedge[w] = -1
                bestedge[b] = -1
                if (t == 1) {
                    queue.addAll(blossomLeaves(b))
                } else if (t == 2) {
                    val base = blossombase[b]
                    assert(mate[base] >= 0)
                    assignLabel(
                        endpoint[mate[base].toInt()].toInt(),
                        1,
                        mate[base].xor(1).toInt()
                    )
                }
            }
        }

        fun scanBlossom(parV: Int, parW: Int): Int {

            var v = parV
            var w = parW
            val path = mutableListOf<Int>()
            var base = -1
            while (v != -1 || w != -1) {
                var b = inblossom[v]
                if (label[b].and(4) != 0) {
                    base = blossombase[b]
                    return base
                }
                assert(label[b] == 1)
                path.add(b)
                label[b] = 5
                assert(labelend[b] == mate[blossombase[b]].toInt())
                if (labelend[b] == -1) {
                    v = -1
                } else {
                    v = endpoint[labelend[b]].toInt()
                    b = inblossom[v]
                    assert(label[b] == 2)
                    assert(labelend[b] >= 0)
                    v = endpoint[labelend[b]].toInt()
                }
                if (w != -1) {
                    v = w.also { w = v }
                }
                path.forEach {
                    label[it] = 1
                }
            }
            return base
        }

        fun addBlossom(base: Int, k: Int) {
            val edge = edges[k]
            var v = edge.node1.toInt()
            var w = edge.node2.toInt()
            val bb = inblossom[base]
            var bv = inblossom[v]
            var bw = inblossom[w]
            val b = unusedblossoms.removeLast()
            blossombase[b] = base
            blossomparent[b] = -1
            blossomparent[bb] = b
            val path = mutableListOf<Int>()
            blossomchilds[b] = path
            val endps = mutableListOf<Int>()
            blossomendps[b] = endps
            while (bv != bb) {
                blossomparent[bv] = b
                path.add(bv)
                endps.add(labelend[bv])
                assert(
                    label[bv] == 2 ||
                        (label[bv] == 1 && labelend[bv] == mate[blossombase[bv]].toInt())
                )
                assert(labelend[bv] >= 0)
                v = endpoint[labelend[bv]].toInt()
                bv = inblossom[v]
            }
            path.add(bv)
            path.reverse()
            endps.reverse()
            endps.add(2 * k)
            while (bw != bb) {
                blossomparent[bw] = b
                path.add(bw)
                endps.add(labelend[bw].xor(1))
                assert(
                    label[bw] == 2 ||
                        (label[bw] == 1 && labelend[bw] == mate[blossombase[bw]].toInt())
                )
                assert(labelend[bw] >= 0)
                w = endpoint[labelend[bw]].toInt()
                bw = inblossom[w]
            }
            assert(label[bb] == 1)
            label[b] = 1
            labelend[b] = labelend[bb]
            dualvar[b] = 0
            blossomLeaves(b).forEach {
                if (label[inblossom[it]] == 2) {
                    queue.add(it)
                }
                inblossom[it] = b
            }
            val bestedgeto = mutableListOf<Int>()
            for (i in 0 until nvertex) {
                bestedgeto.add(-1)
            }
            for (i in 0 until nvertex) {
                bestedgeto.add(-1)
            }
            path.forEach {
                val nblists: MutableList<MutableList<Int>> = if (blossombestedges[it].isEmpty()) {
                    mutableListOf(blossombestedges[it])
                } else {
                    blossomLeaves(it).map { blV ->
                        neighbend[blV].map { neighBlV ->
                            neighBlV.floorDiv(2)
                        }.toMutableList()
                    }.toMutableList()
                }
                nblists.forEach { nbList ->
                    nbList.forEach { intNbList ->
                        val edgeNb = edges[intNbList]
                        var i = edgeNb.node1.toInt()
                        var j = edgeNb.node2.toInt()
                        if (inblossom[j] == b) {
                            i = j.also { j = i }
                        }
                        val bj = inblossom[j]
                        if (bj != b && label[bj] == 1 &&
                            (bestedgeto[bj] == -1 || slack(intNbList) < slack(bestedgeto[bj]))
                        ) {
                            bestedgeto[bj] = k
                        }
                    }
                }
                blossombestedges[it] = mutableListOf()
                bestedge[it] = -1
            }
            blossombestedges[b] = bestedgeto.filter { it != -1 }.toMutableList()
            bestedge[b] = -1
            blossombestedges[b].forEach {
                if (bestedge[b] == -1 || slack(it) < slack(bestedge[b])) {
                    bestedge[b] = it
                }
            }
        }

        // Some sketchy
        fun expandBlossom(b: Int, endstage: Boolean) {

            blossomchilds[b].forEach { s ->
                blossomparent[s] = -1
                if (s < nvertex) {
                    inblossom[s] = s
                } else if (endstage && dualvar[s].toInt() == 0) {
                    expandBlossom(s, endstage)
                } else {
                    blossomLeaves(s).forEach { v ->
                        inblossom[v] = s
                    }
                }
            }

            if (!endstage && label[b] == 2) {
                assert(labelend[b] >= 0)
                val entrychild = inblossom[endpoint[labelend[b].xor(1)].toInt()]
                var j = blossomchilds[b].indexOf(entrychild)
                val jstep: Int
                val endptrick: Int
                if (j.and(1) != 0) {
                    j -= blossomchilds[b].size
                    jstep = 1
                    endptrick = 0
                } else {
                    jstep = -1
                    endptrick = 0
                }
                val p = labelend[b]
                while (j != 0) {
                    label[endpoint[p.xor(1)].toInt()] = 0
                    label[
                        endpoint[
                            blossomendps[b][j - endptrick]
                                .xor(endptrick)
                                .xor(1)
                        ].toInt()
                    ] = 0
                    assignLabel(endpoint[p.xor(1)].toInt(), 2, p)
                    allowedge[blossomendps[b][j - endptrick].floorDiv(2)] = true
                    j += jstep
                }
                var bv = blossomchilds[b][j]
                label[endpoint[p.xor(1)].toInt()] = 2
                label[bv] = 2
                labelend[endpoint[p.xor(1)].toInt()] = p
                labelend[bv] = p
                bestedge[bv] = -1
                j += jstep
                while (blossomchilds[b][j] != entrychild) {
                    bv = blossomchilds[b][j]
                    if (label[bv] == 1) {
                        j += jstep
                        continue
                    }
                    // Sketchy part
                    /*
                    for (v in blossomLeaves(bv)) {
                        if(label[v] != 0)
                            break
                    }
                    if(label[v] != 0) { // Gives error?
                        assert
                    }
                    */
                    for (v in blossomLeaves(bv)) {
                        if (label[v] != 0) { // Gives error?
                            assert(label[v] == 2)
                            assert(inblossom[v] == bv)
                            label[v] = 0
                            label[endpoint[mate[blossombase[bv]].toInt()].toInt()] = 0
                            assignLabel(v, 2, labelend[v])
                        }
                    }
                    j += jstep
                }
            }
            label[b] = -1
            labelend[b] = -1
            blossomchilds[b] = mutableListOf()
            blossomendps[b] = mutableListOf()
            blossombase[b] = -1
            blossombestedges[b] = mutableListOf()
            bestedge[b] = -1
            unusedblossoms.add(b)
        }

        fun augmentBlossom(b: Int, v: Int) {

            var t = v
            val jstep: Int
            val endptrick: Int
            while (blossomparent[t] != b) {
                t = blossomparent[t]
            }
            if (t >= nvertex) {
                augmentBlossom(t, v)
            }
            val i = blossomchilds[b].indexOf(t)
            var j = blossomchilds[b].indexOf(t)
            if (i.and(1) != 0) {
                j -= blossomchilds[b].size
                jstep = 1
                endptrick = 0
            } else {
                jstep = -1
                endptrick = -1
            }
            while (j != 0) {
                j += jstep
                t = blossomchilds[b][j]
                val p = blossomendps[b][j - endptrick].xor(endptrick)
                if (t >= nvertex) {
                    augmentBlossom(t, endpoint[p].toInt())
                }
                j += jstep
                t = blossomchilds[b][j]
                if (t >= nvertex) {
                    augmentBlossom(t, endpoint[p.xor(1)].toInt())
                }
                mate[endpoint[p].toInt()] = p.xor(1).toLong()
                mate[endpoint[p.xor(1)].toInt()] = p.toLong()
            }
            blossomchilds[b] =
                (
                    blossomchilds[b].slice(i until blossomchilds[b].size) +
                        blossomchilds[b].slice(0 until i).toMutableList()
                    ).toMutableList()
            blossomendps[b] =
                (
                    blossomendps[b].slice(i until blossomendps[b].size) +
                        blossomendps[b].slice(0 until i).toMutableList()
                    ).toMutableList()
            blossombase[b] = blossombase[blossomchilds[b][0]]
            assert(blossombase[b] == v)
        }

        fun augmentMatching(k: Int) {
            val edge = edges[k]
            val v = edge.node1.toInt()
            val w = edge.node2.toInt()
            val listPair = listOf(Pair(v, 2 * k + 1), Pair(w, 2 * k))
            listPair.forEach { (ls, lp) ->
                var s = ls
                var p = lp
                while (true) {
                    val bs = inblossom[s]
                    assert(label[bs] == 1)
                    assert(labelend[bs] == mate[blossombase[bs]].toInt())
                    if (bs >= nvertex) {
                        augmentBlossom(bs, s)
                    }
                    mate[s] = p.toLong()
                    if (labelend[bs] == -1) {
                        break
                    }
                    val t = endpoint[labelend[bs]].toInt()
                    val bt = inblossom[t]
                    assert(label[bt] == 2)
                    assert(labelend[bt] >= 0)
                    s = endpoint[labelend[bt]].toInt()
                    val j = endpoint[labelend[bt].xor(1)].toInt()
                    assert(blossombase[bt] == t)
                    if (bt >= nvertex) {
                        augmentBlossom(bt, j)
                    }
                    mate[j] = labelend[bt].toLong()
                    p = labelend[bt].xor(1)
                }
            }
        }

        return listOf()
    }
}

class GraphEdge(
    val node1: Long,
    val node2: Long,
    val weight: Long
)
