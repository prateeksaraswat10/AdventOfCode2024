import java.nio.file.Paths
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.io.path.bufferedWriter
import kotlin.io.path.createFile
import kotlin.io.path.writer

fun main() {
    val cwd = Paths.get("").toAbsolutePath()
    var inputInitial =
        Paths.get(cwd.toString(), "src", "resources", "11DEC24_01.txt").toFile().readText()
    val basePath = Paths.get(cwd.toString(), "src", "output2").toString()
    val writer = Paths.get(basePath, "result_0.txt").writer()
    writer.write(inputInitial)
    writer.flush()
    writer.close()


    for (i in 1..75) {
        val start = Instant.now()
        Paths.get(basePath, "result_${i}.txt").createFile()
        val result = Paths.get(basePath, "result_${i}.txt").bufferedWriter()
        Paths.get(basePath, "result_${i - 1}.txt").toFile().bufferedReader().use { reader ->
            var char: Int
            var num = ""
            var chunk = ""
            while (reader.read().also { char = it } != -1) {
                if (char.toChar() == ' ') {
                    result.write(computeNextString(num.toLong()) + " ")
                    num = ""
                } else {
                    num += char.toChar().toString()
                }

            }
            result.write(computeNextString(num.toLong()))
        }
        result.flush()
        result.close()
        println("loop $i in duration ${Duration.of(Instant.now().toEpochMilli() - start.toEpochMilli(), ChronoUnit.MILLIS)}")
    }

    var result = 0
    Paths.get(basePath, "result_75.txt").toFile().bufferedReader().use {reader ->
        var char: Int
        while(reader.read().also { char = it } != -1) {
            if (char.toChar() == ' ') {
                result += 1
            }
        }
    }

    println("result ${result + 1}")

}

private fun computeNextString(current: Long): String {
    val stringRep = current.toString()
    return when {
        current == 0L -> (1L).toString()
        (stringRep.length >= 2) && ((stringRep.length % 2) == 0) -> {
            val first = stringRep.substring(0, stringRep.length / 2).toLong()
            val second = stringRep.substring(stringRep.length / 2, stringRep.length).toLong()
            return "$first $second"
        }

        else -> (current * 2024).toString()
    }
}