package nl.hkolk.aoc2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*


@DisplayName("Day 2")
class Day2Test {

    val testInput = """forward 5
down 5
forward 8
up 3
down 8
forward 2""".splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day2.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day2(testInput).solvePart1()
            assertThat(answer).isEqualTo(150)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day2(realInput).solvePart1()
            assertThat(answer).isEqualTo(2117664)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day2(testInput).solvePart2()
            assertThat(answer).isEqualTo(900)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day2(realInput).solvePart2()
            assertThat(answer).isEqualTo(2073416724)
        }
    }
}