package nl.hkolk.aoc2021

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class Day22(val input: List<String>) {

    data class Point3D(val x:Int, val y:Int, val z:Int)

    fun IntRange.coversRange(bounds: IntRange): Boolean {
        return if(this.first < bounds.first) {
            this.last >= bounds.first
        } else {
            if(this.last > bounds.last) {
                this.first <= bounds.last
            } else {
                true
            }
        }
    }

    fun solvePart1(): Int {
        val reactor = mutableSetOf<Point3D>()
        for(line in input) {
            val instructions = line.splitIgnoreEmpty(" ", ",")
            val switchOn = instructions[0] == "on"
            val box = instructions.drop(1).map {
                val parts = it.splitIgnoreEmpty("=", ".")
                parts[0] to (parts[1].toInt()..parts[2].toInt())
            }.toMap()
            println("$switchOn, $box")
            if(!box["x"]!!.coversRange(-50..50) || !box["y"]!!.coversRange(-50..50) || !box["z"]!!.coversRange(-50..50)) {
                continue
            }
            for(x in box["x"]!!) {
                for(y in box["y"]!!) {
                    for(z in box["z"]!!) {
                        if(x.absoluteValue <= 50 && y.absoluteValue <= 50 && z.absoluteValue <= 50) {
                            if (switchOn) {
                                reactor.add(Point3D(x, y, z))
                            } else {
                                reactor.remove(Point3D(x, y, z))
                            }
                        }
                    }
                }
            }
        }
        return reactor.size
    }


    class Box(val on: Boolean, val x: Pair<Int, Int>, val y: Pair<Int, Int>, val z: Pair<Int, Int>) {
        override fun toString(): String = "[Box on: $on] x: $x, y: $y, z: $z"

        fun Pair<Int, Int>.diff() = (second+1 - first).toLong().coerceAtLeast(0)
        fun size(): Long = x.diff() * y.diff() * z.diff()

        fun intersectCount(other: Box): Long {
            val minX = max(x.first, other.x.first)
            val maxX = min(x.second, other.x.second)+1
            if(minX >= maxX) { return 0 }
            val minY = max(y.first, other.y.first)
            val maxY = min(y.second, other.y.second)+1
            if(minY >= maxY) { return 0 }
            val minZ = max(z.first, other.z.first)
            val maxZ = min(z.second, other.z.second)+1
            if(minZ >= maxZ) { return 0 }
            return (maxX - minX).toLong() * (maxY - minY).toLong() * (maxZ - minZ).toLong()
        }
        fun cutOut(other: Box): List<Box> {
            if(intersectCount(other) == 0L) {
                return listOf(this)
            }
            // Big flanks
            val xr = max(other.x.first, x.first) to min(other.x.second, x.second)
            val zr = max(other.z.first, z.first) to min(other.z.second, z.second)
            return listOf(
                Box(true, x, y, z.first to other.z.first-1),
                Box(true, x, y, other.z.second+1 to z.second),

                // Medium sides
                Box(true, x.first to other.x.first - 1, y, zr),
                Box(true, other.x.second + 1 to x.second, y, zr),

                // Small top & bottom
                Box(true, xr, y.first to other.y.first - 1, zr),
                Box(true, xr, other.y.second + 1 to y.second, zr)
            ).filter { it.size() > 0 }

        }
    }

    fun solvePart2(): Long {

        /*val box1 = Box(true, 0 to 3, 0 to 3, 0 to 3)
        assert(box1.size() == 64L)
        assert(box1.cutOut(Box(true, 1 to 2, 1 to 2, 0 to 1)).sumOf{it.size()} == 56L)
        box1.cutOut(Box(true, -1 to 4, -1 to 4, -1 to 4)).run {
            this.forEach { println("$it: ${it.size()}") }
            println(this.sumOf {it.size()})
        }
        */

        val boxes = input.mapIndexed { id, line ->
            val instructions = line.splitIgnoreEmpty(" ", ",")
            val switchOn = instructions[0] == "on"
            val box = instructions.drop(1).map {
                val parts = it.splitIgnoreEmpty("=", ".")
                parts[1].toInt() to parts[2].toInt()
            }
            box.forEach { assert(it.first < it.second) }
            Box(switchOn, box[0], box[1], box[2])
        }.toMutableList()

        var universe = listOf<Box>(boxes.first())
        for(box in boxes.drop(1)) {
            universe = universe.flatMap { it.cutOut(box) }
            if(box.on) {
                universe = universe.plus(box)
            }
        }
        return universe.sumOf { it.size()}
    }
}