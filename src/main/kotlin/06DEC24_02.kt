import common.readInputAsLines
import kotlin.io.path.Path

fun main() {
    val cwd = Path("").toAbsolutePath().toString()
    val input: MutableList<String> = readInputAsLines("06Dec24_01.txt").toMutableList()
    val faces = setOf("<", "^", "v", ">")
    var gPos: Pair<Int, Int> = 0 to 0
    var foundG = false
    mutableSetOf<Pair<Int, Int>>()

    for (i in input.indices) {
        val line = input[i]
        for (j in line.indices) {
            if (faces.contains(input[i][j].toString())) {
                if (foundG) {
                    println("Two guards!!")
                }
                foundG = true
                gPos = i to j
            }
        }
    }

    var stopping = 0

    for (i in input.indices) {
        for (j in input.indices) {
            if (input[i][j].toString() != "#" && !faces.contains(input[i][j].toString())) {
                val newInput = input.toMutableList()
                val line = newInput[i]
                val lineBuilder = StringBuilder(line)
                lineBuilder.setCharAt(j, '#')
                newInput[i] = lineBuilder.toString()
                if (!canGuardEscape(newInput, gPos)) {
                    stopping += 1
                    println("Stopped by obstacle at $i, $j")
                }
            }
        }
    }

    println("result $stopping")
}

fun canGuardEscape(input: List<String>, gPosIn: Pair<Int, Int>): Boolean {
    var gPos = gPosIn
    val visitedWhileFacing = mutableSetOf<Triple<Int, Int, String>>()
    var gFace = input[gPos.first][gPos.second].toString()
    var onEdge = false
    var canEscape = true
    while (!onEdge && canEscape) {
        val currentPosAndFace = Triple(gPos.first, gPos.second, gFace)
        if (visitedWhileFacing.contains(currentPosAndFace)) {
            canEscape = false
        } else {
            visitedWhileFacing.add(currentPosAndFace)
        }
        if (gPos.first == 0 || gPos.first ==  input.size  - 1 || gPos.second == 0 || gPos.second == input[0].length - 1) {
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
                    if (input[gPos.first][gPos.second -1].toString() == "#") {
                        gFace = "^"
                    } else {
                        gPos = gPos.first to gPos.second - 1
                    }
                }
            }
        }
    }
    return canEscape
}