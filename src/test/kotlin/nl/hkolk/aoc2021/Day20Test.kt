package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 20")
class Day20Test {

    val testInput =
        """
..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

#..#.
#....
##..#
..#..
..###
        """.trimIndent().split("\n")
    val realInput = Resources.resourceAsList("day20.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day20(testInput).solvePart1()
            assertThat(answer).isEqualTo(35)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day20(realInput).solvePart1()
            assertThat(answer).isEqualTo(5498)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day20(testInput).solvePart2()
            assertThat(answer).isEqualTo(3351)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day20(realInput).solvePart2()
            assertThat(answer).isEqualTo(16014)
        }
    }
}