package nl.hkolk.aoc2020

class Day1(input: List<String>) {
    private val input = input.map { it.toInt() }

    fun solvePart1Old(): Int {
        var increments = 0
        var prev = 0
        for(x in input) {
            if(x > prev && prev != 0) {
               increments++
            }
            prev = x
        }
        return increments
    }
    fun solvePart1(): Int {
        var increments = 0
        input.windowed(2) {
            if(it[1] > it[0]) {
                increments++
            }
        }
        return increments
    }
    fun solvePart2(): Int {
        var increments = 0
        input.windowed(4) {
            val prev = it.take(3).sum()
            val next = it.drop(1).take(3).sum()
            if(next > prev) {
                increments++
            }
        }
        return increments
    }
}