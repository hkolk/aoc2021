package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 12")
class Day12Test {

    val testInput =
        """
start-A
start-b
A-c
A-b
b-d
A-end
b-end
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day12.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day12(testInput).solvePart1()
            assertThat(answer).isEqualTo(10)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day12(realInput).solvePart1()
            assertThat(answer).isEqualTo(4912)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day12(testInput).solvePart2()
            assertThat(answer).isEqualTo(36)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day12(realInput).solvePart2()
            assertThat(answer).isEqualTo(150004)
        }
    }
}