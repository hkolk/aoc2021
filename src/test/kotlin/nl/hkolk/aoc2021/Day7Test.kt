package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 7")
class Day7Test {

    val testInput =
        """
16,1,2,0,4,2,7,1,2,14
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day7.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day7(testInput).solvePart1()
            assertThat(answer).isEqualTo(37)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day7(realInput).solvePart1()
            assertThat(answer).isEqualTo(343468)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day7(testInput).solvePart2()
            assertThat(answer).isEqualTo(168)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day7(realInput).solvePart2()
            assertThat(answer).isEqualTo(96086265)
        }
    }
}