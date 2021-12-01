package nl.hkolk.aoc2020

class Day1(input: List<String>) {
    private val input = input.map { it.toInt() }

    fun solve(windowsize:Int = 1): Int {
        var increments = 0
        input.windowed(windowsize+1) {
            val prev = it.take(windowsize).sum()
            val next = it.drop(1).take(windowsize).sum()
            if(next > prev) {
                increments++
            }
        }
        return increments
    }
    fun solvePart1(): Int = solve(1)
    fun solvePart2(): Int = solve(3)
}