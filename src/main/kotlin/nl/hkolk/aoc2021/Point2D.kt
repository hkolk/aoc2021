package nl.hkolk.aoc2021

import java.lang.IllegalArgumentException
import kotlin.math.absoluteValue

data class Point2D(val x:Int, val y:Int) {
    fun adjacent(): Sequence<Point2D> = sequence {
        DIRECTIONS.map { yield(it(this@Point2D)) }
    }
    fun diag(): Sequence<Point2D> = sequence {
        DIRECTIONSDIAG.map { yield(it(this@Point2D)) }
    }

    fun surrounding(): List<Point2D> {
        return listOf(
            NORTHWEST(this),
            NORTH(this),
            NORTHEAST(this),
            WEST(this),
            this.copy(),
            EAST(this),
            SOUTHWEST(this),
            SOUTH(this),
            SOUTHEAST(this)

        )
    }

    fun flip(): Point2D = Point2D(-x, y)

    fun manhattan(): Int {
        return x.absoluteValue + y.absoluteValue
    }

    fun move(direction: Point2D, times: Int=1): Point2D = move( { Point2D(it.x+direction.x, it.y + direction.y) })

    fun move(direction: (Point2D) -> Point2D, times: Int=1): Point2D {
        return direction.repeated(times).fold(this) { acc, func -> func(acc) }
    }

    fun rotate(degrees:Int): Point2D {
        return when(degrees) {
            90  -> Point2D(y, -x)
            270 -> Point2D(-y, x)
            180 -> Point2D(-x, -y)
            0 -> Point2D(x, y)
            else -> throw IllegalArgumentException("Degrees must be 0, 90, 180, 270. Was: $degrees")
        }
    }

    companion object {
        val NORTH: (Point2D) -> Point2D =        { Point2D(x = it.x,      y = it.y - 1  ) }
        val NORTHEAST: (Point2D) -> Point2D =    { Point2D(x = it.x + 1,  y = it.y - 1  ) }
        val EAST: (Point2D) -> Point2D =         { Point2D(x = it.x + 1,  y = it.y      ) }
        val SOUTHEAST: (Point2D) -> Point2D =    { Point2D(x = it.x + 1,  y = it.y + 1  ) }
        val SOUTH: (Point2D) -> Point2D =        { Point2D(x = it.x,      y = it.y + 1  ) }
        val SOUTHWEST: (Point2D) -> Point2D =    { Point2D(x = it.x - 1,  y = it.y + 1  ) }
        val WEST: (Point2D) -> Point2D =         { Point2D(x = it.x - 1,  y = it.y      ) }
        val NORTHWEST: (Point2D) -> Point2D =    { Point2D(x = it.x - 1,  y = it.y - 1  ) }

        val DIRECTIONS: List<(Point2D) -> Point2D> = listOf(
            NORTH, EAST, SOUTH, WEST
        )
        val DIRECTIONSDIAG: List<(Point2D) -> Point2D> = listOf(
            NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST
        )

        fun fromString(coords: String): Point2D {
            val (x, y) = coords.splitIgnoreEmpty(",").map { it.toInt() }
            return Point2D(x, y)
        }
    }
}


fun Map<Point2D, Int>.print() {
    for(y in keys.minAndMaxOf { it.y }.let { it.first..it.second }) {
        for(x in keys.minAndMaxOf { it.x }.let { it.first..it.second }) {
            if((this[Point2D(x, y)]?:0) != 0) {
                print('???')
            } else {
                print(' ')
            }
        }
        println()
    }
}