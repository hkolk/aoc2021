package nl.hkolk.aoc2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*


@DisplayName("Day 1")
class Day1Test {

    val testInput = listOf("199",
            "200",
            "208",
            "210",
            "200",
            "207",
            "240",
            "269",
            "260",
            "263")
    val realInput = Resources.resourceAsList("day1.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day1(testInput).solvePart1()
            assertThat(answer).isEqualTo(514_579)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day1(realInput).solvePart1()
            assertThat(answer).isEqualTo(876_459)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day1(testInput).solvePart2()
            assertThat(answer).isEqualTo(241_861_950)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day1(realInput).solvePart2()
            assertThat(answer).isEqualTo(116_168_640)
        }
    }
}