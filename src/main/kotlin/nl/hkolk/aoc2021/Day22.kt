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
        TODO()
    }

    class Box(val id: Int, val on: Boolean, val x: Pair<Int, Int>, val y: Pair<Int, Int>, val z: Pair<Int, Int>) {
        override fun toString(): String = "[Box $id - $on] x: $x, y: $y, z: $z"

        fun Pair<Int, Int>.diff() = (second+1 - first).absoluteValue.toLong()
        fun size(): Long = x.diff() * y.diff() * z.diff()

        fun cutOutAndCount(offList: List<Box>) : Long {
            var count = x.diff() * y.diff() * z.diff()
            for(box in offList) {
                count -= intersectCount(box)
            }
            // for offbox
              // remove intersect
              // for processed
                 // re-add intersect (because double-removed)
              // add to processed
            return count
        }
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
    }

    fun solvePart2(): Long {

        /*val box1 = Box(1, true, 0 to 10, 0 to 10, 0 to 10)
        val cnt = box1.intersectCount(Box(2, false,  11 to 12, 4 to 7, 1 to 1))
        println(cnt)
        TODO()
        */

        val boxes = input.mapIndexed { id, line ->
            val instructions = line.splitIgnoreEmpty(" ", ",")
            val switchOn = instructions[0] == "on"
            val box = instructions.drop(1).map {
                val parts = it.splitIgnoreEmpty("=", ".")
                parts[1].toInt() to parts[2].toInt()
            }
            box.forEach { assert(it.first < it.second) }
            Box(id, switchOn, box[0], box[1], box[2])
        }.toMutableList()

        var count = 0L
        while(boxes.isNotEmpty()) {
            val box = boxes.removeFirst()
            if(box.on) {
                count += box.cutOutAndCount(boxes)
                println("Count: $count")
            }
            println(box.size())
        }
        return count

        println(boxes)
        TODO()
    }
}