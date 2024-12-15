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
    val moves = input.split("\r\n\r\n")[1].trim().toCharArray()


    val stringMap = processInput(input);
    println("initial state")
    println(stringMap)
    // read boundary
    // read box locations - tag boxes
    // read robot location
    val bounds = mutableSetOf<Loc>()
    val boxes = mutableSetOf<WideBox>()
    var boxId = 0
    val lines = stringMap.trim().split("\r\n")
    var robotLoc: Loc = Loc(-1, -1)

    for (r in lines.indices) {
        for (c in lines[0].indices) {
            if (lines[r][c] == '#') bounds.add(Loc(c, r))
            if (lines[r][c] == '[') boxes.add(WideBox(boxId++, Loc(c, r), Loc(c + 1, r)))
            if (lines[r][c] == '@') robotLoc = Loc(c, r)
        }
    }
    // read top edge
    val top = bounds.map { it.row }.min()
    val left = bounds.map { it.col }.min() + 1

    // move robot - in mov direction get queue of boxes. if last item in robot + box queue can move (any boxes touch bounds in direction of move), move all (update robot + box locations)
    for (move in moves) {
        if (!dirs.containsKey(move)) {
            continue
        }
        val dir = dirs[move]!!
        val nextLoc = robotLoc + dir
        val queue: Queue<WideBox> = LinkedList()
        // add any box the  robot touches in the next direction
        if (boxes.any { it.left == nextLoc } || boxes.any { it.right == nextLoc }) {
            queue.addAll(boxes.filter { it.left == nextLoc })
            queue.addAll(boxes.filter { it.right == nextLoc })
        }
        // get region of touching boxes
        val region = mutableSetOf<WideBox>()
        while (queue.isNotEmpty()) {
            val box: WideBox = queue.remove()
            if (region.contains(box)) {
                continue
            }
            region.add(box)
            val nextBox = box.move(dir) // next box in the direction of the move
            // add all boxes in the direction of the move
            queue.addAll(boxes.filter { it.left == nextBox.left })
            queue.addAll(boxes.filter { it.right == nextBox.left })
            queue.addAll(boxes.filter { it.left == nextBox.right })
            queue.addAll(boxes.filter { it.right == nextBox.right })
        }

        var canMove = !bounds.contains(nextLoc)
        if (region.isNotEmpty()) {
            // can move if none of the boxes will go into a bound
            canMove = !(region.any { bounds.contains(it.left + dir) } || region.any { bounds.contains(it.right + dir) })
        }

        if (canMove) {
            robotLoc += dir
            region.forEach { box ->
                boxes.remove(box)
                boxes.add(box.move(dir))
            }
        }
    }

    var gps = 0
    for (box in boxes) {
        gps += (100 * (top - box.left.row)).absoluteValue + (left - box.right.col).absoluteValue
    }

    println("final map")
    print(robotLoc, boxes, bounds, lines)
    println("gps part 2 $gps")
}

private fun processInput(input: String): String {
    val map = input.split("\r\n\r\n")[0]
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

    var stringMap = ""
    for (r in lines.indices) {
        for (c in lines[0].indices) {
            if (bounds.contains(Loc(c, r))) {
                stringMap += "##"
            } else if (boxes.map { it.loc }.toSet().contains(Loc(c, r))) {
                stringMap += "[]"
            } else if (robotLoc == Loc(c, r)) {
                stringMap += "@."
            } else {
                stringMap += ".."
            }
        }
        stringMap += "\r\n"
    }
    return stringMap
}

data class WideBox(val id: Int, val left: Loc, val right: Loc) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WideBox

        if (id == other.id) return true

        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }

    fun move(dir: Loc): WideBox {
        return WideBox(this.id, this.left + dir, this.right + dir)
    }
}

private fun print(robotLoc: Loc, boxes: Set<WideBox>, bounds: Set<Loc>, lines: List<String>) {
    var final = ""
    for (r in lines.indices) {
        for (c in lines[0].indices) {
            if (bounds.contains(Loc(c, r))) {
                final += "#"
            } else if (boxes.map { it.left }.toSet().contains(Loc(c, r))) {
                final += "["
            } else if (boxes.map { it.right }.toSet().contains(Loc(c, r))) {
                final += "]"
            } else if (robotLoc == Loc(c, r)) {
                final += "@"
            } else {
                final += "."
            }
        }
        final += "\r\n"
    }
    println(final)
}