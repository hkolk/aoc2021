package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 10")
class Day10Test {

    val testInput =
        """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().splitIgnoreEmpty("\n")
    val realInput = Resources.resourceAsList("day10.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day10(testInput).solvePart1()
            assertThat(answer).isEqualTo(26397)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day10(realInput).solvePart1()
            assertThat(answer).isEqualTo(464991)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day10(testInput).solvePart2()
            assertThat(answer).isEqualTo(288957L)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day10(realInput).solvePart2()
            assertThat(answer).isEqualTo(3_662_008_566L)
        }
    }
}