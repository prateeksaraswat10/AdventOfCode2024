import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("04Dec24_01.txt")
    val R: Int = input.size
    val C: Int = input[0].length

    var result = 0

    for (i in 0..<R) {
        for (j in 0..<C) {
            // left right
            if (j + 3 < C && input[i][j].toString() == "X" && input[i][j + 1].toString() == "M" && input[i][j + 2].toString() == "A" && input[i][j + 3].toString() == "S") {
                result += 1
            }
            if (j + 3 < C && input[i][j].toString() == "S" && input[i][j + 1].toString() == "A" && input[i][j + 2].toString() == "M" && input[i][j + 3].toString() == "X") {
                result += 1
            }

            // up down
            if (i + 3 < R && input[i][j].toString() == "X" && input[i + 1][j].toString() == "M" && input[i + 2][j].toString() == "A" && input[i + 3][j].toString() == "S") {
                result += 1
            }
            if (i + 3 < R && input[i][j].toString() == "S" && input[i + 1][j].toString() == "A" && input[i + 2][j].toString() == "M" && input[i + 3][j].toString() == "X") {
                result += 1
            }

            // right down
            if (j + 3 < C && i + 3 < R && input[i][j].toString() == "X" && input[i + 1][j + 1].toString() == "M" && input[i + 2][j + 2].toString() == "A" && input[i + 3][j + 3].toString() == "S") {
                result += 1
            }
            if (j + 3 < C && i + 3 < R && input[i][j].toString() == "S" && input[i + 1][j + 1].toString() == "A" && input[i + 2][j + 2].toString() == "M" && input[i + 3][j + 3].toString() == "X") {
                result += 1
            }

            // left down
            if (i + 3 < R && j - 3 >= 0 && input[i][j].toString() == "X" && input[i + 1][j - 1].toString() == "M" && input[i + 2][j - 2].toString() == "A" && input[i + 3][j - 3].toString() == "S") {
                result += 1
            }
            if (i + 3 < R && j - 3 >= 0 && input[i][j].toString() == "S" && input[i + 1][j - 1].toString() == "A" && input[i + 2][j - 2].toString() == "M" && input[i + 3][j - 3].toString() == "X") {
                result += 1
            }
        }
    }

    println("XMAS found $result")
}

