package nl.hkolk.aoc2021

class Day9(val input: List<String>) {
    val map = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point2D(x, y) to c.toString().toInt() }}.toMap()
    val lowpoints = map.filter { point -> point.key.adjacent().filter { (map[it]?:10) <= point.value }.count() == 0 }

    private fun basinFinder(accu: Map<Point2D, Int>, cur: Point2D) : Map<Point2D, Int> {
        var newAccu = accu
        val adjacent = cur.adjacent().filter { !newAccu.containsKey(it) && (map[it]?:9) != 9 }
        for(adj in adjacent) {
            newAccu = basinFinder(newAccu + (adj to map[adj]!!), adj )
        }
        return newAccu

    }
    fun solvePart1(): Int {
        return lowpoints.values.sum()+lowpoints.size
    }

    fun solvePart2(): Long {
        val basinSizes = lowpoints.map { basinFinder(mapOf(it.toPair()), it.key ).size }
        return basinSizes.sorted().reversed().slice(0..2).multiply()
    }
}