package nl.hkolk.aoc2021

import kotlin.math.absoluteValue

class Day7(input: List<String>) {
    private val positions = input[0].splitIgnoreEmpty(",").map { it.toInt() }

    fun solve(fuelrate: (Int, Int) -> Int): Int {
        val min = positions.minOrNull()?:0
        val max = positions.maxOrNull()?:0
        val distances = (min..max).map { pos -> pos to
                positions.fold(0) { sum, element ->
                    sum + fuelrate(element, pos)
                }
        }
        return distances.map { it.second }.minOrNull()?:0
    }
    fun solvePart1(): Int = solve { element, pos -> (element - pos).absoluteValue }
    fun solvePart2(): Int = solve { element, pos -> ((element - pos).absoluteValue downTo 0).sum() }
}