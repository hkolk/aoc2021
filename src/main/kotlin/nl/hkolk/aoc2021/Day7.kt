package nl.hkolk.aoc2021

import kotlin.math.absoluteValue

class Day7(input: List<String>) {
    private val positions = input[0].splitIgnoreEmpty(",").map { it.toInt() }

    private fun solve(fuelrate: (Int, Int) -> Int): Int {
        return (positions.minOrNull()!!..positions.maxOrNull()!!).fold(Int.MAX_VALUE){ lowest, pos ->
            minOf(lowest, positions.fold(0) { sum, element ->
                sum + fuelrate(element, pos)
            })
        }
    }
    fun solvePart1(): Int = solve { element, pos -> (element - pos).absoluteValue }
    fun solvePart2(): Int = solve { element, pos -> ((element - pos).absoluteValue downTo 0).sum() }
}