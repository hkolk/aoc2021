package nl.hkolk.aoc2021

class Day23(val input: List<String>) {

    data class Amphipod(val type: String, val location: Int, var movedOut:Boolean = false) {
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
        val amphipods = rawRooms.mapIndexed { idx, name -> Amphipod(name, idx)}
        println(amphipods)
        // ((idx % 4) * 2) + (idx/4))
        makeMove(amphipods, 0, 0)
        //println(routeTo(20, 6))

        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }
}