package nl.hkolk.aoc2021

import java.lang.IllegalStateException
import kotlin.math.max
import kotlin.math.min

class Day13(input: List<String>) {
    private val dots = input.takeWhile { it.isNotBlank() }.map { it.splitIgnoreEmpty(",") }.map { Point2D(it[0].toInt(), it[1].toInt())}.toSet()
    private val folds = input.takeLastWhile { it.isNotBlank() }.map { it.splitIgnoreEmpty(" ", "=")}.map { it[2] to it[3].toInt() }

    private fun printDots(dots: Set<Point2D>) {
        val maxY = dots.fold(0) { acc, cur -> max(acc, cur.y)}
        val maxX = dots.fold(0) { acc, cur -> max(acc, cur.x)}
        for(y in 0..maxY) {
            for (x in 0..maxX) {
                if(dots.contains(Point2D(x, y))) {
                    print("░")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }

    private fun fold(dots: Set<Point2D>, fold:Pair<String, Int>): Set<Point2D> = dots.map {
        when(fold.first) {
            "x" -> Point2D(x=min(fold.second*2 - it.x, it.x), y=it.y)
            "y" -> Point2D(x=it.x, y=min(fold.second*2 - it.y, it.y))
            else -> throw IllegalStateException()
        } }.toSet()

    fun solvePart1(): Int = fold(dots, folds.first()).size

    fun solvePart2(): Int {
        val dots = folds.fold(dots) { prev, fold -> fold(prev, fold) }
        printDots(dots)
        return dots.size
    }
}