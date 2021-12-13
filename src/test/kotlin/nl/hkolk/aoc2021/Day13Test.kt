package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 13")
class Day13Test {

    val testInput =
        """
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day13.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day13(testInput).solvePart1()
            assertThat(answer).isEqualTo(17)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day13(realInput).solvePart1()
            assertThat(answer).isEqualTo(689)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day13(testInput).solvePart2()
            assertThat(answer).isEqualTo(16)
        }
        @Test // RLBCJGLU
        fun `Actual Answer`() {
            val answer = Day13(realInput).solvePart2()
            assertThat(answer).isEqualTo(91) // RLBCJGLU
        }
    }
}