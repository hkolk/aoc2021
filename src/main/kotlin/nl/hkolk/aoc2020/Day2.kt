package nl.hkolk.aoc2020

class Day2(input: List<String>) {
    private val moves = input.map {
        val parts = it.splitIgnoreEmpty(" ")
        Pair(parts[0], parts[1].toInt())
    }

    fun solvePart1(): Int {
        var hpos = 0
        var depth = 0
        for(move in moves){
            when(move.first) {
                "forward" -> hpos += move.second
                "down" -> depth += move.second
                "up" -> depth -= move.second
            }
        }
        return hpos * depth
    }
    fun solvePart2(): Int {
        var hpos = 0
        var depth = 0
        var aim = 0
        for(move in moves){
            when(move.first) {
                "forward" -> {
                    hpos += move.second
                    depth += aim * move.second
                }
                "down" -> aim += move.second
                "up" -> aim -= move.second
            }
        }
        return hpos * depth
    }
}