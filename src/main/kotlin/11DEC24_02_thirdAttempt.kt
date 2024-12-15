import common.readInputAsText
import java.nio.file.Paths

fun main() {
    val cwd = Paths.get("").toAbsolutePath()
    var inputInitial = readInputAsText("11DEC24_01.txt")
    val input = inputInitial.split(" ").filter { it != " " }.map { it.toLong() }

    var result = 0L
    for (item in input) {
        result += solve(item, 75)
    }

    println("result $result")
}


private val dpCalc = mutableMapOf<Pair<Long, Long>, Long>()

private fun solve(x: Long, t: Long): Long {
    val result: Long
    if (dpCalc.containsKey(x to t)) {
        return dpCalc[x to t]!!
    }
    if (t == 0L) {
        result = 1
    } else if (x == 0L) {
        result = solve(1, t - 1)
    } else if (x.toString().length >= 2 && x.toString().length % 2 == 0) {
        val digit = x.toString()
        val one = digit.substring(0, digit.length / 2).toLong()
        val two = digit.substring(digit.length / 2, digit.length).toLong()
        result = solve(one, t - 1) + solve(two, t - 1)
    } else {
        result = solve(x * 2024, t - 1)
    }
    dpCalc[x to t] = result
    return result
}