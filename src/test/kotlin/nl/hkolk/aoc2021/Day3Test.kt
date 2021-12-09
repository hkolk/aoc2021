package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 3")
class Day3Test {

    val testInput =
        """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day3.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day3(testInput).solvePart1()
            assertThat(answer).isEqualTo(198)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day3(realInput).solvePart1()
            assertThat(answer).isEqualTo(3885894)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day3(testInput).solvePart2()
            assertThat(answer).isEqualTo(230)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day3(realInput).solvePart2()
            assertThat(answer).isEqualTo(4375225)
        }
    }
}