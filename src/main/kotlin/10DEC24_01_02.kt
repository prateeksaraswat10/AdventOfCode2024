import common.Point
import common.dirs
import common.readInputAsLines
import java.util.*

fun main() {
    val input: List<String> = readInputAsLines("10Dec24_01.txt")

    val R = input.size
    val C = input[0].length

    var score = 0

    // BFS
    for (i in input.indices) {
        for (j in input[0].indices) {
            val startP = input[i][j]
            if (startP == '0') {
                val seen = mutableSetOf<Point>()
                val paths: Queue<Point> = LinkedList<Point>()
                val start = Point(i, j)
                paths.add(start)
                while (paths.isNotEmpty()) {
                    val current = paths.remove()
                    if (seen.contains(current)) {
                        continue
                    }
                    seen.add(current)
                    val currentVal = input[current.x][current.y]
                    if (currentVal == '9') {
                        score++
                    }
                    for (dir in dirs) {
                        val next = current + dir
                        val nI = next.x
                        val nJ = next.y
                        if (nI in 0..<R && nJ >= 0 && nJ < C) {
                            val nextVal = input[nI][nJ].digitToInt()
                            if (nextVal - currentVal.digitToInt() == 1) {
                                paths.add(next)
                            }
                        }
                    }
                }
            }
        }
    }
    println("result part 1 $score")
    score = 0

    //BFS no memory
    for (i in input.indices) {
        for (j in input[0].indices) {
            val startP = input[i][j]
            if (startP == '0') {
                val paths: Queue<Point> = LinkedList<Point>()
                val start = Point(i, j)
                paths.add(start)
                while (paths.isNotEmpty()) {
                    val current = paths.remove()
                    val currentVal = input[current.x][current.y]
                    if (currentVal == '9') {
                        score++
                    }
                    for (dir in dirs) {
                        val next = current + dir
                        val nI = next.x
                        val nJ = next.y
                        if (nI in 0..<R && nJ >= 0 && nJ < C) {
                            val nextVal = input[nI][nJ].digitToInt()
                            if (nextVal - currentVal.digitToInt() == 1) {
                                paths.add(next)
                            }
                        }
                    }
                }
            }
        }
    }
    println("result part 2 $score")
}