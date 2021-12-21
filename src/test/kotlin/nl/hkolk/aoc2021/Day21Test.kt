package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 20")
class Day21Test {

    val testInput =
        """
Player 1 starting position: 4
Player 2 starting position: 8
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day21.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day21(testInput).solvePart1()
            assertThat(answer).isEqualTo(739785)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day21(realInput).solvePart1()
            assertThat(answer).isEqualTo(913560)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day21(testInput).solvePart2()
            assertThat(answer).isEqualTo(444356092776315)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day21(realInput).solvePart2()
            assertThat(answer).isEqualTo(110271560863819)
        }
    }
}