import common.readInputAsLines

fun main() {
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
    for (line in lines) {
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
        if (correct) {
            println("valid line $line")
            result += line[line.size / 2]
        }
    }

    println("result $result")
}

