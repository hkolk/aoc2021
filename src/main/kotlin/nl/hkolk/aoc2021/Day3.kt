package nl.hkolk.aoc2021

class Day3(input: List<String>) {
    private val length = input[0].length
    private val input = input.map { it.toInt(2) }

    fun solvePart1(): Int {
        var res = IntArray(length) { 0 }
        for(line in input) {
            for(i in length-1 downTo 0) {
                if(line and (1 shl i) > 0) {
                    res[i] += 1
                }
            }
        }
        var gamma = 0
        var epsilon = 0
        for(i in 0 until length) {
            if(res[i] > input.size/2) {
                gamma += (1 shl i)
            } else {
                epsilon += (1 shl i)
            }
        }
        return gamma * epsilon
    }

    fun solvePart2(): Int {
        var remaining = input
        for(i in (length-1) downTo 0) {
            var accu = 0
            for(line in remaining) {
                if(line and (1 shl i) > 0) {
                    accu += 1
                }
            }
            remaining = if(accu.toDouble() >= (remaining.size/2.0)) {
                remaining.filter { it and (1 shl i) > 0 }
            } else {
                remaining.filter { it.inv() and (1 shl i) > 0 }
            }
            if(remaining.size == 1) {
                break;
            }

        }
        var oxygen = remaining[0]
        remaining = input
        for(i in (length-1) downTo 0) {
            var accu = 0
            for(line in remaining) {
                if(line and (1 shl i) > 0) {
                    accu += 1
                }
            }
            remaining = if(accu.toDouble() < (remaining.size/2.0)) {
                remaining.filter { it and (1 shl i) > 0 }
            } else {
                remaining.filter { it.inv() and (1 shl i) > 0 }
            }
            if(remaining.size == 1) {
                break;
            }
        }
        var co2 = remaining[0]
        return co2*oxygen

    }
}