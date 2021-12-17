package nl.hkolk.aoc2021

import java.io.ByteArrayInputStream
import kotlin.math.abs


class Day16(val input: List<String>) {

    class BitInputStream(input: String) {
        private val inputStream = ByteArrayInputStream(hexStringToByteArray(input))
        private var bitsBuffer = -1
        private var remainingBits = 0

        fun read(): Int {
            if (remainingBits == 0) {
                bitsBuffer = inputStream.read()
                remainingBits = Byte.SIZE_BITS
            }
            if (bitsBuffer == -1) {
                println("one bit too many")
                return -1
            }
            remainingBits--
            return bitsBuffer shr remainingBits and 1
        }

        fun readBitsAsInt(number: Int): Int {
            return (number - 1 downTo 0).sumOf { read() shl it }
        }

        private fun hexStringToByteArray(s: String): ByteArray {
            return (s.indices step 2).map {
                ((s[it].digitToInt(16) shl 4) + (s[it + 1].digitToInt(16))).toByte()
            }.toByteArray()
        }
    }

    data class Packet(val version: Int, val type: Int, val subpackets: List<Packet>, val value: Long?) {

        fun sumVersion(): Int {
            return version + subpackets.sumOf { it.sumVersion() }
        }

        fun getValue(): Long {
            return when(type) {
                0 -> subpackets.sumOf { it.getValue() }
                1 -> subpackets.map { it.getValue() }.multiply()
                2 -> subpackets.minOf { it.getValue() }
                3 -> subpackets.maxOf { it.getValue() }
                4 -> value!!
                5 -> if(subpackets[0].getValue() > subpackets[1].getValue()) { 1 } else { 0 }
                6 -> if(subpackets[0].getValue() < subpackets[1].getValue()) { 1 } else { 0 }
                7 -> if(subpackets[0].getValue() == subpackets[1].getValue()) { 1 } else { 0 }
                else -> throw IllegalStateException()
            }
        }

        fun prettyPrint(indent:Int=0) {
            val indentSpace = " ".repeated(indent).joinToString("")
            print("$indentSpace{ v: $version, t: $type, val: $value")
            if(subpackets.isNotEmpty()) {
                println()
                subpackets.forEach { it.prettyPrint(indent + 1) }
                println("$indentSpace}")

            } else {
                println(" }")
            }
        }

        companion object  {
            fun fromStream(stream: BitInputStream): Pair<Packet, Int> {
                var bitsRead = 0
                val version = stream.readBitsAsInt(3)
                bitsRead+=3
                val type = stream.readBitsAsInt(3)
                bitsRead+=3
                var value: Long? = null
                val subpackets = mutableListOf<Packet>()

                if(type == 4) {
                    readType4(stream).let {
                        value=it.first
                        bitsRead += it.second
                    }
                } else {
                    // operator package
                    val lengthType = stream.read()
                    bitsRead+=1

                    if(lengthType == 0) {
                        // 15 length
                        val length = stream.readBitsAsInt(15)
                        bitsRead+=15
                        var subpacketBitsRead = 0
                        while(subpacketBitsRead < length) {
                            val (subpacket, read) = fromStream(stream)
                            subpacketBitsRead += read
                            subpackets.add(subpacket)
                        }
                        bitsRead+=subpacketBitsRead
                    } else {
                        // 11 length
                        val length = stream.readBitsAsInt(11)
                        bitsRead+=11
                        var subpacketBitsRead = 0
                        for(i in 0 until length) {
                            val (subpacket, read) = fromStream(stream)
                            subpacketBitsRead += read
                            subpackets.add(subpacket)
                        }
                        bitsRead+=subpacketBitsRead
                    }
                }
                return Packet(version, type, subpackets, value) to bitsRead
            }
            private fun readType4(stream: BitInputStream): Pair<Long, Int> {
                val groups = mutableListOf<Long>()
                var read = 0
                while (stream.read() == 1) {
                    groups.add(stream.readBitsAsInt(4).toLong())
                    read+=5
                }
                groups.add(stream.readBitsAsInt(4).toLong())
                read+=5
                if(groups.size> 15) {
                    println(groups)
                    println(groups.fold(0L) { accu, item -> (accu shl 4) + item })
                    throw IllegalStateException("Overflow!!!")
                }
                return groups.fold(0L) { accu, item -> (accu shl 4) + item } to read
            }
        }
    }

    @Suppress("unused")
    fun parseAndPrint(): Int {
        input.map {
            val stream = BitInputStream(it)
            val (packet, _) = Packet.fromStream(stream)
            packet.prettyPrint()
            println("$it = ${packet.sumVersion()}")
        }
        return 0
    }

    fun solvePart1(): Int {
        val stream = BitInputStream(input[0])
        val (packet, _) = Packet.fromStream(stream)
        return packet.sumVersion()
    }
    fun solvePart2(): Long {
        val stream = BitInputStream(input[0])
        val (packet, _) = Packet.fromStream(stream)
        return packet.getValue()
    }
}