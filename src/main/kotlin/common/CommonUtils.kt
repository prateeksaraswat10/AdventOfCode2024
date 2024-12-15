package common

import kotlin.io.path.Path

val cwd = Path("").toAbsolutePath().toString()
val dirs = listOf(Point(0, 1), Point(0, -1), Point(1, 0), Point(-1, 0))


fun readInputAsText(fileName: String): String {
    return Path(cwd, "src", "main", "resources", "input", fileName).toFile().readText()
}

fun readInputAsLines(fileName: String): List<String> {
    return Path(cwd, "src", "main", "resources", "input", fileName).toFile().readLines()
}

data class Point(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        return (other != null) && (other is Point) && this.x == other.x && this.y == other.y
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
}

// this is easier to comprehend when reading AoC input into code
data class Loc(
    val col: Int,
    val row: Int


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Loc

        if (col != other.col) return false
        if (row != other.row) return false

        return true
    }

    override fun hashCode(): Int {
        var result = col
        result = 31 * result + row
        return result
    }

    operator fun plus(dir: Loc): Loc {
        return Loc(this.col + dir.col, this.row + dir.row)
    }
}