package nl.hkolk.aoc2021

class Day5(val input: List<String>) {
    val lines = input.map { line -> line.splitIgnoreEmpty(" ") }.map { Point2D.fromString(it[0]) to Point2D.fromString(it[2]) }

    fun solvePart1(): Int {
        val map = mutableListOf<Point2D>()
        for(line in lines) {
            if(line.first.x == line.second.x) {
                for (y in minOf(line.first.y, line.second.y)..maxOf(line.first.y, line.second.y)) {
                    map.add(Point2D(line.first.x, y))
                }
            }
            else if(line.first.y == line.second.y) {
                for (x in minOf(line.first.x, line.second.x)..maxOf(line.first.x, line.second.x)) {
                    map.add(Point2D(x, line.first.y))
                }
            }
        }
        return map.groupBy { it }.filter { it.value.size > 1 }.size
    }
    fun solvePart2(): Int {
        val map = mutableListOf<Point2D>()
        for(line in lines) {
            if(line.first.x == line.second.x) {
                for (y in minOf(line.first.y, line.second.y)..maxOf(line.first.y, line.second.y)) {
                    map.add(Point2D(line.first.x, y))
                }
            }
            else if(line.first.y == line.second.y) {
                for (x in minOf(line.first.x, line.second.x)..maxOf(line.first.x, line.second.x)) {
                    map.add(Point2D(x, line.first.y))
                }
            } else {
                val ydistance = maxOf(line.first.y, line.second.y) - minOf(line.first.y, line.second.y)
                val xdistance = maxOf(line.first.x, line.second.x) - minOf(line.first.x, line.second.x)
                val lcd = minOf(xdistance, ydistance)
                val xstep = (line.second.x - line.first.x) / lcd
                val ystep = (line.second.y - line.first.y) / lcd
                var x = line.first.x
                var y = line.first.y
                while(x != line.second.x) {
                    map.add(Point2D(x, y))
                    x+= xstep
                    y+= ystep
                }
                map.add(Point2D(line.second.x, line.second.y))

            }
        }
        return map.groupBy { it }.filter { it.value.size > 1 }.size
    }
}