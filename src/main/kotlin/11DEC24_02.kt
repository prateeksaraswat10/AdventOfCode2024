import common.readInputAsText
import java.nio.file.Paths
import kotlin.io.path.createFile
import kotlin.io.path.writer

fun main() {
    val cwd = Paths.get("").toAbsolutePath()
    val inputInitial = readInputAsText("11DEC24_01.txt")
    val basePath = Paths.get(cwd.toString(), "src", "main", "resources", "output").toString()
    val writer = Paths.get(basePath, "result_0.txt").writer()
    writer.write(inputInitial)
    writer.flush()
    writer.close()

    val MAX_SAFE_LIMIT = 25

    // the file generated hit gigabytes in size after 40 or so. And will quickly grow to terabytes
    for (i in 1..MAX_SAFE_LIMIT) {
        Paths.get(basePath, "result_${i}.txt").createFile()
        val result = Paths.get(basePath, "result_${i}.txt").writer()
        Paths.get(basePath, "result_${i - 1}.txt").toFile().bufferedReader().useLines {
            var num = ""
            it.forEach { input ->
                input.forEach { char ->
                    if (char == ' ') {
                        result.write(computeNextString(num.toLong()) + " ")
                        num = ""
                    } else {
                        num += char.toString()
                    }
                }
            }
            result.write(computeNextString(num.toLong()))
        }
        result.flush()
        result.close()
        println("loop $i")
    }

    var result = 0
    Paths.get(basePath, "result_$MAX_SAFE_LIMIT.txt").toFile().bufferedReader().useLines {
        it.forEach { input ->
            input.forEach { c: Char ->
                if (c == ' ') {
                    result += 1
                }
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