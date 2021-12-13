package nl.hkolk.aoc2021

import kotlin.math.max

class Day13(input: List<String>) {
    val dots = input.takeWhile { it.isNotBlank() }.map { it.splitIgnoreEmpty(",") }.map { Point2D(it[0].toInt(), it[1].toInt())}
    val folds = input.takeLastWhile { it.isNotBlank() }.map { it.splitIgnoreEmpty(" ", "=")}.map { it[2] to it[3].toInt() }

    fun printDots(dots: List<Point2D>) {
        val maxY = dots.fold(0) { acc, cur -> max(acc, cur.y)}
        val maxX = dots.fold(0) { acc, cur -> max(acc, cur.x)}
        for(y in 0..maxY) {
            for (x in 0..maxX) {
                if(dots.contains(Point2D(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
    fun solvePart1(): Int {
        var dots = dots
        println(dots)
        for(fold in folds) {
            dots = dots.map {
                if (fold.first == "x") {
                    if(it.x > fold.second) {
                        Point2D(x=fold.second - (it.x - fold.second), y=it.y)
                    } else {
                        it
                    }
                } else {
                    if(it.y > fold.second) {
                        Point2D(x=it.x , y=fold.second - (it.y - fold.second))
                    } else {
                        it
                    }
                }
            }
            return dots.distinct().size

        }
        throw IllegalStateException()
    }
    fun solvePart2(): Int {
        var dots = dots
        for(fold in folds) {
            dots = dots.map {
                if (fold.first == "x") {
                    if(it.x > fold.second) {
                        Point2D(x=fold.second - (it.x - fold.second), y=it.y)
                    } else {
                        it
                    }
                } else {
                    if(it.y > fold.second) {
                        Point2D(x=it.x , y=fold.second - (it.y - fold.second))
                    } else {
                        it
                    }
                }
            }

        }
        printDots(dots)
        return dots.distinct().size
    }
}