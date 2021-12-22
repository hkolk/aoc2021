package nl.hkolk.aoc2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("Day 22")
class Day22Test {

    val testInput =
        """
on x=-20..26,y=-36..17,z=-47..7
on x=-20..33,y=-21..23,z=-26..28
on x=-22..28,y=-29..23,z=-38..16
on x=-46..7,y=-6..46,z=-50..-1
on x=-49..1,y=-3..46,z=-24..28
on x=2..47,y=-22..22,z=-23..27
on x=-27..23,y=-28..26,z=-21..29
on x=-39..5,y=-6..47,z=-3..44
on x=-30..21,y=-8..43,z=-13..34
on x=-22..26,y=-27..20,z=-29..19
off x=-48..-32,y=26..41,z=-47..-37
on x=-12..35,y=6..50,z=-50..-2
off x=-48..-32,y=-32..-16,z=-15..-5
on x=-18..26,y=-33..15,z=-7..46
off x=-40..-22,y=-38..-28,z=23..41
on x=-16..35,y=-41..10,z=-47..6
off x=-32..-23,y=11..30,z=-14..3
on x=-49..-5,y=-3..45,z=-29..18
off x=18..30,y=-20..-8,z=-3..13
on x=-41..9,y=-7..43,z=-33..15
on x=-54112..-39298,y=-85059..-49293,z=-27449..7877
on x=967..23432,y=45373..81175,z=27513..53682
        """.trimIndent().split("\n")

    val testInput2 = """
on x=0..99,y=0..99,z=0..99
off x=0..49,y=0..99,z=0..99
on x=0..49,y=0..49,z=0..99
    """.trimIndent().split("\n")

    val realInput = Resources.resourceAsList("day22.txt")

    @Nested
    @DisplayName("Part 1")
    inner class Part1 {
        @Test
        fun `Matches Example`() {
            val answer = Day22(testInput).solvePart1()
            assertThat(answer).isEqualTo(590784)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day22(realInput).solvePart1()
            assertThat(answer).isEqualTo(561032)
        }
    }
    @Nested
    @DisplayName("Part 2")
    inner class Part2 {
        @Test
        fun `Matches Example`() {
            val answer = Day22(testInput2).solvePart2()
            assertThat(answer).isEqualTo(590784)
        }
        @Test
        fun `Actual Answer`() {
            val answer = Day22(realInput).solvePart2()
            assertThat(answer).isEqualTo(110271560863819)
        }
    }
}