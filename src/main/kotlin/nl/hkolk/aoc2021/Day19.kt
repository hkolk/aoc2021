package nl.hkolk.aoc2021

import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue

class Day19(val input: List<String>) {
    data class Point3D(val x:Int, val y:Int, val z:Int) {

        fun distance(other: Point3D): Point3D {
            return Point3D((x - other.x).absoluteValue, (y - other.y).absoluteValue, (z - other.z).absoluteValue)
        }

        fun manhattan(other: Point3D): Int {
            return (other.x - x).absoluteValue + (other.y - y).absoluteValue + (other.z - z).absoluteValue
        }

        companion object {
            fun getTransformers(): List<List<(Point3D) -> Point3D>> {
                return listOf({i: Point3D -> i},{i: Point3D -> i.copy(x=0-i.x)}).flatMap { xMod ->
                    listOf({i: Point3D -> i},{i: Point3D -> i.copy(y=0-i.y)}).flatMap { yMod ->
                        listOf({i: Point3D -> i},{i: Point3D -> i.copy(z=0-i.z)}).flatMap { zMod ->
                            listOf(
                                {i: Point3D -> Point3D(i.x, i.y, i.z)},
                                {i: Point3D -> Point3D(i.x, i.z, i.y)},
                                {i: Point3D -> Point3D(i.y, i.x, i.z)},
                                {i: Point3D -> Point3D(i.y, i.z, i.x)},
                                {i: Point3D -> Point3D(i.z, i.y, i.x)},
                                {i: Point3D -> Point3D(i.z, i.x, i.y)}
                            ).map { shifter ->
                                listOf(xMod, yMod, zMod, shifter)
                            }
                        }
                    }
                }
            }
        }
    }

    private val scanners = input.splitBy { it.isEmpty() }.map { scanner ->
        scanner.drop(1).map { coord ->
            coord.splitIgnoreEmpty(",").map { it.toInt() }
        }.map { Point3D(it[0], it[1], it[2]) }.toSet()
    }

    fun Set<Point3D>.distances(): List<Point3D> {
        return this.combinations(2).map { it[0].distance(it[1]) }.toList()
    }

    private fun fit(rotation: Set<Point3D>, universe: Set<Point3D>): ((Point3D) -> Point3D)? {
        for(pointLocal in rotation) {
            for(pointUniverse in universe) {
                val xMod = pointUniverse.x - pointLocal.x
                val yMod = pointUniverse.y - pointLocal.y
                val zMod = pointUniverse.z - pointLocal.z
                val modifier: (Point3D)-> Point3D = { Point3D(it.x + xMod, it.y+yMod, it.z+zMod)}
                val intersect = rotation.map(modifier).intersect(universe)
                if(intersect.size >= 12) {
                    //println("Intersect!!")
                    return modifier
                }
            }
        }
        return null
    }

    private fun createUniverse(): Pair<Set<Point3D>, Set<Point3D>> {
        val foundScanners = mutableSetOf(Point3D(0, 0, 0))
        val universe = scanners.first().toMutableSet()
        //println(universe.distances())
        var rotatedScanners = (scanners.drop(1)).map { scanner ->
            Point3D.getTransformers().map { transformerChain ->
                scanner.map { point ->
                    transformerChain.fold(point) { accu, transformer -> transformer(accu) }
                }.toSet()
            }
        }.toMutableList()

        outer@ while(rotatedScanners.isNotEmpty()  ) {
            for ((index, scanner) in rotatedScanners.withIndex()) {
                for (rotation in scanner) {
                    val intersect = rotation.distances().intersect(universe.distances())
                    if (intersect.size >= 12) {
                        //println("Found possible intersect in scanner $index")
                        val modifier = fit(rotation, universe)
                        if (modifier != null) {
                            universe.addAll(rotation.map(modifier))
                            //println(universe.size)
                            rotatedScanners.removeAt(index)
                            foundScanners.add(modifier(Point3D(0, 0, 0)))
                            continue@outer
                        }
                    }
                }
            }
        }
        return universe to foundScanners
    }

    fun solvePart1(): Int {
        val(universe, _) = createUniverse()
        return universe.size
    }



    fun solvePart2(): Int {
        val(_, scanners) = createUniverse()
        return scanners.combinations(2).maxOf{ it[0].manhattan(it[1])}
    }

}