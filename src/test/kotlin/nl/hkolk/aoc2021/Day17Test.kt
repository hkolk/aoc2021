package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 17")
class Day17Test {

    val testInput =
        """
target area: x=20..30, y=-10..-5
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day17.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day17(testInput).solvePart1()
            assertThat(answer).isEqualTo(45)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day17(realInput).solvePart1()
            assertThat(answer).isEqualTo(7626)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day17(testInput).solvePart2()
            assertThat(answer).isEqualTo(112)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day17(realInput).solvePart2()
            assertThat(answer).isEqualTo(2032)
        }
    }
}