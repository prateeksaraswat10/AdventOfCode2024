import common.readInputAsLines
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.pow

fun main() {
    val input: List<String> = readInputAsLines("07Dec24_01.txt")
    val calList = mutableListOf<Cal>()
    input.forEach { line ->
        val total = line.split(":")[0].trim().toLong()
        val values: List<Long> = line.split(":")[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }
        calList.add(Cal(total, values))
    }
    var result = 0L
    val start = Instant.now()
    calList.forEach {
        if (it.isAnyCalValid()) {
            result += it.total
        }
    }

    println(
        "result $result in ${
            Duration.of(Instant.now().toEpochMilli() - start.toEpochMilli(), ChronoUnit.MILLIS).toMillis()
        }msec"
    )
}

data class Cal(val total: Long, val list: List<Long>) {
    fun computeAllCals() {
        val allOperations = mutableListOf<IntArray>()
        allOperations.add(IntArray(list.size - 1) { 0 })
        while (allOperations.size < 2f.pow(list.size - 1)) {
            val ops = allOperations.last().copyOf()
            allOperations.add(addOneToOps(ops))
        }
        allOperations.forEach {
            val computeOps = computeOps(it)
        }
    }

    fun isAnyCalValid(): Boolean {
        val allOperations = mutableListOf<IntArray>()
        allOperations.add(IntArray(list.size - 1) { 0 })
        while (allOperations.size < 2f.pow(list.size - 1)) {
            val ops = allOperations.last().copyOf()
            allOperations.add(addOneToOps(ops))
        }
        allOperations.forEach {
            val computeOps = computeOps(it)
            if (computeOps == total) {
                return true
            }
        }
        return false
    }

    private fun addOneToOps(ops: IntArray): IntArray {
        var carry = 1
        for (i in ops.indices) {
            val (r, c) = binAdd(ops[i], carry)
            ops[i] = r
            carry = c
        }
        return ops
    }

    private fun binAdd(a: Int, b: Int): Pair<Int, Int> {
        when {
            a == 0 && b == 0 -> return 0 to 0
            a == 1 && b == 0 -> return 1 to 0
            a == 0 && b == 1 -> return 1 to 0
            a == 1 && b == 1 -> return 0 to 1
        }
        error("Illegal state $a, $b")
    }

    private fun computeOps(ops: IntArray): Long {
        var result = 0L
        if (ops.size != list.size - 1) {
            error("Illegal ops size ${ops.toSsv()}, $list")
        }
        for (i in 0..list.size - 2) {
            if (i == 0) {
                result = computeOp(list[i], list[i + 1], ops[i])
            } else {
                result = computeOp(result, list[i + 1], ops[i])
            }
        }
        return result
    }
}

fun IntArray.toSsv(): String {
    val ops = mapOf(
        0 to "*",
        1 to "+"
    )
    var result = ""
    forEach {
        result += "${ops[it]} "
    }
    return result
}

fun computeOp(ip1: Long, ip2: Long, op: Int): Long {
    when (op) {
        0 -> {
            return ip1 * ip2
        }

        1 -> {
            return ip1 + ip2
        }

        else -> {
            error("Illegal ops value")
        }
    }
}