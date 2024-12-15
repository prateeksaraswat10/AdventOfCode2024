import common.readInputAsLines

fun main(args: Array<String>) {
    val input: List<String> = readInputAsLines("05Dec24_01.txt")

    val afterI = mutableMapOf<Int, MutableList<Int>>()
    val beforeI = mutableMapOf<Int, MutableList<Int>>()
    val lines = mutableListOf<List<Int>>()

    var processingRules = true
    input.forEach { it ->
        if (it.isEmpty()) {
            processingRules = false
        }
        if (processingRules) {
            val x = it.split("|")[0].toInt()
            val y = it.split("|")[1].toInt()
            val after = afterI.getOrDefault(x, mutableListOf())
            after.add(y)
            afterI[x] = after
            val before = beforeI.getOrDefault(y, mutableListOf())
            before.add(x)
            beforeI[y] = before
        } else {
            if (it.isNotEmpty()) {
                lines.add(it.split(",").map { ip -> ip.toInt() }.toList())
            }
        }
    }

    println("afterI $afterI")
    println("beforeI $beforeI")
    println("lines $lines")

    var result = 0
    var resultForFixed = 0

    for (line in lines) {
        if (isLineCorrect(line, beforeI, afterI)) {
            println("valid line $line")
            result += line[line.size / 2]
        } else {
            val fixedLine = fixLine(line, beforeI)
            println(
                "invalid line $line. \t fixed line $fixedLine. is line fixed ${
                    isLineCorrect(
                        fixedLine,
                        beforeI,
                        afterI
                    )
                }"
            )
            resultForFixed += fixedLine[fixedLine.size / 2]

        }
    }

    println("result $result")
    println("result for fixed $resultForFixed")
}

fun isLineCorrect(line: List<Int>, beforeI: Map<Int, List<Int>>, afterI: Map<Int, List<Int>>): Boolean {
    var correct = true
    for (idx in line.indices) { // for each number in line
        val num = line[idx]
        for (b in 0..<idx) {
            // numbers before don't appear in after
            if (afterI[num].orEmpty().contains(line[b])) {
                correct = false
            }
        }
        for (a in idx + 1..<line.size) {
            // numbers after don't appear in before
            if (beforeI[num].orEmpty().contains(line[a])) {
                correct = false
            }
        }
    }
    return correct
}

fun fixLine(line: List<Int>, beforeX: Map<Int, List<Int>>): List<Int> {
    // for each page in line, find out how many pages of the line should appear before it
    // sort by count of pages before it
    return line.map {
        it to beforeX[it].orEmpty().toSet().intersect(line.toSet()).size
    }.sortedBy { it.second }
        .map { it.first }
        .toList()
}

