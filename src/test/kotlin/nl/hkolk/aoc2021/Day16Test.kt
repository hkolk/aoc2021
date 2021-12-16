package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 16")
class Day16Test {

    val testInput =
        """
D2FE28
38006F45291200
EE00D40C823060
8A004A801A8002F478
620080001611562C8802118E34
C0015000016115A2E0802F182340
A0016C880162017C3686B18A3D4780
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day16.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day16(testInput).solvePart1()
            assertThat(answer).isEqualTo(6)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day16(realInput).solvePart1()
            assertThat(answer).isEqualTo(969)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day16(testInput).solvePart2()
            assertThat(answer).isEqualTo(2021L)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day16(realInput).solvePart2()
            assertThat(answer).isEqualTo(124921618408L)
        }
    }
}