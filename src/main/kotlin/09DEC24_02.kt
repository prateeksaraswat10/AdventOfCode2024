import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("09Dec24_01_test.txt")
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
            val start = processInput.indexOfFirst { it == processInput[i] }

            val size = i - start + 1
            val freeSpaces: List<String> = processInput.subList(0, start - 1)
            var space = -1
            var spaceStart = false
            var found = false
            for (j in freeSpaces.indices) {
                if (!spaceStart && freeSpaces[j] == ".") {
                    space = j
                    spaceStart = true
                }
                if (spaceStart && freeSpaces[j] != ".") {
                    if (size <= j - space) {
                        found = true
                        break
                    }
                    spaceStart = false
                }
            }

            if (found) {
                val subList = processInput.subList(start, i)
                for (j in subList.indices.reversed()) {
                    processInput[space] = subList[j]
                    subList[j] = "."
                }
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