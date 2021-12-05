package nl.hkolk.aoc2021

import java.lang.IllegalStateException

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
                if(board.slice(range).none { it != -1 }) {
                    return true
                }
            }
            return false
        }

        override fun toString(): String {
            return board.chunked(5).joinToString("\n") { it.toString() }
        }

        fun remaining(): Int {
            return board.filter { it != -1 }.sum()
        }
        companion object {
            fun fromString(input: List<String>): Board {
                val rows = input.map { s -> s.splitIgnoreEmpty(" ").map { it.toInt() } }
                return Board(rows.flatten())
            }
        }
    }

    private val draws = input[0].trim().splitIgnoreEmpty(",").map {it.toInt()}
    private val boards = (2..input.size step 6).map { Board.fromString(input.slice(it..it+4)) }

    fun solvePart1(): Int {
        for(draw in draws) {
            for(board in boards) {
                board.markDraw(draw)
                if (board.isBingo()){
                    return draw * board.remaining()
                }
            }
        }
        throw IllegalStateException("Could not get a bingo")
    }
    fun solvePart2(): Int {

        var remainingBoards = boards
        for(draw in draws) {
            for(board in remainingBoards) {
                board.markDraw(draw)
            }
            if(remainingBoards.size == 1) {
                if(remainingBoards[0].isBingo()) {
                    return remainingBoards[0].remaining() * draw
                }
            }
            remainingBoards = remainingBoards.filter { !it.isBingo() }

        }
        throw IllegalStateException("Did not have a winning board")
    }
}