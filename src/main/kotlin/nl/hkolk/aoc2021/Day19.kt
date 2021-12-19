package nl.hkolk.aoc2021

import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue

class Day19(val input: List<String>) {
    data class Point3D(val x:Int, val y:Int, val z:Int) {

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

    val scanners = input.splitBy { it.isEmpty() }.map { scanner ->
        scanner.drop(1).map { coord ->
            coord.splitIgnoreEmpty(",").map { it.toInt() }
        }.map { Point3D(it[0], it[1], it[2]) }.toSet()
    }

    fun solvePart1(): Int {
        val universe = scanners.first().toMutableSet()
        for(scanner in scanners.drop(1)) {
            val projections = Point3D.getTransformers().map { transformerChain ->
                scanner.map { point ->
                    transformerChain.fold(point) { accu, transformer -> transformer(accu) }
                }.toSet()
            }
            val(xMin, xMax) = universe.minAndMaxOf{ it.x }
            val(yMin, yMax) = universe.minAndMaxOf{ it.y }
            val(zMin, zMax) = universe.minAndMaxOf{ it.z }
            println((xMax-xMin)*(yMax-yMin)*(zMax-zMin))

            var step = 1
            for (xMod in (xMin/2..xMax/2)+(xMin..xMin/2)+(xMax/2..xMax)) {
                for (yMod in (yMin/2..yMax/2)+(yMin..yMin/2)+(yMax/2..yMax)) {
                    for (zMod in (zMin/2..zMax/2)+(zMin..zMin/2)+(zMax/2..zMax)) {
                        val shiftedUniverse = universe.map { Point3D(it.x + xMod, it.x + yMod, it.x + zMod) }.toSet()
                        for (projection in projections) {
                            if(step++ % 1_000_000 == 0) {
                                println("Step: $step")
                            }
                            if (projection.intersect(shiftedUniverse).size >= 12) {
                                println("Intersection!")
                            }
                        }
                    }
                }

            }
        }

        TODO()
    }
    fun solvePart2(): Int {
        TODO()
    }

    @OptIn(kotlin.experimental.ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun <T, R : Comparable<R>> Iterable<T>.minAndMaxOf(selector: (T) -> R): Pair<R, R> {
        val iterator = iterator()
        if (!iterator.hasNext()) throw NoSuchElementException()
        var maxValue = selector(iterator.next())
        var minValue = maxValue
        while (iterator.hasNext()) {
            val v = selector(iterator.next())
            if (maxValue < v) {
                maxValue = v
            }
            if (minValue > v) {
                minValue = v
            }
        }
        return minValue to maxValue
    }
}