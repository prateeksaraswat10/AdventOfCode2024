import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.io.path.Path

fun main() {
    val cwd = Path("").toAbsolutePath().toString()
    val input: List<String> = Path(cwd, "src", "main", "resources", "07Dec24_01.txt").toFile().readLines()
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
        "result with memo $result in ${
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

    return false
}