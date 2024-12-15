import common.Point
import kotlin.io.path.Path

fun main() {
    val cwd = Path("").toAbsolutePath().toString()
    val input: MutableList<String> =
        Path(cwd, "src", "main", "resources", "08Dec24_01.txt").toFile().readLines().toMutableList()
    val antennas: MutableMap<Char, MutableSet<Antenna>> = mutableMapOf()
    val antiNodes: MutableSet<Point> = mutableSetOf()

    for (j in input.indices) {
        for (i in input[0].indices) {
            if (input[i][j] != '.') {
                val antenna = Antenna(input[i][j], Point(i, j))
                val antennaTypeLocs = antennas.getOrDefault(antenna.type, mutableSetOf())
                antennaTypeLocs.add(antenna)
                antennas[antenna.type] = antennaTypeLocs
            }
        }
    }

    antennas.keys.forEach { type ->
        val allInType = antennas[type]!!
        for (i in allInType.indices) {
            for (j in allInType.indices) {
                val a1 = allInType.elementAt(i)
                val a2 = allInType.elementAt(j)
                if (a1 != a2) {
                    for (jj in input.indices) {
                        for (ii in input[0].indices) {
                            val loc = Point(ii, jj)
                            if (areInLine(a1.loc, a2.loc, loc)) {
                                antiNodes.add(loc)
                            }
                        }
                    }
                }
            }
        }
    }

    antiNodes.forEach {
        val newLine = StringBuilder(input[it.x])
        newLine.setCharAt(it.y, 'T')
        input[it.x] = newLine.toString()
    }

    println("result ${antiNodes.size}")
    println("marked antiNodes")
    input.forEach {
        println(it)
    }

}

// check slope of a wrt c, and c wrt b
fun areInLine(a: Point, b: Point, c: Point): Boolean {
    return (a.x - c.x) * (c.y - b.y) == (c.x - b.x) * (a.y - c.y)
}