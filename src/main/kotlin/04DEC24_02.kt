import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("04Dec24_01.txt")
    val R: Int = input.size
    val C: Int = input[0].length

    var result = 0

    for (i in 0..<R) {
        for (j in 0..<C) {
            if (i - 1 >= 0 && i + 1 < R && j - 1 >= 0 && j + 1 < C && input[i][j].toString() == "A") {
                val pDiag: String =
                    input[i - 1][j - 1].toString() + input[i][j].toString() + input[i + 1][j + 1].toString()
                val sDiag: String = input[i + 1][j - 1].toString() + input[i][j].toString() + input[i - 1][j + 1]
                if ((pDiag == "MAS" || pDiag == "SAM") && (sDiag == "MAS" || sDiag == "SAM")) {
                    result += 1
                }
            }
        }
    }

    println("X-MAS count $result")
}

