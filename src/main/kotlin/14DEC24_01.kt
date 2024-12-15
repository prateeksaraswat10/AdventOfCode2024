import common.Point
import common.readInputAsLines

fun main() {
    val input = readInputAsLines("14DEC24_01.txt")

//    size for example
//    val R = 7
//    val C = 11
    val R = 103
    val C = 101
    val ticks = 100
    val robots = mutableListOf<Robot>()

    input.forEach { line ->
        // p=2,4 v=2,-3
        val c = line.substring(line.indexOfFirst { it == '=' } + 1, line.indexOfFirst { it == ',' }).trim().toInt()
        val r = line.substring(line.indexOfFirst { it == ',' } + 1, line.indexOfFirst { it == ' ' }).trim().toInt()
        val vc = line.substring(line.indexOfLast { it == '=' } + 1, line.indexOfLast { it == ',' }).trim().toInt()
        val vr = line.substring(line.indexOfLast { it == ',' } + 1, line.length).trim().toInt()

        println("$c $r $vc $vr")
        robots.add(Robot(Point(c, r), Velocity(vc, vr)))
    }
    val movedRobotsList = robots.map { it -> mapToEnd(it, ticks, R, C) }.toList()

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

    val finalPos = mutableListOf<List<Char>>()

    for (r in 0..<R) {
        val line = mutableListOf<Char>()
        for (c in 0..<C) {
            if (roboCount.containsKey(Point(c, r))) {
                line.add(roboCount[Point(c, r)]!!.digitToChar())
            } else {
                line.add('.')
            }
        }
        finalPos.add(line)
    }

    finalPos.forEach {
        println(it)
    }

    val q1 = movedRobotsList.filter { it -> it.x in 0..<C / 2 && it.y in 0..<R / 2 }.toList()
    val q2 = movedRobotsList.filter { it -> it.x in C / 2 + 1..<C && it.y in 0..<R / 2 }.toList()
    val q3 = movedRobotsList.filter { it -> it.x in 0..<C / 2 && it.y in R / 2 + 1..<R }.toList()
    val q4 = movedRobotsList.filter { it -> it.x in C / 2 + 1..<C && it.y in R / 2 + 1..<R }.toList()

    println("${q1.size} * ${q2.size} * ${q3.size} * ${q4.size} = ${q1.size * q2.size * q3.size * q4.size}")
}

fun mapToEnd(robot: Robot, ticks: Int, R: Int, C: Int): Point {
    val (c, r) = robot.loc
    val (vc, vr) = robot.vPerTicket
    val cEnd: Int =
        if ((c + vc * ticks) % C < 0) {
            C + (c + vc * ticks) % C
        } else {
            (c + vc * ticks) % C
        }

    val rEnd: Int =
        if ((r + vr * ticks) % R < 0) {
            R + (r + vr * ticks) % R
        } else {
            (r + vr * ticks) % R
        }
    return Point(cEnd, rEnd)
}

data class Robot(
    val loc: Point, // colum to row
    val vPerTicket: Velocity // vc to vr
)

data class Velocity(
    val vc: Int,
    val vr: Int
)