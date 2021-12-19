package nl.hkolk.aoc2021

import java.lang.IllegalStateException

class Day18(val input: List<String>) {
    private val expressions = input.map { parser(it.iterator()) }

    class FishNode(var left: FishNode?=null, var right: FishNode?=null, var value:Int?=null) {
        fun plus(that: FishNode): FishNode {
            if (this.value == null) {
                return FishNode(this, that)
            } else {
                throw IllegalStateException("Adding value to pair?!")
            }
        }
        fun clone(): FishNode {
            return FishNode(left?.clone(), right?.clone(), value)
        }
        fun toSmallString(): String {
            return if(value == null) {
                "[${left!!.toSmallString()},${right!!.toSmallString()}]"
            } else {
                value.toString()
            }
        }
        override fun toString(): String = toSmallString()

        fun getMagnitude(): Int {
            return if(value != null) {
                value!!
            } else {
                left!!.getMagnitude() * 3 + right!!.getMagnitude() * 2
            }
        }

        fun findExplode(depth:Int=0): FishNode? {
            if(value != null) { return null }
            if(depth == 4 && left?.value != null && right?.value != null) { return this }
            left!!.findExplode(depth+1).let { if (it != null) return it }
            right!!.findExplode(depth+1).let { if (it != null) return it }
            return null
        }
        fun findSplit(): FishNode? {
            if((left?.value ?: 0) > 9) {
                return left
            }
            if(left?.value == null) {
                left?.findSplit().let { if(it != null) return it }
            }
            if((right?.value ?: 0) > 9) {
                return right
            }
            if(right?.value == null) {
                right?.findSplit().let { if(it != null) return it }
            }
            return null
        }
        fun replaceWith(find:FishNode, replacement: FishNode) {
            if(left == find) {
                left = replacement
            } else if (right == find) {
                right = replacement
            } else {
                left?.replaceWith(find, replacement)
                right?.replaceWith(find, replacement)
            }
        }
        fun getValues(): List<FishNode> {
            val ret = mutableListOf<FishNode>()
            if(left?.value != null) {
                ret.add(left!!)
            } else {
                ret.addAll(left!!.getValues())
            }
            if(right?.value != null) {
                ret.add(right!!)
            } else {
                ret.addAll(right!!.getValues())
            }
            return ret
        }
    }


    private fun parser(input: Iterator<Char>): FishNode {
        var left: FishNode? = null
        var right: FishNode? = null
        while(true) {
            when(val next = input.next()) {
                '[' -> left = parser(input)
                ',' -> right = parser(input)
                in '0'..'9' -> return FishNode(value=next.digitToInt())
                ']' -> {
                    return FishNode(left=left!!, right=right!!)
                }
            }
        }
    }
    // explode [[1, [2, 3]], 4]
    private fun processExplode(expression:FishNode, verbose:Boolean=false): Boolean {
        val explode = expression.findExplode()
        if(explode != null) {
            if(verbose) {println(explode)}
            val values = expression.getValues()
            if(verbose) {println(values)}
            val leftIndex = values.indexOfFirst { it == explode.left }
            if(verbose) {println(leftIndex)}
            if(leftIndex > 0) {
                values[leftIndex-1].value = values[leftIndex-1].value?.plus(explode.left?.value!!)
            }

            val rightIndex = values.indexOfFirst { it == explode.right }
            if(verbose) {println(rightIndex)}
            if(rightIndex < values.size-1) {
                values[rightIndex+1].value = values[rightIndex+1].value?.plus(explode.right?.value!!)
            }
            if(verbose) {println(values)}
            expression.replaceWith(explode, FishNode(value=0))
            if(verbose) {println(expression)}
            return true
        }
        return false
    }

    private fun processSplit(expression:FishNode, verbose:Boolean=false): Boolean {
        val split = expression.findSplit()
        if(split != null) {
            if(verbose) { println(split)}
            val left = FishNode(value=split.value!! / 2)
            val right = FishNode(value=(split.value!!/2) + (split.value!!%2))
            if(verbose) { println(FishNode(left, right))}

            expression.replaceWith(split, FishNode(left=left, right=right) )
            if(verbose) { println(expression)}
            return true
        }
        return false
    }

    private fun plusAndReduce(expression: FishNode, addition: FishNode, verbose: Boolean=false): FishNode {
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
    }
}