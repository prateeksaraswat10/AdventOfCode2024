import common.readInputAsLines
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

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
        if (isValid(ArrayList(it.list), it.total)) {
            result += it.total
        }
    }

    println(
        "result with recursion $result in ${
            Duration.of(
                Instant.now().toEpochMilli() - start.toEpochMilli(),
                ChronoUnit.MILLIS
            ).toMillis()
        }msec"
    )
}

private fun isValid(list: ArrayList<Long>, target: Long): Boolean {
    if (list.size == 1) return list[0] == target

    val addList = ArrayList(listOf(list[0] + list[1]))
    addList.addAll(list.subList(2, list.size))
    if (isValid(addList, target)) return true

    val mulList = ArrayList(listOf(list[0] * list[1]))
    mulList.addAll(list.subList(2, list.size))
    if (isValid(mulList, target)) return true

    val concatList = ArrayList(listOf("${list[0]}${list[1]}".toLong()))
    concatList.addAll(list.subList(2, list.size))
    if (isValid(concatList, target)) return true

    return false
}