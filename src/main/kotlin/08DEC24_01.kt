import common.Point
import common.readInputAsLines
import kotlin.math.abs

fun main() {
    val input: MutableList<String> = readInputAsLines("08Dec24_01.txt").toMutableList()
    val antennas: MutableMap<Char, MutableSet<Antenna>> = mutableMapOf()
    val antiNodes: MutableSet<Point> = mutableSetOf()

    for (j in input.indices) {
        for (i in input[j].indices) {
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
                    val antiNodeA1 = a1.antiNode(a2)
                    val antiNodeA2 = a2.antiNode(a1)
                    antiNodes.add(antiNodeA1)
                    antiNodes.add(antiNodeA2)
                }
            }
        }
    }


    val validAntiNodes = antiNodes.filter {
        input.indices.contains(it.x) && input[0].indices.contains(it.y)
    }.toSet()
    validAntiNodes.forEach {
        val newLine = StringBuilder(input[it.x])
        newLine.setCharAt(it.y, '#')
        input[it.x] = newLine.toString()
    }

    println("marked antiNodes")
    input.forEach {
        println(it)
    }
    println("result ${validAntiNodes.size}")
}

data class Antenna(val type: Char, val loc: Point) {
    fun isInLine(other: Antenna): Boolean {
        return (abs(this.loc.x - other.loc.x) == abs(this.loc.y - other.loc.y)) || (this.loc.x == other.loc.x) || (this.loc.y == other.loc.y)
    }

    fun antiNode(other: Antenna): Point {
        return Point(this.loc.x + (this.loc.x - other.loc.x), this.loc.y + (this.loc.y - other.loc.y))
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is Antenna && this.type == other.type && this.loc == other.loc
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + loc.hashCode()
        return result
    }

}
