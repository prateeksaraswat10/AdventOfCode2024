import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("09Dec24_01.txt")
    var processInput = ArrayList<String>()
    var file = true
    var fileName = 0
    for (i in input.indices) {
        val line = ArrayList<String>()
        for (j in input[i].indices) {
            if (file) {
                for (k in 0..<input[i][j].digitToInt()) {
                    line.add(fileName.toString())
                }
                fileName++
                file = false
            } else {
                for (k in 0..<input[i][j].digitToInt()) {
                    line.add(".")
                }
                file = true
            }
        }
        processInput = line
    }

    println("input $input")
    println("process input unsorted $processInput")

    for (i in processInput.indices.reversed()) {
        if (processInput[i] != ".") {
            val freeSpace = processInput.subList(0, i).indexOfFirst { it == "." }
            if (freeSpace != -1) {
                processInput[freeSpace] = processInput[i]
                processInput[i] = "."
            }
        }
    }
    println("process input sorted $processInput")

    var checksum = 0L
    for (i in processInput.indices) {
        if (processInput[i] != ".") {
            checksum += i * processInput[i].toLong()
        }
    }

    println("checksum $checksum")
}