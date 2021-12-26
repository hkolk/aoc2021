package nl.hkolk.aoc2021

import java.lang.IllegalStateException
import kotlinx.coroutines.*
import kotlin.math.absoluteValue

class Day24(val input: List<String>) {

    val matrix = listOf(
        13L to 6L, // = 13-0
        11L to 11L, // =12-1
        12L to 5L, // =11-2
        10L to 6L, // = 8-3
        14L to 8L,  // = 5-4
        -1L to 14L, // = 4-5
        14L to 9L, // = 7-6
        -16L to 4L, // = 6-7
        -8L to 7L, // = 3-8
        12L to 13L,  // = 10-9
        -16L to 11L,  // = 9-10
        -13L to 11L,  // = 2-11
        -6L to 6L,  // =1-12
        -6L to 1L // = 0-13
    )

    private fun generateSequences(): List<List<Long>> {

        val matches = listOf(
            13 to 0,
            12 to 1,
            11 to 2,
            8 to 3,
            5 to 4,
            4 to 5,
            7 to 6,
            6 to 7,
            3 to 8,
            10 to 9,
            9 to 10,
            2 to 11,
            1 to 12,
            0 to 13
        )

        val mappings = matches.sortedBy { it.first }.map {
            val firstVars = matrix[it.first]
            val secondVars = matrix[it.second]
            val diff = if(firstVars.first>=10L) {
                firstVars.second + secondVars.first
            } else {
                0 - firstVars.first - secondVars.second
            }
            //println("$it, $diff: $firstVars, $secondVars")
            it.first to diff
        }.toMap()

        return mappings.map {  (digitPost, diff) ->
            if(diff >= 0)  {
                (1..(9-diff)).toList()
            } else {
                (9 downTo diff.absoluteValue+1).toList()
            }
        }
    }

    fun solvePart1(): Long = generateSequences().map { it.maxOf { it } }.joinToString("").toLong()
    fun solvePart2(): Long = generateSequences().map { it.minOf { it } }.joinToString("").toLong()

    /***
     * Helper, debugging, testing and investigation code below
     */

    class Program() {

