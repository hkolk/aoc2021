package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 25")
class Day25Test {

    val testInput =
        """
v...>>.vv>
.vv>>.vv..
>>.>v>...v
>>v>>.>.v.
v>v.vv.v..
>.>>..v...
.vv..>.>v.
v.v..>>v.v
....v..v.>
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day25.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day25(testInput).solvePart1()
            assertThat(answer).isEqualTo(58)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day25(realInput).solvePart1()
            assertThat(answer).isEqualTo(504)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day25(testInput).solvePart2()
            assertThat(answer).isEqualTo(1)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day25(realInput).solvePart2()
            assertThat(answer).isEqualTo(1)
        }
    }
}