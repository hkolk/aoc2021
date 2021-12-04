package nl.hkolk.aoc2021

class Day4(val input: List<String>) {

    class Board(board: List<Int>) {
        val board = board.toMutableList()
        fun markDraw(draw: Int) {
            board.replaceAll { if(it == draw) -1 else it }
        }

        fun isBingo(): Boolean {
            val bingoRanges = listOf(
                0..4,
                5..9,
                10..14,
                15..19,
                20..24,
                0..20 step 5,
                1..21 step 5,
                2..22 step 5,
                3..23 step 5,
                4..24 step 5,
                // (Diagonals don't count.)
                //0..24 step 6,
                //4..20 step 4
            )
            for(range in bingoRanges) {
                if(board.slice(range).filter { it != -1 }.isEmpty()) {
                    return true
                }
            }
            return false
        }

        override fun toString(): String {
            return board.chunked(5).map { it.toString() }.joinToString("\n")
        }

        fun remaining(): Int {
            return board.filter { it != -1 }.sum()
        }
        companion object {
            fun fromString(input: List<String>): Day4.Board {
                val rows = input.map { it.splitIgnoreEmpty(" ").map { it.toInt() } }
                return Board(rows.flatten())
            }
        }
    }

    fun solvePart1(): Int {

        val draws = input[0].trim().splitIgnoreEmpty(",").map {it.toInt()}
        var boards = mutableListOf<Board>()
        for(i in 2..input.size step 6) {
            val board = Board.fromString(input.slice(i..i+4))
            boards.add(board)
        }

        for(draw in draws) {
            println("==== Draw $draw   ======")
            for(board in boards) {
                board.markDraw(draw)
                if (board.isBingo()){
                    println(board)
                    println("Draw: $draw, Board remaining: ${board.remaining()}")
                    return draw * board.remaining()
                }
            }
        }
        TODO()
    }
    fun solvePart2(): Int {

        val draws = input[0].trim().splitIgnoreEmpty(",").map {it.toInt()}
        var boards = mutableListOf<Board>()
        for(i in 2..input.size step 6) {
            val board = Board.fromString(input.slice(i..i+4))
            boards.add(board)
        }

        for(draw in draws) {
            //println("==== Draw $draw   ======")
            for(board in boards) {
                board.markDraw(draw)
            }
            if(boards.size == 1) {
                if(boards[0].isBingo()) {
                    return boards[0].remaining() * draw
                }
            }
            boards = boards.filter { !it.isBingo() }.toMutableList()
            println("Remaining boards: ${boards.size}")

        }
        TODO()
    }
}