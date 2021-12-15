package nl.hkolk.aoc2021

import kotlin.math.abs

class Day15(val input: List<String>) {
    private val map = input.flatMapIndexed { y, line -> line.mapIndexed{ x, value -> Point2D(x, y) to value.digitToInt() }}.toMap()
    private val mapSizeX = input[0].length
    private val mapSizeY = input.size

    private fun heuristic(a:Point2D, b:Point2D) = abs(a.x - b.x) + abs(a.y - b.y)

    private fun findShortestPath(start: Point2D, goal: Point2D, map: Map<Point2D, Int>): Pair<List<Point2D>, Int> {

        fun generatePath(currentPos: Point2D, cameFrom: Map<Point2D, Point2D>): List<Point2D> {
            val path = mutableListOf(currentPos)
            var current = currentPos
            while (cameFrom.containsKey(current)) {
                current = cameFrom.getValue(current)
                path.add(0, current)
            }
            return path.toList()
        }

        val openVertices = mutableSetOf(start)
        val closedVertices = mutableSetOf<Point2D>()
        val costFromStart = mutableMapOf(start to 0)
        val cameFrom = mutableMapOf<Point2D, Point2D>()

        val estimatedTotalCost = mutableMapOf(start to heuristic(start, goal))
        while(openVertices.isNotEmpty()) {
            val current = openVertices.minByOrNull { estimatedTotalCost[it]!! }!!
            if(current == goal) {
                val path = generatePath(current, cameFrom)
                return Pair(path, estimatedTotalCost.getValue(goal)) // First Route to finish will be optimum route
            }
            openVertices.remove(current)
            closedVertices.add(current)

            for(next in current.adjacent().filter { map.containsKey(it) }.filterNot { closedVertices.contains(it) }) {
                val newCost = costFromStart[current]!! + map[next]!!
                if(!costFromStart.containsKey(next) || newCost < costFromStart[next]!!) {
                    costFromStart[next] = newCost
                    estimatedTotalCost[next] = newCost + heuristic(goal, next)
                    openVertices.add(next)
                    cameFrom[next] = current
                }
            }
        }
        throw IllegalStateException("No path found")
    }
    fun solvePart1(): Int = findShortestPath(Point2D(0, 0), Point2D(mapSizeX-1, mapSizeY-1), map).second

    fun solvePart2(): Int {
        val newMap = (0 until 5).flatMap { x -> (0 until 5).flatMap { y ->
            map.map { Point2D(x=it.key.x+(x*mapSizeX), y=it.key.y+(y*mapSizeY)) to (it.value+x+y).let { i ->
                if(i > 9) { i-9} else {i} }
            }
        } }.toMap()

        return findShortestPath(Point2D(0, 0), Point2D((mapSizeX*5)-1, (mapSizeY*5)-1), newMap).second
    }
}