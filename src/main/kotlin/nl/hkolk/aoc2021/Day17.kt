package nl.hkolk.aoc2021

import java.lang.IllegalStateException
import kotlin.math.abs
import kotlin.math.max

class Day17(val input: List<String>) {

    fun solvePart1() : Int {
        // target area: x=211..232, y=-124..-69
        val (xBox, yBox) = input[0].splitIgnoreEmpty(" ").drop(2).map { it.splitIgnoreEmpty("=", ".", ",").drop(1).map { it.toInt() } }
        //println(xBox)
        //val x = 7
        //val distance = (0..x).map { x - it }.sum()
        //println("x: $x, distance: $distance")

        println(yBox)
        val y = yBox.maxOf { abs(it) } - 1
        println(y)
        val height = (0..y).map { y - it }.sum()
        println(height)
        return height
    }
    private fun simulate(xSpeed: Int, ySpeed: Int, xBox: List<Int>, yBox: List<Int>): Boolean {
        var x = 0
        var y = 0

        for(step in 1..300) {
            x+=max(xSpeed - step + 1, 0)
            y+=ySpeed - step + 1
            if(x >= xBox[0] && x <= xBox[1] && y >= yBox[0] && y <= yBox[1] ) {
                return true
            } else if(x> xBox[1] || y < yBox[0]) {
                return false
            }
        }
        throw IllegalStateException("Too many spins")
    }
    fun solvePart2() : Int {
        val (xBox, yBox) = input[0].splitIgnoreEmpty(" ").drop(2).map { it.splitIgnoreEmpty("=", ".", ",").drop(1).map { it.toInt() } }

        val minY = yBox.minOf { it }
        val maxY = abs(minY) - 1
        val maxX = xBox.maxOf{ it }
        var counter = 0
        for(y in minY..maxY) {
            // optimize minX ?
            for(x in 1..maxX) {
                if(simulate(x, y, xBox, yBox)) {
                    counter++
                }
            }
        }
        return counter
        TODO()
    }
}