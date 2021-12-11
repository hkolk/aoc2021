package nl.hkolk.aoc2021

class Day11(input: List<String>) {
    val initialMap = input.flatMapIndexed{ y, line -> line.mapIndexed { x, c -> Point2D(x, y) to c.digitToInt() }}.toMap()
    fun solvePart1(): Int {
        val map = initialMap.toMutableMap()
        var flashCount = 0
        for(step in 1..100) {
            // Update all to +1
            for((coord, value) in map) {
                map[coord] = value + 1
            }
            while(true) {
                val flashes = map.filter { it.value > 9 }
                if(flashes.isEmpty()) {
                    break
                }
                for(toFlash in flashes.keys) {
                    map[toFlash] = 0
                    flashCount += 1
                    for(adj in (toFlash.adjacent()+toFlash.diag())) {
                        if(map.getOrDefault(adj, -1) > 0) {
                            map[adj] = map[adj]!! + 1
                        }
                    }
                }
            }
            println(flashCount)

        }
        return flashCount
    }
    fun solvePart2(): Int {
        val map = initialMap.toMutableMap()
        var flashCount = 0
        for(step in 1..1_000_000) {
            // Update all to +1
            for((coord, value) in map) {
                map[coord] = value + 1
            }
            while(true) {
                val flashes = map.filter { it.value > 9 }
                if(flashes.isEmpty()) {
                    break
                }
                for(toFlash in flashes.keys) {
                    map[toFlash] = 0
                    flashCount += 1
                    for(adj in (toFlash.adjacent()+toFlash.diag())) {
                        if(map.getOrDefault(adj, -1) > 0) {
                            map[adj] = map[adj]!! + 1
                        }
                    }
                }
            }
            if(step % 100 == 0) {
                println("Step: $step")
            }
            if(map.filter { it.value > 0 }.isEmpty()) {
                return step
            }
        }
        TODO()
    }
}