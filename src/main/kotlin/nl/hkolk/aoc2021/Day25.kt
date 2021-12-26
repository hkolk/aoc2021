package nl.hkolk.aoc2021

import kotlin.IllegalStateException

class Day25(val input: List<String>) {
    fun step(map: Map<Point2D, Char>): Pair<Map<Point2D, Char>, Int> {
        var changes = 0
        val newMap = map.filter { it.value == '>' }.map { east ->
            val x = if(east.key.x + 1 < input[0].length) { east.key.x+1 } else { 0 }
            val next = east.key.copy(x=x)
            if(!map.containsKey(next)) {
                changes++
                next to '>'
            } else {
                east.key to '>'
            }
        }.toMap() + map.filter { it.value == 'v' }
        val newMap2 = newMap.filter { it.value == 'v' }.map { south ->
            val y = if(south.key.y + 1 < input.size) { south.key.y+1 } else { 0 }
            val next = south.key.copy(y=y)
            if(!newMap.containsKey(next)) {
                changes++
                next to 'v'
            } else {
                south.key to 'v'
            }
        }.toMap() + newMap.filter { it.value == '>' }

        return newMap2 to changes
    }
    fun Pair<Int, Int>.toRange(): IntRange = this.first..this.second

    fun printMap(map: Map<Point2D, Char>) {
        for(y in map.keys.minAndMaxOf { it.y }.toRange()) {
            for(x in map.keys.minAndMaxOf { it.x }.toRange()) {
                print(map.getOrDefault(Point2D(x, y), '.'))
            }
            println()
        }
    }

    fun solvePart1(): Int {
        var map = input.flatMapIndexed{ y, line -> line.mapIndexedNotNull{x, c -> if(c != '.') { Point2D(x, y) to c} else { null }} }.toMap()
        for(step in 1..2_000_000) {
            val result = step(map)
            map = result.first
            if(result.second == 0) {
                printMap(map)
                return step
            }
        }
        throw IllegalStateException("No resolution after 2m rounds")
    }
    fun solvePart2(): Int {
        return 1
    }
}