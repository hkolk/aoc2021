package nl.hkolk.aoc2021

fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
}

fun List<String>.splitBy(func: (String) -> Boolean): List<List<String>> {
    val ret = mutableListOf<List<String>>()
    var collect = mutableListOf<String>()
    for(line in this) {
        if(func(line)) {
            ret.add(collect)
            collect = mutableListOf()
        } else {
            collect.add(line)
        }
    }
    if(collect.isNotEmpty()) ret.add(collect)
    return ret
}

fun List<Number>.multiply(): Long = map { it.toLong() }.fold(1L) { acc, it -> acc * it }

fun List<String>.rotate(): List<String> = (0 until first().length).map { x -> (size-1 downTo 0).map { this[it][x] }.joinToString("") }