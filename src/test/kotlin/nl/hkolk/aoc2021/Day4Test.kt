package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 4")
class Day4Test {

    val testInput = Resources.resourceAsList("day4_test.txt")
    val realInput = Resources.resourceAsList("day4.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day4(testInput).solvePart1()
            assertThat(answer).isEqualTo(4512)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day4(realInput).solvePart1()
            assertThat(answer).isEqualTo(63424)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day4(testInput).solvePart2()
            assertThat(answer).isEqualTo(1924)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day4(realInput).solvePart2()
            assertThat(answer).isEqualTo(23541)
        }
    }
}