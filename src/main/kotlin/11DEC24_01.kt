import common.readInputAsLines
import common.readInputAsText

fun main() {
    val input: MutableList<Long> = readInputAsText("11DEC24_01.txt").split(" ").map { it.toLong() }.toMutableList()
    val MAX_SAFE_LIMIT = 25
    println("input $input")
    var result: MutableList<Long> = input
    for (i in 1..MAX_SAFE_LIMIT) { // this program fails whe loop crosses 38 or so
        val newResult = mutableListOf<Long>()
        for (item in result) {
            newResult.addAll(computeNext(item))
        }
        println("loop $i")
        result = newResult
    }

    println("answer ${result.size}")
}

private fun computeNext(current: Long): List<Long> {
    val stringRep = current.toString()
    return when {
        current == 0L -> listOf(1L)
        (stringRep.length >= 2) && ((stringRep.length % 2) == 0) -> {
            val first = stringRep.substring(0, stringRep.length / 2).toLong()
            val second = stringRep.substring(stringRep.length / 2, stringRep.length).toLong()
            listOf(first, second)
        }

        else -> listOf(current * 2024)
    }
}