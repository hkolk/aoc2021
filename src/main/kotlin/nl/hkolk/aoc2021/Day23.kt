package nl.hkolk.aoc2021

import java.util.*

class Day23(val input: List<String>) {

    class RoomSystem(val amphipods: List<Amphipod>) {

        fun move(amphipod: Amphipod, to:Int): RoomSystem = RoomSystem(amphipods - amphipod + Amphipod(amphipod.type, to, true))

        override fun equals(other: Any?): Boolean {
            if(other is RoomSystem && this.amphipods.size == other.amphipods.size) {
                return amphipods.all { other.amphipods.contains(it) }
            }
            return false
        }
        override fun toString(): String = amphipods.sortedBy { it.location }.toString()
        override fun hashCode(): Int {
            return amphipods.sortedBy { it.location }.toList().hashCode()
        }

        fun nice(): String {
            var ret = "#############\n"
            val locs = amphipods.associateBy { it.location }
            ret += "#"+(20..30).map { locs[it]?.type ?: "." }.joinToString("")+"#\n"
            ret += "###"+(0..3).map { locs[it]?.type ?: "." }.joinToString("#")+"###\n"
            ret += "  #"+(4..7).map { locs[it]?.type ?: "." }.joinToString("#")+"#\n"
            ret += "  #"+(8..11).map { locs[it]?.type ?: "." }.joinToString("#")+"#\n"
            ret += "  #"+(12..15).map { locs[it]?.type ?: "." }.joinToString("#")+"#\n"
            return "$ret  #########\n";

        }
    }
    fun List<Amphipod>.toRoomSystem(): RoomSystem = RoomSystem(this)

    data class Amphipod(val type: String, val location: Int, val movedOut:Boolean = false) {
        fun getCost() = when(type) {
            "A" -> 1
            "B" -> 10
            "C" -> 100
            "D" -> 1000
            else -> throw IllegalStateException()
        }
        fun inOwnRoom() = when(location % 4) {
            0 -> type == "A"
            1 -> type == "B"
            2 -> type == "C"
            3 -> type == "D"
            else -> false
        }
        override fun equals(other: Any?): Boolean {
            if(other is Amphipod) {
//                println("$type == ${other.type} && $location == ${other.location}")
                return (type == other.type && location == other.location )
            }
            return super.equals(other)
        }
    }

    private fun routeTo(from:Int, to:Int): List<Int> {
        if(from == to) {
            return listOf(to)
        } else if (from in 0..3) {
            return listOf(from) + routeTo(22 + (from * 2), to)
        } else if (from in 4..7) {
            return listOf(from, from%4) + routeTo(22 + (from % 4 * 2), to)
        } else if (from in 8..11) {
            return listOf(from, (from%4)+4, from%4) + routeTo(22 + (from % 4 * 2), to)
        } else if (from in 12..15) {
            return listOf(from, (from%4)+8, (from%4)+4, from%4) + routeTo(22 + (from % 4 * 2), to)
        } else if(from in 20..30 && to in 20..30) {
            if(from > to) {
                return listOf(from) + routeTo(from-1, to)
            } else {
                return listOf(from) + routeTo(from+1, to)
            }
        } else if( to in 0..3) {
            return routeTo(from, 22+(to * 2)) + to
        } else if( to in 4..7) {
            return routeTo(from, 22+((to % 4) * 2)) + (to % 4) + to
        } else if( to in 8..11) {
            return routeTo(from, 22+((to % 4) * 2)) + (to % 4) + ((to % 4) + 4) + to
        } else if( to in 12..15) {
            return routeTo(from, 22+((to % 4) * 2)) + (to % 4) + ((to % 4) + 4) + ((to % 4) + 8) +to
        } else {
            throw IllegalStateException("From: $from, to: $to")
        }
    }

    val reserved = setOf(22, 24, 26, 28)

    fun possibleMoves(room: RoomSystem, rooms: Map<String,List<Int>>): List<Pair<Amphipod, Int>> {
        val amphipods = room.amphipods

        if(amphipods.all { it.inOwnRoom() }) {
            return listOf()
        }

        val occupied = amphipods.map { it.location }.toSet()
        val possibleMoves = mutableListOf<Pair<Amphipod, Int>>()
        for (amphipod in amphipods) {
            // move from room to hallway
            val fromRoomToHallway = when (amphipod.location) {
                in 0..3 -> true
                in 4..7 -> !occupied.contains(amphipod.location % 4)
                in 8..11 -> !occupied.contains(amphipod.location % 4) && !occupied.contains((amphipod.location % 4)+4)
                in 12..15 -> !occupied.contains(amphipod.location % 4) && !occupied.contains((amphipod.location % 4)+4) && !occupied.contains((amphipod.location % 4)+8)
                else -> false
            }
            if (fromRoomToHallway) {
                if(!amphipod.inOwnRoom() || !amphipod.movedOut) {
                    val locations = (20..30).toList().filter { it !in reserved }
                    for (to in locations) {
                        val route = routeTo(amphipod.location, to)
                        if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {
                            possibleMoves += amphipod to to
                        }
                    }
                }
            }
            if (amphipod.location in 20..30) {
                val ownRoom = rooms[amphipod.type]!!
                val safeOwnRoom = amphipods.filter { it.location in ownRoom && it.type != amphipod.type }.isEmpty()

                if(safeOwnRoom) {
                    val to = rooms[amphipod.type]!!.filter { !occupied.contains(it) }.maxOf{it}
                    val route = routeTo(amphipod.location, to)
                    if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {

                        possibleMoves += amphipod to to
                    }
                }
            }
        }
        return possibleMoves
    }

