import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("09Dec24_01.txt")
    val files = mutableListOf<Triple<Int, Int, Int>>()
    val spaces = mutableListOf<Pair<Int, Int>>()
    var fileId = 0
    var pos = 0
    val processedInput = mutableListOf<String>()
    for (i in input[0].indices) {
        val itemCount = input[0][i].digitToInt()
        if (i % 2 == 0) {
            files.add(Triple(pos, itemCount, fileId))
            for (j in 0..<itemCount) {
                processedInput.add(fileId.toString())
                pos++
            }
            fileId++
        } else {
            spaces.add(pos to itemCount)
            for (j in 0..<itemCount) {
                processedInput.add(".")
                pos++
            }
        }
    }
    println("$processedInput")

    for (j in files.indices.reversed()) {
        val filePos = files[j].first
        val fileSize = files[j].second
        val id = files[j].third
        for (spaceIndex in spaces.indices) {
            val spacePos = spaces[spaceIndex].first
            val spaceSize = spaces[spaceIndex].second
            if (spacePos < filePos && fileSize <= spaceSize) {
                for (i in 0..<fileSize) {
                    assert(processedInput[filePos + i] == id.toString()) { processedInput[filePos + i] }
                    processedInput[filePos + i] = "."
                    processedInput[spacePos + i] = id.toString()
                }
                spaces[spaceIndex] = spacePos + fileSize to spaceSize - fileSize
                break
            }
        }
    }

    println("processedInput $processedInput")

    var checksum = 0L
    for (i in processedInput.indices) {
        if (processedInput[i] != ".") {
            checksum += i * processedInput[i].toLong()
        }
    }

    println("checksum $checksum")
}