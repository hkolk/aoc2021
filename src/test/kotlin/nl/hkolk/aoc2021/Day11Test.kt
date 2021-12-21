package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 11")
class Day11Test {

    val testInput =
        """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day11.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day11(testInput).solvePart1()
            assertThat(answer).isEqualTo(1656)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day11(realInput).solvePart1()
            assertThat(answer).isEqualTo(1755)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day11(testInput).solvePart2()
            assertThat(answer).isEqualTo(195)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day11(realInput).solvePart2()
            assertThat(answer).isEqualTo(212)
        }
    }
}