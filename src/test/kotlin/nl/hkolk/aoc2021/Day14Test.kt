package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 14")
class Day14Test {

    val testInput =
        """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day14.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day14(testInput).solvePart1()
            assertThat(answer).isEqualTo(1588)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day14(realInput).solvePart1()
            assertThat(answer).isEqualTo(2590)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day14(testInput).solvePart2()
            assertThat(answer).isEqualTo(2188189693529L)
        }
        @Test // RLBCJGLU
        fun `Actual Answer`() {
            val answer = Day14(realInput).solvePart2()
            assertThat(answer).isEqualTo(2875665202438L) // RLBCJGLU
        }
    }
}