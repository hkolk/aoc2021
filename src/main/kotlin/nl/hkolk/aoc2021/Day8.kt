package nl.hkolk.aoc2021

class Day8(val input: List<String>) {
    fun solvePart1(): Int =
        input.map { it.splitIgnoreEmpty("|")[1].splitIgnoreEmpty(" ") }.map { it.map { if(it.length == 4 || it.length == 2 || it.length == 3 || it.length == 7) { 1 } else { 0 } }.sum()}.sum()

    fun solvePart2(): Int {
        val lines = input.map { it.splitIgnoreEmpty("|").map { it.splitIgnoreEmpty(" ").map{it.toCharArray().sorted().toSet()} } }
        var sum = 0
        for(line in lines) {
            val found = (line[0] + line[1]).toSet().groupBy { it.size}
            val mapping = mutableMapOf<Int, Set<Char>>()
            mapping[1] = found[2]!!.first()
            mapping[4] = found[4]!!.first()
            mapping[7] = found[3]!!.first()
            mapping[8] = found[7]!!.first()
            mapping[6] = found[6]!!.first { !it.containsAll(mapping[7]!!) }
            mapping[9] = found[6]!!.first { it.containsAll(mapping[4]!!) }
            mapping[0] = found[6]!!.first { it != mapping[6]!! && it != mapping[9]!!}
            mapping[5] = found[5]!!.first { it.intersect(mapping[6]!!).size == 5 }
            mapping[3] = found[5]!!.first { it.containsAll(mapping[7]!!)}
            mapping[2] = found[5]!!.first { it != mapping[5]!! && it != mapping[3]!!}

            val lookup = mapping.entries.associateBy({ it.value }) { it.key }
            val result = line[1].map { lookup[it]!!}.joinToString("").toInt()
            //println(result)
            sum += result;
            //println(mapping)
            //println(found)
            //TODO()
        }
        return sum
    }
}