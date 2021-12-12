package nl.hkolk.aoc2021

class Day12(val input:List<String>) {
    val connections = input.map { it.splitIgnoreEmpty("-") }.map { it[0] to it[1] }
    val caves = (connections + connections.map { it.second to it.first}).groupBy(keySelector = {it.first}, valueTransform = {it.second})

    fun findPaths(visited: List<String>, current:String) : Set<List<String>> {
        return caves[current]!!.map { connection ->
            if (connection == "end") {
                setOf(visited+"end")
            } else if (connection.uppercase() == connection || !visited.contains(connection)) {
                findPaths(visited + connection, connection)
            } else {
                setOf()
            }
        }.flatten().toSet()
    }
    fun findPaths2(visited: List<String>, current:String, revisited:String?) : Set<List<String>> {
        return caves[current]!!.map { connection ->
            if (connection == "end") {
                setOf(visited + "end")
            } else if (connection == "start") {
                setOf()
            } else if (connection.uppercase() == connection) {
                findPaths2(visited + connection, connection, revisited)
            } else if (visited.contains(connection) && revisited.isNullOrBlank()) {
                findPaths2(visited + connection, connection, connection)
            } else if (!visited.contains(connection)) {
                findPaths2(visited + connection, connection, revisited)
            } else {
                setOf()
            }
        }.flatten().toSet()
    }
    fun solvePart1():Int {
        val paths = findPaths(listOf("start"), "start")
        return paths.size
    }
    fun solvePart2():Int {
        val paths = findPaths2(listOf("start"), "start", null)
        return paths.size
    }
}