        fun run(originalInput: String): Long {
            var z = 0L

            fun duplicate3(L5Add: Long, L15Add: Long, input: Long) {
                var x = (z % 26L) + L5Add // 1, 2, 3, 4

                if(L5Add<0) {
                    z /=26L // 4
                }
                x = (x != input).toLong()  // 6+7
                z *= (x * 25L) + 1L  // 8 + 9 + 10 + 11 + 12
                z += (input + L15Add) * x  // 13, 14, 15, 16, 17
            }

            val input = originalInput.map { it.digitToInt() }.toMutableList()

            val verbose = false
            var iteration = 1
            duplicate3(13L, 6L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(11L, 11L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(12L, 5L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(10L, 6L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(14L, 8L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-1L, 14L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(14L, 9L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-16L, 4L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-8L, 7L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(12L, 13L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-16L, 11L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-13L, 11L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-6L, 6L, input.removeFirst().toLong())
            if(verbose) { println("Iteration ${iteration++}: $z") }

            duplicate3(-6L, 1L, input.removeFirst().toLong())

            return z
        }


        fun originalProgram(originalInput: String): Long {
            val input = originalInput.map { it.digitToInt() }.toMutableList()
            var w = 0L
            var x = 0L
            var y = 0L
            var z = 0L

            var iteration = 1

            w = input.removeFirst().toLong() // 0
            x *= 0L  // 1
            x += z  // 2
            x %= 26L  // 3
            z /= 1L  // 4
            x += 13L  // 5
            x = (x == w).toLong()  // 6
            x = (x == 0L).toLong()  // 7
            y *= 0L  // 8
            y += 25L  // 9
            y *= x  // 10
            y += 1L  // 11
            z *= y  // 12
            y *= 0L  // 13
            y += w  // 14
            y += 6L  // 15
            y *= x  // 16
            z += y  // 17
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 18
            x *= 0L  // 19
            x += z  // 20
            x %= 26L  // 21
            z /= 1L  // 22
            x += 11L  // 23
            x = (x == w).toLong()  // 24
            x = (x == 0L).toLong()  // 25
            y *= 0L  // 26
            y += 25L  // 27
            y *= x  // 28
            y += 1L  // 29
            z *= y  // 30
            y *= 0L  // 31
            y += w  // 32
            y += 11L  // 33
            y *= x  // 34
            z += y  // 35
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 36
            x *= 0L  // 37
            x += z  // 38
            x %= 26L  // 39
            z /= 1L  // 40
            x += 12L  // 41
            x = (x == w).toLong()  // 42
            x = (x == 0L).toLong()  // 43
            y *= 0L  // 44
            y += 25L  // 45
            y *= x  // 46
            y += 1L  // 47
            z *= y  // 48
            y *= 0L  // 49
            y += w  // 50
            y += 5L  // 51
            y *= x  // 52
            z += y  // 53
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 54
            x *= 0L  // 55
            x += z  // 56
            x %= 26L  // 57
            z /= 1L  // 58
            x += 10L  // 59
            x = (x == w).toLong()  // 60
            x = (x == 0L).toLong()  // 61
            y *= 0L  // 62
            y += 25L  // 63
            y *= x  // 64
            y += 1L  // 65
            z *= y  // 66
            y *= 0L  // 67
            y += w  // 68
            y += 6L  // 69
            y *= x  // 70
            z += y  // 71
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 72
            x *= 0L  // 73
            x += z  // 74
            x %= 26L  // 75
            z /= 1L  // 76
            x += 14L  // 77
            x = (x == w).toLong()  // 78
            x = (x == 0L).toLong()  // 79
            y *= 0L  // 80
            y += 25L  // 81
            y *= x  // 82
            y += 1L  // 83
            z *= y  // 84
            y *= 0L  // 85
            y += w  // 86
            y += 8L  // 87
            y *= x  // 88
            z += y  // 89
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 90
            x *= 0L  // 91
            x += z  // 92
            x %= 26L  // 93
            z /= 26L  // 94
            x += -1L  // 95
            x = (x == w).toLong()  // 96
            x = (x == 0L).toLong()  // 97
            y *= 0L  // 98
            y += 25L  // 99
            y *= x  // 100
            y += 1L  // 101
            z *= y  // 102
            y *= 0L  // 103
            y += w  // 104
            y += 14L  // 105
            y *= x  // 106
            z += y  // 107
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 108
            x *= 0L  // 109
            x += z  // 110
            x %= 26L  // 111
            z /= 1L  // 112
            x += 14L  // 113
            x = (x == w).toLong()  // 114
            x = (x == 0L).toLong()  // 115
            y *= 0L  // 116
            y += 25L  // 117
            y *= x  // 118
            y += 1L  // 119
            z *= y  // 120
            y *= 0L  // 121
            y += w  // 122
            y += 9L  // 123
            y *= x  // 124
            z += y  // 125
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 126
            x *= 0L  // 127
            x += z  // 128
            x %= 26L  // 129
            z /= 26L  // 130
            x += -16L  // 131
            x = (x == w).toLong()  // 132
            x = (x == 0L).toLong()  // 133
            y *= 0L  // 134
            y += 25L  // 135
            y *= x  // 136
            y += 1L  // 137
            z *= y  // 138
            y *= 0L  // 139
            y += w  // 140
            y += 4L  // 141
            y *= x  // 142
            z += y  // 143
            println("Iteration ${iteration++}: $z") // 8

            w = input.removeFirst().toLong() // 144
            x *= 0L  // 145
            x += z  // 146
            x %= 26L  // 147
            z /= 26L  // 148
            x += -8L  // 149
            x = (x == w).toLong()  // 150
            x = (x == 0L).toLong()  // 151
            y *= 0L  // 152
            y += 25L  // 153
            y *= x  // 154
            y += 1L  // 155
            z *= y  // 156
            y *= 0L  // 157
            y += w  // 158
            y += 7L  // 159
            y *= x  // 160
            z += y  // 161
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 162
            x *= 0L  // 163
            x += z  // 164
            x %= 26L  // 165
            z /= 1L  // 166
            x += 12L  // 167
            x = (x == w).toLong()  // 168
            x = (x == 0L).toLong()  // 169
            y *= 0L  // 170
            y += 25L  // 171
            y *= x  // 172
            y += 1L  // 173
            z *= y  // 174
            y *= 0L  // 175
            y += w  // 176
            y += 13L  // 177
            y *= x  // 178
            z += y  // 179
            println("Iteration ${iteration++}: $z") // 10

            w = input.removeFirst().toLong() // 180
            x *= 0L  // 181
            x += z  // 182
            x %= 26L  // 183
            z /= 26L  // 184
            x += -16L  // 185
            x = (x == w).toLong()  // 186
            x = (x == 0L).toLong()  // 187
            y *= 0L  // 188
            y += 25L  // 189
            y *= x  // 190
            y += 1L  // 191
            z *= y  // 192
            y *= 0L  // 193
            y += w  // 194
            y += 11L  // 195
            y *= x  // 196
            z += y  // 197
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 198
            x *= 0L  // 199
            x += z  // 200
            x %= 26L  // 201
            z /= 26L  // 202
            x += -13L  // 203
            x = (x == w).toLong()  // 204
            x = (x == 0L).toLong()  // 205
            y *= 0L  // 206
            y += 25L  // 207
            y *= x  // 208
            y += 1L  // 209
            z *= y  // 210
            y *= 0L  // 211
            y += w  // 212
            y += 11L  // 213
            y *= x  // 214
            z += y  // 215
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 216
            x *= 0L  // 217
            x += z  // 218
            x %= 26L  // 219
            z /= 26L  // 220
            x += -6L  // 221
            x = (x == w).toLong()  // 222
            x = (x == 0L).toLong()  // 223
            y *= 0L  // 224
            y += 25L  // 225
            y *= x  // 226
            y += 1L  // 227
            z *= y  // 228
            y *= 0L  // 229
            y += w  // 230
            y += 6L  // 231
            y *= x  // 232
            z += y  // 233
            println("Iteration ${iteration++}: $z")

            w = input.removeFirst().toLong() // 234
            x *= 0L  // 235
            x += z  // 236
            x %= 26L  // 237
            z /= 26L  // 238
            x += -6L  // 239
            x = (x == w).toLong()  // 240
            x = (x == 0L).toLong()  // 241
            y *= 0L  // 242
            y += 25L  // 243
            y *= x  // 244
            y += 1L  // 245
            z *= y  // 246
            y *= 0L  // 247
            y += w  // 248
            y += 1L  // 249
            y *= x  // 250
            z += y  // 251

            return z
        }

        fun compile(code: List<String>) {
            val instructions = code.map { it.splitIgnoreEmpty(" ") }

            for((idx, instr) in instructions.withIndex()) {
                val second = if(instr.size == 3 &&  instr[2].toLongOrNull() != null) {
                    instr[2]+"L"
                } else if(instr.size == 3) {
                    instr[2]
                } else {
                    ""
                }

                when (instr[0]) {
                    "inp" -> println("    ${instr[1]} = input.removeFirst().toLong() // $idx")
                    "add" -> println("    ${instr[1]} += $second  // $idx")
                    "mul" -> println("    ${instr[1]} *= $second  // $idx")
                    "div" -> println("    ${instr[1]} /= $second  // $idx")
                    "mod" -> println("    ${instr[1]} %= $second  // $idx")
                    "eql" -> println("    ${instr[1]} = (${instr[1]} == $second).toLong()  // $idx")
                }
                //println("[$idx] $instr")
                //println("  Vars: $vars")
            }
        }


    }

    fun helperCode(): Long {
        fun nomadFunction(zOrig: Long, L5Add: Long, L15Add: Long, input: Long): Long {
            var z = zOrig
            if(L5Add < 10L) {
                ////         (i[3] + 6)     + -8     == input
                val x = (((z % 26L) + L5Add) != input).toLong() // 1, 2, 3, 4, 6, 7

                z /=26L // 4
                z *= (x * 25L) + 1L  // 8 + 9 + 10 + 11 + 12
                z += (input + L15Add) * x  // 13, 14, 15, 16, 17
            } else {
                z *= 26L  // 8 + 9 + 10 + 11 + 12
                z += input + L15Add  // 13, 14, 15, 16, 17
            }
            println(z.toString(26))
            return z
        }
        val matrix = listOf(
            13L to 6L, // = 13-0
            11L to 11L, // =12-1
            12L to 5L, // =11-2
            10L to 6L, // = 8-3
            14L to 8L,  // = 5-4
            -1L to 14L, // = 4-5
            14L to 9L, // = 7-6
            -16L to 4L, // = 6-7
            -8L to 7L, // = 3-8
            12L to 13L,  // = 10-9
            -16L to 11L,  // = 9-10
            -13L to 11L,  // = 2-11
            -6L to 6L,  // =1-12
            -6L to 1L // = 0-13
        )
        val outcome = matrix.fold(0L) { z, tweaks -> nomadFunction(z, tweaks.first, tweaks.second, 1)  }
        println(outcome)
        println(Program().run(11_111_111_111_111.toString()))
        TODO()
        fun recurse(z: Long, matrix: List<Pair<Long, Long>>, number: String, zMax: Long): List<Long> {
            if(z > zMax) {
                return listOf()
            }
            if(number.length == 1) {
                println("Processing! $z")
            }
            if(matrix.isEmpty()) {
                return if(z == 0L) {
                    println("Found $number")
                    listOf(number.toLong())
                } else {
                    listOf()
                }
            }
            val tweaks = matrix.first()
            return (9L downTo 1L).flatMap { i ->
                val newZ = nomadFunction(z, tweaks.first, tweaks.second, i)
                recurse(newZ, matrix.drop(1), number + i.toString(), zMax)
            }
        }

        recurse(0, matrix, "", 1)
        TODO()

        val compile = false
        if (compile) {
            val program = Program()
            program.compile(input)
            TODO()
        }
        var round = 0
        println(Program().run(11_111_111_111_111.toString()))
        println(Program().run(11_111_111_111_121.toString()))


        //println(Program().run(98_999_999_999_999.toString()))
        //println(Program().originalProgram(98_999_999_999_999.toString()))
        for(x in 99_999_999_999_999 downTo 0 step 10_000_000_000_000) {
            if(!x.toString().contains("0")) {
                val result = Program().run(x.toString())
                println("$x = $result")
                if(result == 0L) {
                    return x
                    //throw IllegalStateException("value $x did not match!")
                }
            }
            /*if(x % 100_000_000L == 0L) {
                println("At $x")
            }*/
            if(round++ > 1000) {
                TODO()
            }
        }
        TODO()
    }
}
