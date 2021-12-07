package nl.hkolk.aoc2021

class Day6(val input: List<String>) {
    fun solve(days: Int): Long {
        var state = input[0].splitIgnoreEmpty(",").map { it.toInt() }.groupBy { it }.map { it.key to it.value.size.toLong() }.toMap().toMutableMap()
        for(day in 1..days) {
            //println(state)
            state = state.map {  if(it.key == 0) { 8 to it.value} else {it.key - 1 to it.value} }.toMap().toMutableMap()
            state[6] = (state[6]?:0) + (state[8]?:0)
            //println("Day $day: Sumfish: ${state.map { it.value }.sum()}")
        }
        return state.map { it.value }.sum()
    }
    fun solvePart1(): Long = solve(80)
    fun solvePart2(): Long = solve(256)
}