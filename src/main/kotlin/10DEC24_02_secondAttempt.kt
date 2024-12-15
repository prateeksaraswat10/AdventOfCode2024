import common.readInputAsLines

fun main() {
    val input: List<String> = readInputAsLines("10Dec24_01.txt")

    println("result ${Day10(input).solvePart2()}")
}

/**
 * Advent of Code 2024, Day 10 - Hoof It
 * This solution was taken from:
 * Blog Post/Commentary: https://todd.ginsberg.com/post/advent-of-code/2024/10/
 */
class Day10(input: List<String>) {

    private val grid: List<IntArray> = input.map { row ->
        row.map { it.digitToInt() }.toIntArray()
    }

    fun solvePart1(): Int = scoreTrails(true)

    fun solvePart2(): Int = scoreTrails(false)

    private fun scoreTrails(memory: Boolean): Int =
        grid.mapIndexed { y, row ->
            row.mapIndexed { x, i ->
                if (i == 0) findPaths(Point2D(x, y), memory) else 0
            }.sum()
        }.sum()

    private operator fun List<IntArray>.contains(at: Point2D): Boolean =
        at.y in indices && at.x in get(at.y).indices

    private operator fun List<IntArray>.get(at: Point2D): Int =
        this[at.y][at.x]

    private fun findPaths(start: Point2D, memory: Boolean): Int {
        val queue = mutableListOf(start)
        val seen = mutableSetOf<Point2D>()
        var found = 0

        while (queue.isNotEmpty()) {
            val place = queue.removeFirst()
            if (place !in seen) {
                if (memory) seen += place
                if (grid[place] == 9) found++
                else {
                    queue.addAll(
                        place.cardinalNeighbors()
                            .filter { it in grid }
                            .filter { grid[it] == grid[place] + 1 }
                    )
                }
            }
        }
        return found
    }

    data class Point2D(val x: Int, val y: Int) {

        fun cardinalNeighbors(): Set<Point2D> =
            setOf(
                this + NORTH,
                this + EAST,
                this + SOUTH,
                this + WEST
            )

        operator fun plus(other: Point2D): Point2D =
            Point2D(x + other.x, y + other.y)

        operator fun minus(other: Point2D): Point2D =
            Point2D(x - other.x, y - other.y)

        companion object {
            val NORTH = Point2D(0, -1)
            val EAST = Point2D(1, 0)
            val SOUTH = Point2D(0, 1)
            val WEST = Point2D(-1, 0)
        }
    }

}
