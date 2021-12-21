package nl.hkolk.aoc2021

class Day20(val input: List<String>) {
    private val enhancer = input.first().map { if(it == '#') { 1 } else { 0 } }
    private val initialImage = input.drop(2).flatMapIndexed{ y, line ->
        line.mapIndexed { x, c ->
            Point2D(x, y) to if(c == '#') { 1 } else { 0 }
        }
    }.toMap()

    private fun enhance(image: Map<Point2D, Int>, default: Int=0) : Map<Point2D, Int> {
        var newImage = mutableMapOf<Point2D, Int>()
        for(y in image.keys.minAndMaxOf { it.y }.let { (it.first - 1) .. (it.second + 1)}) {
            for(x in image.keys.minAndMaxOf { it.x }.let { (it.first - 1) .. (it.second + 1) }) {
                val index = Point2D(x, y).surrounding().map { image[it]?:default }.fold(0) { acc, it -> (acc shl 1) + it}
                if(enhancer[index] == 1 ) {
                    newImage[Point2D(x, y)] = 1
                } else {
                    newImage[Point2D(x, y)] = 0
                }
            }
        }
        return newImage
    }

    private fun solve(steps: Int): Int {
        var image = initialImage
        val blinkSpace = (enhancer[0] == 1 && enhancer[511] == 0)
        for(step in 0 until steps) {
            val default = if(blinkSpace) { step % 2 } else { 0 }
            image = enhance(image, default)
        }
        return image.values.sum()
    }
    fun solvePart1() = solve(2)
    fun solvePart2() = solve(50)

}