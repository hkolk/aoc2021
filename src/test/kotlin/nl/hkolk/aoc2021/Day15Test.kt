package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 15")
class Day15Test {

    val testInput =
        """
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day15.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day15(testInput).solvePart1()
            assertThat(answer).isEqualTo(40)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day15(realInput).solvePart1()
            assertThat(answer).isEqualTo(602)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day15(testInput).solvePart2()
            assertThat(answer).isEqualTo(315)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day15(realInput).solvePart2()
            assertThat(answer).isEqualTo(2935)
        }
    }
}