import common.readInputAsLines

fun main() {
    val input: MutableList<String> = readInputAsLines("06Dec24_01.txt").toMutableList()
    var guard = setOf("<", "^", "v", ">")
    var gPos: Pair<Int, Int> = 0 to 0
    var foundG = false
    val visited = mutableSetOf<Pair<Int, Int>>()

    for (i in input.indices) {
        val line = input[i]
        for (j in line.indices) {
            if (guard.contains(input[i][j].toString())) {
                if (foundG) {
                    println("Two guards!!")
                }
                foundG = true
                gPos = i to j
            }
        }
    }

    var gFace = input[gPos.first][gPos.second].toString()
    var onEdge = false
    while (!onEdge) {
        visited.add(gPos.first to gPos.second)
        if (gPos.first == 0 || gPos.first == input.size - 1 || gPos.second == 0 || gPos.second == input[0].length - 1) {
            onEdge = true
        }
        if (!onEdge) {
            when (gFace) {
                // up
                "^" -> {
                    if (input[gPos.first - 1][gPos.second].toString() == "#") {
                        gFace = ">"
                    } else {
                        gPos = gPos.first - 1 to gPos.second
                    }
                }
                // down
                "v" -> {
                    if (input[gPos.first + 1][gPos.second].toString() == "#") {
                        gFace = "<"
                    } else {
                        gPos = gPos.first + 1 to gPos.second
                    }
                }
                // right
                ">" -> {
                    if (input[gPos.first][gPos.second + 1].toString() == "#") {
                        gFace = "v"
                    } else {
                        gPos = gPos.first to gPos.second + 1
                    }
                }
                // left
                "<" -> {
                    if (input[gPos.first][gPos.second - 1].toString() == "#") {
                        gFace = "^"
                    } else {
                        gPos = gPos.first to gPos.second - 1
                    }
                }
            }
        }
    }

    println("Guard exit at $gPos, facing $gFace, cells visited ${visited.size}")

}