    private fun heuristicPath(from: Amphipod, to:Int, distance: Int) = distance * from.getCost()
    private fun heuristicPathOld(from: Amphipod, to:Int, distance: Int) = when (to) {
            in 4..7 -> distance * from.getCost()
            in 0..3 -> (distance * from.getCost())
            else -> throw IllegalStateException()
            //in 20..30 -> (distance * from.getCost()) * 3
            //else -> (distance * from.getCost()) * 4
        }

    private fun heuristic(goal: RoomSystem, next: RoomSystem, verbose: Boolean = false): Int {
        var score = 0
        val elimination = goal.amphipods.toMutableList()
        for(amphipod in next.amphipods) {
            val element = elimination.first { it.type == amphipod.type }
            if(!element.inOwnRoom()) {
                score += heuristicPath(element, amphipod.location, routeTo(element.location, amphipod.location).size-1)
            }
            elimination.remove(element)
        }
        return score //+ next.amphipods.count { it.inHallway() } * 20000
    }

    val breakpoint = listOf(
        Amphipod("A", 20),
        Amphipod("A", 21),
        Amphipod("A", 29),
        Amphipod("D", 30),
        Amphipod("B", 0),
        Amphipod("D", 4),
        Amphipod("D", 8),
        Amphipod("A", 12),
        Amphipod("B", 5),
        Amphipod("B", 9),
        Amphipod("B", 13),
        Amphipod("C", 2),
        Amphipod("C", 6),
        Amphipod("C", 10),
        Amphipod("C", 14),
        Amphipod("D", 15),
    ).toRoomSystem()

    private fun findShortestPath(start: RoomSystem, goal: RoomSystem, rooms: Map<String, List<Int>>): Int {

        fun generatePath(currentPos: RoomSystem, cameFrom: Map<RoomSystem, RoomSystem>): List<RoomSystem> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        //val openVertices = mutableSetOf(start)
        val openVertices = PriorityQueue<Pair<RoomSystem, Int>>(compareBy { it.second })
        openVertices.add(start to 0)
        val closedVertices = mutableSetOf<RoomSystem>()
        val costFromStart = mutableMapOf(start to 0)
        val cameFrom = mutableMapOf<RoomSystem, RoomSystem>()

        val estimatedTotalCost = mutableMapOf(start to heuristic(goal, start))
        var round = 1
        while(openVertices.isNotEmpty()) {
            val current = openVertices.remove().first
            if(current == goal) {
                println(current)
                println(costFromStart[current])
                val path = generatePath(current, cameFrom)
                path.forEach {
                    println("Cost: ${costFromStart[it]}")
                    println(it.nice())
                }
                return costFromStart[current]!!
                // keep going
                //return Pair(path, estimatedTotalCost.getValue(goal)) // First Route to finish will be optimum route
            }
            closedVertices.add(current)


            for((amphipod, to) in possibleMoves(current, rooms)) { //current.adjacent().filter { map.containsKey(it) }.filterNot { closedVertices.contains(it) }) {
                val route = routeTo(amphipod.location, to)
                val newCost = costFromStart[current]!! + (route.size-1) * amphipod.getCost()
                val next = current.move(amphipod, to)

                if(!costFromStart.containsKey(next) || newCost < costFromStart[next]!!) {
                    if(current == breakpoint) {
                        if(amphipod.location == 0) {
                            val asdf = heuristic(goal, next, true)
                            println("To: $to, cost: $newCost, $asdf")
                        }
                    }

                    costFromStart[next] = newCost
                    estimatedTotalCost[next] = newCost + heuristic(goal, next)
                    openVertices.add(next to (newCost + heuristic(goal, next)))
                    cameFrom[next] = current
                }
            }
            /*if(round++ % 1000 == 0) {
                print(current.nice())
            }

             */
        }

        throw IllegalStateException("No path found")
    }


    fun solvePart1(): Int {

        val rawRooms = input.drop(2).take(2).flatMap {  it.splitIgnoreEmpty(" ", "#", ".") }
        val amphipods = rawRooms.mapIndexed { idx, name -> Amphipod(name, idx)}.toRoomSystem()
        val goal = listOf(
            Amphipod("A", 0),
            Amphipod("B", 1),
            Amphipod("C", 2),
            Amphipod("D", 3),
            Amphipod("A", 4),
            Amphipod("B", 5),
            Amphipod("C", 6),
            Amphipod("D", 7)
        ).toRoomSystem()

        val rooms = mapOf("A" to listOf(0, 4), "B" to listOf(1, 5), "C" to listOf(2, 6), "D" to listOf(3, 7))

        return findShortestPath(amphipods, goal, rooms)

    }
    fun solvePart2(): Int {
        val rawRooms = input.drop(2).take(2).map {  it.splitIgnoreEmpty(" ", "#", ".") }
        val injected = listOf(rawRooms[0], listOf("D", "C", "B", "A"), listOf("D", "B", "A", "C"), rawRooms[1]).flatten()
        val amphipods = injected.mapIndexed { idx, name -> Amphipod(name, idx)}.toRoomSystem()
        val goal = listOf(
            Amphipod("A", 0),
            Amphipod("B", 1),
            Amphipod("C", 2),
            Amphipod("D", 3),
            Amphipod("A", 4),
            Amphipod("B", 5),
            Amphipod("C", 6),
            Amphipod("D", 7),
            Amphipod("A", 8),
            Amphipod("B", 9),
            Amphipod("C", 10),
            Amphipod("D", 11),
            Amphipod("A", 12),
            Amphipod("B", 13),
            Amphipod("C", 14),
            Amphipod("D", 15),
        ).toRoomSystem()

        val rooms = mapOf("A" to listOf(0, 4, 8, 12), "B" to listOf(1, 5, 9, 13), "C" to listOf(2, 6, 10, 14), "D" to listOf(3, 7, 11, 15))

        return findShortestPath(amphipods, goal, rooms)
    }
}