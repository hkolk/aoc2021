package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 9")
class Day9Test {

    val testInput =
        """
2199943210
3987894921
9856789892
8767896789
9899965678
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day9.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day9(testInput).solvePart1()
            assertThat(answer).isEqualTo(15)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day9(realInput).solvePart1()
            assertThat(answer).isEqualTo(480)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day9(testInput).solvePart2()
            assertThat(answer).isEqualTo(1134)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day9(realInput).solvePart2()
            assertThat(answer).isEqualTo(1045660)
        }
    }
}