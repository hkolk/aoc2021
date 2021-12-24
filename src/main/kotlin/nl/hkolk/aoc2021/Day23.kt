package nl.hkolk.aoc2021

import java.util.*
import kotlin.math.abs

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
            println(locs)
            ret += "#"+(10..20).map { locs[it]?.type ?: "." }.joinToString("")+"#\n"
            ret += "###"+(0..3).map { locs[it]?.type ?: "." }.joinToString("#")+"###\n"
            ret += "  #"+(4..7).map { locs[it]?.type ?: "." }.joinToString("#")+"#\n"
            return "$ret  #########";

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
        fun inOwnRoom() = when(location) {
            in 0..1 -> type == "A"
            in 2..3 -> type == "B"
            in 4..5 -> type == "C"
            in 6..7 -> type == "D"
            else -> false
        }
        fun inHallway() = location in 8..20
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
            return listOf(from) + routeTo(12 + (from * 2), to)
        } else if (from in 4..7) {
            return listOf(from, from%4) + routeTo(12 + (from % 4 * 2), to)
        } else if(from in 10..20 && to in 10..20) {
            if(from > to) {
                return listOf(from) + routeTo(from-1, to)
            } else {
                return listOf(from) + routeTo(from+1, to)
            }
        } else if( to in 0..3) {
            return routeTo(from, 12+(to * 2)) + to
        } else if( to in 4..7) {
            return routeTo(from, 12+((to % 4) * 2)) + (to % 4) + to
        } else {
            throw IllegalStateException("From: $from, to: $to")
        }
    }

    val rooms = mapOf("A" to listOf(0, 4), "B" to listOf(1, 5), "C" to listOf(2, 6), "D" to listOf(3, 7))
    val reserved = setOf(12, 14, 16, 18)

    fun possibleMoves(room: RoomSystem): List<Pair<Amphipod, Int>> {
        var verbose = false
        val amphipods = room.amphipods
        if( amphipods.count{it.location in 10..20} == 1 &&
            amphipods.any { it.location == 13 && it.type == "B" } &&
            amphipods.any { it.location == 0 && it.type == "B" }
        ) {
            verbose = false
            //println(amphipods)
        }
        if( amphipods.count{it.location in 10..20} == 2 &&
            amphipods.any { it.location == 13 && it.type == "B" } &&
            amphipods.any { it.location == 15 && it.type == "C" } &&
            amphipods.any { it.location == 0 && it.type == "B" } &&
            amphipods.any { it.location == 6 && it.type == "C" }
        ) {
            verbose = true
            println(amphipods)
        }
        if(amphipods.count{it.location in 10..20} > 4) {
            if(verbose) { println("Too many in hallway") }
            return listOf()
        }


        if(amphipods.all { it.inOwnRoom() }) {
            println("Found solution!")
            return listOf()
        }

        val occupied = amphipods.map { it.location }.toSet()
        var possibleMoves = mutableListOf<Pair<Amphipod, Int>>()
        for (amphipod in amphipods) {
            // move from room to hallway
            val fromRoomToHallway = when (amphipod.location) {
                in 0..3 -> true
                in 4..7 -> !occupied.contains(amphipod.location % 4)
                else -> false
            }
            if (fromRoomToHallway) {
                if(!amphipod.inOwnRoom() || !amphipod.movedOut) {
                    val locations = (10..20).toList().filter { it !in reserved }
                    for (to in locations) {
                        val route = routeTo(amphipod.location, to)
                        if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {
                            if(verbose) {
                                println("Simulate move: $amphipod to $to")
                            }
                            possibleMoves += amphipod to to
                        }
                    }
                }
            }
            if (amphipod.location in 10..20) {
                val ownRoom = rooms[amphipod.type]!!
                val safeOwnRoom = amphipods.filter { it.location in ownRoom && it.type != amphipod.type }.isEmpty()
                if(verbose && amphipod.type == "C" && amphipod.location == 15) {
                    println("Safe own room: $safeOwnRoom ($ownRoom contains ${amphipods.filter { it.location in ownRoom}})")
                    println()
                }
                if(safeOwnRoom) {
                    for (to in rooms[amphipod.type]!!) {
                        val route = routeTo(amphipod.location, to)
                        if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {
                            if(verbose) {
                                println("Simulate move: $amphipod to $to")
                            }
                            possibleMoves += amphipod to to
                        }
                    }
                }
            }
        }
        if(verbose) {
            println(possibleMoves)
        }
        return possibleMoves
    }

    private fun heuristicPath(from: Amphipod, to:Int, distance: Int) = when (to) {
            in 4..7 -> distance * from.getCost()
            in 0..3 -> (distance * from.getCost())
            else -> throw IllegalStateException()
            //in 10..20 -> (distance * from.getCost()) * 3
            //else -> (distance * from.getCost()) * 4
        }

    private fun heuristic(goal: RoomSystem, next: RoomSystem): Int {
        var score = 0
        val elimination = next.amphipods.toMutableList()
        for(amphipod in goal.amphipods) {
            val element = elimination.first { it.type == amphipod.type }
            if(!element.inOwnRoom()) {
                score += heuristicPath(element, amphipod.location, routeTo(element.location, amphipod.location).size-1)
            }
            elimination.remove(element)
        }
        return score //+ next.amphipods.count { it.inHallway() } * 20000
    }
    val breakpoint = listOf(
        Amphipod("B", 0),
        Amphipod("C", 1),
        Amphipod("B", 13),
        Amphipod("D", 3),
        Amphipod("A", 4),
        Amphipod("D", 5),
        Amphipod("C", 6),
        Amphipod("A", 7)
    ).toRoomSystem()
    val breakpoint2 = listOf(
        Amphipod("B", 0),
        Amphipod("C", 15),
        Amphipod("B", 13),
        Amphipod("D", 3),
        Amphipod("A", 4),
        Amphipod("D", 5),
        Amphipod("C", 6),
        Amphipod("A", 7)
    ).toRoomSystem()
    private fun findShortestPath(start: RoomSystem, goal: RoomSystem): Int {

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


            for((amphipod, to) in possibleMoves(current)) { //current.adjacent().filter { map.containsKey(it) }.filterNot { closedVertices.contains(it) }) {
                val route = routeTo(amphipod.location, to)
                val newCost = costFromStart[current]!! + (route.size-1) * amphipod.getCost()
                val next = current.move(amphipod, to)
                if(current == breakpoint || current == breakpoint2) {
                    println("$amphipod to $to costs $newCost")
                    if(next ==  breakpoint2) {
                        println(newCost + heuristic(goal, next))
                    }
                }
                if(!costFromStart.containsKey(next) || newCost < costFromStart[next]!!) {
                    if(next == goal) {
                        println(newCost)
                    }
                    costFromStart[next] = newCost
                    estimatedTotalCost[next] = newCost + heuristic(goal, next)
                    openVertices.add(next to (newCost + heuristic(goal, next)))
                    cameFrom[next] = current
                }
            }
            if(round++ % 1000 == 0) {
                println("Peek: ${openVertices.peek()}")
                println(costFromStart.containsKey(breakpoint))
            }
            if(current == breakpoint || current == breakpoint2) {
                println(openVertices.peek())
            }
        }

        throw IllegalStateException("No path found")
    }




    fun makeMove(amphipods: List<Amphipod>, score: Int, depth:Int): List<Int> {
        var verbose = false
        if( amphipods.count{it.location in 10..20} == 1 &&
            amphipods.any { it.location == 13 && it.type == "B" } &&
            amphipods.any { it.location == 0 && it.type == "B" }
        ) {
            verbose = true
            print(" ".repeated(depth).joinToString(""))
            println(amphipods)
        }
        if( amphipods.count{it.location in 10..20} == 2 &&
            amphipods.any { it.location == 13 && it.type == "B" } &&
            amphipods.any { it.location == 15 && it.type == "C" } &&
            amphipods.any { it.location == 0 && it.type == "B" } &&
            amphipods.any { it.location == 6 && it.type == "C" }
                ) {
            verbose = true
            print(" ".repeated(depth).joinToString(""))
            println(amphipods)
        }
        if(depth > 10 || amphipods.count{it.location in 10..20} > 4) {
            return listOf()
        }


        if(amphipods.all { it.inOwnRoom() }) {
            println("Found solution!")
            return listOf(score)
        }

        val occupied = amphipods.map { it.location }.toSet()
        val scores = mutableListOf<Int>()
        for (amphipod in amphipods) {
            // move from room to hallway
            val fromRoomToHallway = when (amphipod.location) {
                in 0..3 -> true
                in 4..7 -> !occupied.contains(amphipod.location % 4)
                else -> false
            }
            if (fromRoomToHallway) {
                if(!amphipod.inOwnRoom() || !amphipod.movedOut) {
                    val locations = (10..20).toList().filter { it !in reserved }
                    for (to in locations) {
                        val route = routeTo(amphipod.location, to)
                        if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {
                            if(verbose) {
                                print(" ".repeated(depth).joinToString(""))
                                println("Simulate move: $amphipod to $to")
                            }
                            scores += makeMove(amphipods - amphipod + Amphipod(amphipod.type, to, true), score, depth + 1)
                        }
                    }
                }
            }
            if (amphipod.location in 10..20) {
                val ownRoom = rooms[amphipod.type]!!
                val safeOwnRoom = amphipods.filter { it.location in ownRoom && it.type != amphipod.type }.isEmpty()
                if(verbose && amphipod.type == "C" && amphipod.location == 15) {
                    print(" ".repeated(depth).joinToString(""))
                    println("Safe own room: $safeOwnRoom ($ownRoom contains ${amphipods.filter { it.location in ownRoom}})")
                    println()
                }
                if(safeOwnRoom) {
                    println(amphipod)
                    println(amphipods)
                }
                if(safeOwnRoom) {
                    for (to in rooms[amphipod.type]!!) {
                        val route = routeTo(amphipod.location, to)
                        if (route.intersect(occupied.filter { it != amphipod.location }.toSet()).isEmpty()) {
                            if(verbose) {
                                print(" ".repeated(depth).joinToString(""))
                                println("Simulate move: $amphipod to $to")
                            }
                            scores += makeMove(amphipods - amphipod + Amphipod(amphipod.type, to, true), score, depth + 1)
                        }
                    }
                }
            }
        }
        return scores

        // move from hallway to hallway
        // move from hallway to room
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
        val goal2 = listOf(
            Amphipod("A", 0, true),
            Amphipod("B", 1),
            Amphipod("C", 2),
            Amphipod("D", 7),
            Amphipod("A", 4),
            Amphipod("B", 5),
            Amphipod("C", 6),
            Amphipod("D", 3)
        ).toRoomSystem()
        println(goal == goal2)
        println(amphipods)

        // ((idx % 4) * 2) + (idx/4))
        return findShortestPath(amphipods, goal)
        //makeMove(amphipods, 0, 0)
        //println(routeTo(20, 6))

        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}