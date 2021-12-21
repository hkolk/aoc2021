package nl.hkolk.aoc2021

class Day21(val input: List<String>) {
    val player1 = input.first().splitIgnoreEmpty(" ").get(4).toInt()
    val player2 = input.drop(1).first().splitIgnoreEmpty(" ").get(4).toInt()

    fun solvePart1(): Int {
        class DeterministicDie() {
            var nextValue = 1
            fun roll(): Int {
                var ret = nextValue
                nextValue = (nextValue % 100) + 1
                return ret
            }
        }

        var p1Pos = player1
        var p2Pos = player2
        var p1Score = 0
        var p2Score = 0
        var round = 0
        val die = DeterministicDie()

        while(true) {
            if(round++ % 2 == 0) {
                // p1
                val role = die.roll() + die.roll() + die.roll()
                p1Pos = ((p1Pos+role-1) % 10) + 1
                p1Score += p1Pos
                println("Player 1 rolls $role and moves to space $p1Pos for a total of $p1Score")
                if(p1Score >= 1000) { return p2Score * round * 3 }
            } else {
                val role = die.roll() + die.roll() + die.roll()
                p2Pos = ((p2Pos+role-1) % 10) + 1
                p2Score += p2Pos
                println("Player 2 rolls $role and moves to space $p2Pos for a total of $p2Score")
                if(p2Score >= 1000) { return p1Score * round * 3 }            }


        }
        TODO()
    }
    fun solvePart2(): Long {
        val diracRoles = (1..3).flatMap { a-> (1..3).flatMap { b ->  (1..3).map { c-> a+b+c  }}}.groupingBy { it }.eachCount()

        data class Player(val pos:Int, val score:Int)


        fun play(p1: Player, p2: Player, round: Int): Pair<Long, Long> {
            var p1Wins:Long = 0
            var p2Wins:Long = 0
            if(round % 2 == 0) {
                for ((role, times) in diracRoles) {
                    val p1Pos = ((p1.pos + role - 1) % 10) + 1
                    val p1Score = p1.score + p1Pos
                    if (p1Score >= 21) {
                        p1Wins += times
                    } else {
                        play(Player(p1Pos, p1Score), p2, round + 1).let {
                            p1Wins += it.first * times
                            p2Wins += it.second * times
                        }
                    }
                }
            } else {
                for ((role, times) in diracRoles) {
                    val p2Pos = ((p2.pos + role - 1) % 10) + 1
                    val p2Score = p2.score + p2Pos
                    if (p2Score >= 21) {
                        p2Wins += times
                    } else {
                        play(p1, Player(p2Pos, p2Score), round + 1).let {
                            p1Wins += it.first * times
                            p2Wins += it.second * times
                        }
                    }
                }
            }
            return p1Wins to p2Wins
        }

        val outcome = play(Player(player1, 0), Player(player2, 0), 0)
        return maxOf(outcome.first, outcome.second)
    }

}