package nl.hkolk.aoc2021

class Day10(val input:List<String>) {

    private fun reduce(originalLine: String) : String {
        var line = originalLine
        while (true) {
            val newline = line.replace("[]", "").replace("()", "").replace("<>", "").replace("{}", "")
            if (newline.length == line.length) {
                break
            }
            line = newline
        }
        return line
    }

    fun solvePart1(): Int =
        input.map {
            val match = """[]>})]""".toRegex().find(reduce(it))
            when (match?.value) {
                ")" -> 3
                "]" -> 57
                "}" -> 1197
                ">" -> 25137
                else -> 0
            }
        }.sum()


    fun solvePart2(): Long {
        val scores = mutableListOf<Long>()
        for(originalLine in input) {
            val line = reduce(originalLine)
            if(!line.contains("""[]>})]""".toRegex())) {
                val score = line.reversed().map { when(it) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> 0
                } }.fold(0L) {accu, value -> accu * 5 + value}
                scores.add(score)
            }
        }
        return scores.sorted()[scores.size/2]
    }
}