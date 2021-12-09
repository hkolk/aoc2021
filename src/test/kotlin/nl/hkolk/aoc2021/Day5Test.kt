package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 5")
class Day5Test {

    val testInput =
        """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day5.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day5(testInput).solvePart1()
            assertThat(answer).isEqualTo(5)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day5(realInput).solvePart1()
            assertThat(answer).isEqualTo(5698)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day5(testInput).solvePart2()
            assertThat(answer).isEqualTo(12)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day5(realInput).solvePart2()
            assertThat(answer).isEqualTo(15463)
        }
    }
}