import common.Point
import common.readInputAsLines
import java.util.*

fun main() {
    val input: List<String> = readInputAsLines("12Dec24_01.txt")

    val mappedTiles = mutableSetOf<Point>()
    val allPlots = mutableSetOf<Plot>()

    for (i in input.indices) {
        for (j in input[0].indices) {
            val current = Point(i, j)
            if (!mappedTiles.contains(current)) {
                val plot = computePlot(current, input)
                mappedTiles.addAll(plot.tiles)
                allPlots.add(plot)
            }
        }
    }

    var result = 0L
    for (plot in allPlots) {
        println("A region of ${plot.type} with price ${plot.area} * ${plot.perimeter} = ${plot.perimeter * plot.area}")
        result += plot.perimeter * plot.area
    }

    println("result with perimeter $result")

    result = 0
    for (plot in allPlots) {
        println("A region of ${plot.type} with price ${plot.area} * ${plot.sides} = ${plot.sides * plot.area}")
        result += plot.sides * plot.area
    }
    println("result with edge $result")
}

data class Plot(
    val type: Char,
    val perimeter: Int,
    val area: Int,
    val tiles: Set<Point>,
    val sides: Int
)

private fun computePlot(startAt: Point, input: List<String>): Plot {
    val dirs = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))
    val rows = input.size
    val cols = input[0].length
    var perimeter: Int = 0

    val tiles = mutableSetOf<Point>()
    val search: Queue<Point> = LinkedList()
    val edges = mutableSetOf<Point>()
    search.add(startAt)
    while (search.isNotEmpty()) {
        val point = search.remove()
        if (tiles.contains(point)) {
            continue
        }

        tiles.add(point)

        for (dir in dirs) {
            val next = point + dir
            if (next.x in 0..<rows && next.y in 0..<cols && input[startAt.x][startAt.y] == input[next.x][next.y]) {
                search.add(next)
            } else {
                perimeter++
                edges.add(next)
            }
        }
    }
    var sides = 0
    for (dir in dirs) {
        sides += computeSides(edges, tiles, dir)
    }
    return Plot(input[startAt.x][startAt.y], perimeter, tiles.size, tiles, sides)
}

private fun computeSides(edges: Set<Point>, tiles: Set<Point>, side: Point): Int {
    val dirs = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))
    var sides = 0
    val seen = mutableSetOf<Point>()
    for (edge in edges) {
        val tile = edge + side
        if (!seen.contains(edge) && tiles.contains(tile)) {
            sides++
            val queue: Queue<Point> = LinkedList()
            queue.add(edge)
            while (queue.isNotEmpty()) {
                val cur = queue.remove()
                if (seen.contains(cur)) {
                    continue
                }
                seen.add(cur)
                for (dir in dirs) {
                    val next = cur + dir
                    if (edges.contains(next) && tiles.contains(next + side)) {
                        queue.add(next)
                    }
                }
            }
        }
    }

    return sides
}