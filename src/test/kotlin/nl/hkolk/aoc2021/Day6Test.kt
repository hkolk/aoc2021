package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 6")
class Day6Test {

    val testInput =
        """
3,4,3,1,2
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day6.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day6(testInput).solvePart1()
            assertThat(answer).isEqualTo(5934)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day6(realInput).solvePart1()
            assertThat(answer).isEqualTo(350605)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day6(testInput).solvePart2()
            assertThat(answer).isEqualTo(26984457539L)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day6(realInput).solvePart2()
            assertThat(answer).isEqualTo(1592778185024L)
        }
    }
}