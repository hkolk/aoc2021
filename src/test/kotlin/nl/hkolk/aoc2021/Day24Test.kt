package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 24")
class Day24Test {

    val testInput =
        """
inp w
add z w
mod z 2
div w 2
add y w
mod y 2
div w 2
add x w
mod x 2
div w 2
mod w 2
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day24.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day24(realInput).solvePart1()
            assertThat(answer).isEqualTo(12521)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day24(realInput).solvePart1()
            assertThat(answer).isEqualTo(15338)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day24(testInput).solvePart2()
            assertThat(answer).isEqualTo(44169)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day24(realInput).solvePart2()
            assertThat(answer).isEqualTo(47064)
        }
    }
}