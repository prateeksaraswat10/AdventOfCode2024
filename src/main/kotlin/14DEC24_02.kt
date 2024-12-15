import common.Point
import common.readInputAsLines
import java.util.LinkedList
import java.util.Queue

fun main() {
    val input = readInputAsLines("14DEC24_01.txt")


    val R = 103
    val C = 101
    val robots = mutableListOf<Robot>()

    input.forEach { line ->
        // p=2,4 v=2,-3
        val c = line.substring(line.indexOfFirst { it == '=' } + 1, line.indexOfFirst { it == ',' }).trim().toInt()
        val r = line.substring(line.indexOfFirst { it == ',' } + 1, line.indexOfFirst { it == ' ' }).trim().toInt()
        val vc = line.substring(line.indexOfLast { it == '=' } + 1, line.indexOfLast { it == ',' }).trim().toInt()
        val vr = line.substring(line.indexOfLast { it == ',' } + 1, line.length).trim().toInt()

        robots.add(Robot(Point(c, r), Velocity(vc, vr)))
    }

    for (i in 1..<10_000) {
        val movedRobotsList = robots.map { it -> mapToEnd(it, i, R, C) }.toList()
        val roboCount = mutableMapOf<Point, Int>()
        movedRobotsList.forEach { it ->
            val (c, r) = it
            val loc = Point(c, r)
            if (roboCount.containsKey(loc)) {
                roboCount[loc] = roboCount[loc]!! + 1
            } else {
                roboCount[loc] = 1
            }

        }

        var components = 0
        val allSeen = mutableSetOf<Point>()
        roboCount.keys.forEach {
            if (!allSeen.contains(it)) {
                components += 1
                val seen = mutableSetOf<Point>()
                val queue: Queue<Point> = LinkedList()
                queue.add(it)
                while (queue.isNotEmpty()) {
                    val dirs = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))
                    val cur = queue.remove()
                    if (seen.contains(cur)) {
                        continue
                    }
                    seen.add(cur)
                    for (dir in dirs) {
                        val (c, r) = cur + dir
                        val nxt = cur + dir
                        if (c in 0..<C && r in 0..<R && roboCount.keys.contains(nxt) && !seen.contains(nxt)) {
                            queue.add(nxt)
                            allSeen.add(cur)
                        }
                    }
                }
            }
        }

        if (components < 300) {
            val finalPos = mutableListOf<String>()
            for (row in 0..<R) {
                var line = ""
                for (col in 0..<C) {
                    if (roboCount.containsKey(Point(col, row))) {
                        line += "#"
                    } else {
                        line += "."
                    }
                }
                finalPos.add(line)
            }
            println("painting for $i, components $components")
            finalPos.forEach {
                println(it)
            }
        }
    }

    println("Program end")
}