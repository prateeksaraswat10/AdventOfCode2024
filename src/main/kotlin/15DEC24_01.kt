import common.Loc
import common.readInputAsText
import java.util.*
import kotlin.math.absoluteValue

fun main() {
    val input = readInputAsText("15DEC24_01.txt")
    //<^>v
    val dirs = mapOf(
        '^' to Loc(0, -1),
        'v' to Loc(0, 1),
        '<' to Loc(-1, 0),
        '>' to Loc(1, 0)
    )
    // read moves
    val moves = input.split("\r\n\r\n")[1].trim().toCharArray() // windows line breaks

    val map = input.split("\r\n\r\n")[0] // windows line breaks
    // read boundary
    // read box locations - tag boxes
    // read robot location
    val bounds = mutableSetOf<Loc>()
    val boxes = mutableSetOf<Box>()
    var boxId = 0
    val lines = map.split("\r\n")
    var robotLoc: Loc = Loc(-1, -1)

    for (r in lines.indices) {
        for (c in lines[0].indices) {
            if (lines[r][c] == '#') bounds.add(Loc(c, r))
            if (lines[r][c] == 'O') boxes.add(Box(boxId++, Loc(c, r)))
            if (lines[r][c] == '@') robotLoc = Loc(c, r)
        }
    }
    // read top edge
    val top = bounds.minOfOrNull { it.row }!!
    val left = bounds.minOfOrNull { it.col }!!
    // move robot - in mov direction get queue of boxes.
    // if last item in robot + box queue can move, move all (update robot + box locations)
    for (mov in moves) {
        if (!dirs.containsKey(mov)) {
            // on windows some \r chars also get picked up.
            // didn't have time to fix this, added a check instead
            continue
        }
        val dir = dirs[mov]!!
        var nextMov = robotLoc + dir
        val queue: Queue<Box> = LinkedList()
        while (boxes.any { it.loc == nextMov }) {
            queue.add(boxes.firstOrNull { it.loc == nextMov })
            nextMov += dir
        }

        var canMove = false
        if (queue.isNotEmpty()) {
            canMove = !bounds.contains(queue.last().loc + dir)
        } else {
            canMove = !bounds.contains(robotLoc + dir)
        }
        if (canMove) {
            robotLoc += dir
            queue.forEach { box ->
                boxes.remove(box)
                boxes.add(Box(box.id, box.loc + dir))
            }
        }
    }

    var final = ""
    for (r in lines.indices) {
        for (c in lines[0].indices) {
            if (bounds.contains(Loc(c, r))) {
                final += "#"
            } else if (boxes.map { it.loc }.toSet().contains(Loc(c, r))) {
                final += "O"
            } else if (robotLoc == Loc(c, r)) {
                final += "@"
            } else {
                final += "."
            }
        }
        final += "\r\n"
    }

    println("final map")
    println(final)

    var gps = 0L
    for (box in boxes) {
        gps += (100 * (top - box.loc.row)).absoluteValue + (left - box.loc.col).absoluteValue
    }
    println("gps $gps")
}

data class Box(
    val id: Int,
    val loc: Loc
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Box
        if (this.id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}