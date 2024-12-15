import common.Point
import common.dirs
import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("10Dec24_01_test.txt")

    var score = 0

    for (i in input.indices) {
        for (j in input.indices) {
            if (input[i][j] == '9') {
                score += ways(i, j, input, dirs)
            }
        }
    }

    println("result $score")
}

private val dpMap: MutableMap<Point, Int> = mutableMapOf()

fun ways(r: Int, c: Int, input: List<String>, dirs: List<Point>): Int {
    val R = input.size
    val C = input[0].length
    if (input[r][c] == '0') {
        return 1
    }
    val point = Point(r, c)
    if (dpMap.containsKey(point)) {
        return dpMap[point]!!
    }
    var ans = 0
    for (dir in dirs) {
        val newPoint = point + dir
        if (newPoint.x in 0..<R && newPoint.y in 0..<C) {
            val diff = input[newPoint.x][newPoint.y].digitToInt() - input[point.y][point.y].digitToInt()
            if (diff == -1) {
                ans += ways(newPoint.x, newPoint.y, input, dirs)
            }
        }
    }
    dpMap[point] = ans
    return ans
}