package nl.hkolk.aoc2021

class Day10(val input:List<String>) {

    fun solvePart1(): Int {
        var score = 0
        for(line in input) {
            var line = line
            while (true) {
                val newline = line.replace("[]", "").replace("()", "").replace("<>", "").replace("{}", "")
                if (newline.length == line.length) {
                    break
                }
                line = newline
            }
            println("   ${input[0]}")
            println("-> $line")
            if(line.contains("""[\]\>\}\)]""".toRegex())) {
                println("Corrupt!")
                val match = """[\]\>\}\)]""".toRegex().find(line)
                println(match?.value)
                score += when (match?.value) {
                    ")" -> 3
                    "]" -> 57
                    "}" -> 1197
                    ">" -> 25137
                    else -> 0
                }

                //val first = "}>)]".toCharArray().map { line.indexOf(it) }.minOrNull()
            } else {
                println("Incomplete!")
            }
        }
        return score
    }
    fun solvePart2(): Long {
        var scores = mutableListOf<Long>()
        for(line in input) {
            var line = line
            while (true) {
                val newline = line.replace("[]", "").replace("()", "").replace("<>", "").replace("{}", "")
                if (newline.length == line.length) {
                    break
                }
                line = newline
            }
            if(!line.contains("""[\]\>\}\)]""".toRegex())) {
                println("   ${input[0]}")
                println("-> $line")
                println("Incomplete!")
                val score = line.reversed().map { when(it) {
                    '(' -> 1
                    '[' -> 2
                    '{' -> 3
                    '<' -> 4
                    else -> 0
                } }.fold(0L) {accu, value -> accu * 5 + value}
                println(score)
                scores.add(score)
            }
        }
        return scores.sorted().get(scores.size/2)
    }
}