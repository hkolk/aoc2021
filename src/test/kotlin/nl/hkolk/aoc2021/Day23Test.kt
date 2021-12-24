package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 23")
class Day23Test {

    val testInput =
        """
#############
#...........#
###B#C#B#D###
  #A#D#C#A#
  #########
        """.trimIndent().split("\n")

    val realInput = Resources.resourceAsList("day23.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day23(testInput).solvePart1()
            assertThat(answer).isEqualTo(12521)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day23(realInput).solvePart1()
            assertThat(answer).isEqualTo(15338)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day23(testInput).solvePart2()
            assertThat(answer).isEqualTo(2758514936282235L)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day23(realInput).solvePart2()
            assertThat(answer).isEqualTo(1322825263376414L)
        }
    }
}