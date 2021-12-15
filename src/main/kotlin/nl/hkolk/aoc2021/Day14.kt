package nl.hkolk.aoc2021

class Day14(input:List<String>) {
    private val start = input.first()
    private val mappings =
        input.dropWhile { it.isNotBlank() }.drop(1).map { it.splitIgnoreEmpty(" -> ") }.associate { it[0] to it[1] }
    private val mappings2 = input.dropWhile { it.isNotBlank() }.drop(1).map { it.splitIgnoreEmpty(" -> ") }
        .associate { it[0] to listOf(it[0][0] + it[1], it[1] + it[0][1]) }


    fun solvePart1(): Int {
        var bla = start
        for(step in 1..10) {
            bla = bla.windowed(2).joinToString(separator = "") {
                it[0] + (mappings[it] ?: "")
            }.plus(bla.last())
        }
        val blasorted = bla.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }
        println(blasorted)
        return blasorted.last().second - blasorted.first().second
    }
    fun solvePart2_old(): Long {
        var bla: Map<String, Long> = start.windowed(2).groupBy { it }.map { it.key to it.value.size.toLong() }.toMap().plus(start.last().toString() to 1)
        for(step in 1..40) {
            //println("Step $step")
            //println("Before: $bla")
            bla = bla.flatMap {
                (mappings2[it.key]?:listOf(it.key)).map { str -> str to it.value }
            }.groupBy { it.first }.map { it.key to it.value.map { it.second }.sum() }.toMap()
            //println("After: $bla")
        }
        val blasorted = bla.toList().groupBy { it.first[0] }.map { it.key to it.value.sumOf { it.second } }.sortedBy { it.second }
        //println(blasorted)
        return blasorted.last().second - blasorted.first().second

        //val blasorted = bla.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }
        //println(blasorted)
    }
    fun solvePart2(): Long {
        var bla: Map<String, Long> = start.windowed(2).groupBy { it }.map { it.key to it.value.size.toLong() }.toMap().plus(start.last().toString() to 1)
        for(step in 1..40) {
            //println("Step $step")
            //println("Before: $bla")
            bla = bla.flatMap {
                (mappings2[it.key]?:listOf(it.key)).map { str -> str to it.value }
            }.sumValue { it.first }
            //println("After: $bla")
        }
        val blasorted = bla.toList().sumValue { it.first[0] }.toList().sortedBy { it.second }
        //println(blasorted)
        return blasorted.last().second - blasorted.first().second

        //val blasorted = bla.groupBy { it }.map { it.key to it.value.size }.sortedBy { it.second }
        //println(blasorted)
    }

}