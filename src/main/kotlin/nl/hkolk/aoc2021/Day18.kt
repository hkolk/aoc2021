package nl.hkolk.aoc2021

import java.lang.IllegalStateException

class Day18(val input: List<String>) {
    private val expressions = input.map { parser(it.iterator()) }

    sealed class FishTerm(var parent: FishPair? = null) {
        fun plus(that: FishTerm): FishPair = when(this) {
                is FishPair -> FishPair(this, that)
                is FishValue -> throw IllegalStateException("Adding value to pair?!")
            }
        abstract fun toSmallString() : String
        abstract fun getMagnitude(): Int
        abstract fun clone(): FishTerm
    }
    class FishPair(var left:FishTerm, var right:FishTerm): FishTerm() {
        override fun toSmallString(): String = "[${left.toSmallString()},${right.toSmallString()}]"
        override fun toString(): String = toSmallString()

        override fun getMagnitude(): Int {
            return left.getMagnitude() * 3 + right.getMagnitude() * 2
        }
        override fun clone(): FishPair {
            return FishPair(left.clone(), right.clone())
        }

        fun findExplode(depth:Int=0): FishPair? {
            if(depth == 4 && left is FishValue && right is FishValue) { return this }
            if(left is FishPair) {
                val res = (left as FishPair).findExplode(depth+1)
                if(res != null) {
                    return res
                }
            }
            if(right is FishPair) {
                val res = (right  as FishPair).findExplode(depth+1)
                if(res != null) {
                    return res
                }
            }
            return null
        }

        fun findSplit(): FishValue? {
            if(left is FishValue && (left as FishValue).value > 9) {
                return left as FishValue
            }
            if(left is FishPair) {
                val res = (left as FishPair).findSplit()
                if(res != null) {
                    return res
                }
            }
            if(right is FishValue && (right as FishValue).value > 9) {
                return right as FishValue
            }
            if(right is FishPair) {
                val res = (right  as FishPair).findSplit()
                if(res != null) {
                    return res
                }
            }
            return null
        }

        fun replaceWith(find:FishTerm, replacement: FishTerm) {
            if(left == find) {
                left = replacement
            } else if (right == find) {
                right = replacement
            } else {
                if(left is FishPair) {
                    (left as FishPair).replaceWith(find, replacement)
                }
                if(right is FishPair) {
                    (right as FishPair).replaceWith(find, replacement)
                }
            }
        }

        fun getValues(): List<FishValue> {
            val ret = mutableListOf<FishValue>()
            when(left) {
                is FishValue -> ret.add(left as FishValue)
                is FishPair -> ret.addAll((left as FishPair).getValues())
            }
            when(right) {
                is FishValue -> ret.add(right as FishValue)
                is FishPair -> ret.addAll((right as FishPair).getValues())
            }
            return ret
        }
    }

    class FishValue(var value:Int): FishTerm() {
        override fun toSmallString(): String = value.toString()
        override fun toString(): String = value.toString()
        override fun getMagnitude(): Int {
            return value
        }
        override fun clone(): FishValue {
            return FishValue(value)
        }
    }



    private fun parser(input: Iterator<Char>): FishTerm {
        var left: FishTerm? = null
        var right: FishTerm? = null
        while(true) {
            when(val next = input.next()) {
                '[' -> left = parser(input)
                ',' -> right = parser(input)
                in '0'..'9' -> return FishValue(next.digitToInt())
                ']' -> {
                    val ret = FishPair(left!!, right!!)
                    ret.left.parent = ret
                    ret.right.parent = ret
                    return ret
                }
            }
        }
    }
    // explode [[1, [2, 3]], 4]
    private fun processExplode(expression:FishPair, verbose:Boolean=false): Boolean {
        val explode = expression.findExplode()
        if(explode != null) {
            if(verbose) {println(explode)}
            val values = expression.getValues()
            if(verbose) {println(values)}
            val leftIndex = values.indexOfFirst { it == explode.left }
            if(verbose) {println(leftIndex)}
            if(leftIndex > 0) {
                values[leftIndex-1].value += (explode.left as FishValue?)?.value!!
            }

            val rightIndex = values.indexOfFirst { it == explode.right }
            if(verbose) {println(rightIndex)}
            if(rightIndex < values.size-1) {
                values[rightIndex+1].value += (explode.right as FishValue?)?.value!!
            }
            if(verbose) {println(values)}
            expression.replaceWith(explode, FishValue(0))
            if(verbose) {println(expression)}
            return true
        }
        return false
    }

    fun processSplit(expression:FishPair, verbose:Boolean=false): Boolean {
        val split = expression.findSplit()
        if(split != null) {
            if(verbose) { println(split)}
            val left = FishValue(split.value / 2)
            val right = FishValue((split.value/2) + (split.value%2))
            if(verbose) { println(FishPair(left, right))}

            expression.replaceWith(split, FishPair(left, right) )
            if(verbose) { println(expression)}
            return true
        }
        return false
    }

    private fun plusAndReduce(expression: FishTerm, addition: FishTerm, verbose: Boolean=false): FishPair {
        val result = expression.clone().plus(addition.clone())
        if(verbose) { println("Addition!") }
        if(verbose) { println(result.toSmallString()) }

        while (true) {
            if (processExplode(result, verbose)) {
                if(verbose) { println("Explode!")}
                if(verbose) { println(result.toSmallString())}
                continue
            }
            if (processSplit(result, verbose)) {
                if(verbose) { println("Split!") }
                if(verbose) { println(result.toSmallString()) }
                continue
            }
            if(verbose) { println("no more mutations") }
            if(verbose) { println(result.toSmallString()) }
            break
        }
        return result
    }

    fun solvePart1(): Int {
        var result = expressions[0]
        val verbose = false
        for(addition in expressions.drop(1)) {
            result = plusAndReduce(result, addition, verbose)
        }
        if(verbose) { println("Result: ") }
        if(verbose) { println(result) }
        return result.getMagnitude()
    }
    fun solvePart2(): Int {
        val combinations = expressions.combinations(2).flatMap { combo ->
            listOf(
                plusAndReduce(combo.first(), combo.last()).getMagnitude(),
                plusAndReduce(combo.last(), combo.first()).getMagnitude()
            )
        }
        return combinations.maxOf { it }
        TODO()
    }
}