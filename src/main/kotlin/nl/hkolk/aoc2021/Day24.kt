package nl.hkolk.aoc2021

class Day24(val input: List<String>) {

    class Program(code: List<String>) {
        private val instructions = code.map { it.splitIgnoreEmpty(" ") }

        var z = 0L

        private fun duplicate3(L5Add: Long, L15Add: Long, input: Long) {
            var x = (z % 26L) + L5Add // 1, 2, 3, 4

            if(L5Add<0) {
                z /=26L // 4
            }
            x = (x != input).toLong()  // 6+7
            z *= (x * 25L) + 1L  // 8 + 9 + 10 + 11 + 12
            z += (input + L15Add) * x  // 13, 14, 15, 16, 17
        }

        fun run(originalInput: String): Long {
            val input = originalInput.map { it.digitToInt() }.toMutableList()


            duplicate3(13L, 6L, input.removeFirst().toLong())

            duplicate3(11L, 11L, input.removeFirst().toLong())

            duplicate3(12L, 5L, input.removeFirst().toLong())

            duplicate3(10L, 6L, input.removeFirst().toLong())

            duplicate3(14L, 8L, input.removeFirst().toLong())

            duplicate3(-1L, 14L, input.removeFirst().toLong())

            duplicate3(14L, 9L, input.removeFirst().toLong())

            duplicate3(16L, 4L, input.removeFirst().toLong())

            duplicate3(-8L, 7L, input.removeFirst().toLong())

            duplicate3(12L, 13L, input.removeFirst().toLong())

            duplicate3(16L, 11L, input.removeFirst().toLong())

            duplicate3(-13L, 11L, input.removeFirst().toLong())

            duplicate3(-6L, 6L, input.removeFirst().toLong())

            duplicate3(-6L, 1L, input.removeFirst().toLong())

            return z

        }

        fun compile() {
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

    fun solvePart1(): Long {
        val compile = false
        if (compile) {
            val program = Program(input)
            program.compile()
            TODO()
        }
        for(x in 99_999_999_999_999 downTo 0) {
            if(!x.toString().contains("0")) {
                if(Program(input).run(x.toString()) == 0L) {
                    return x
                }
            }
            if(x % 100_000_000L == 0L) {
                println("At $x")
            }
        }
        TODO()
    }
    fun solvePart2(): Long {
        TODO()
    }
}
inline fun Boolean.toLong() = if(this) { 1L } else { 0L }
