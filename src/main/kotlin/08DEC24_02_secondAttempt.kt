import common.readInputAsLines

fun main() {
    val input: MutableList<String> = readInputAsLines("08Dec24_02_test_01.txt").toMutableList()
    val antennas: MutableMap<Char, MutableSet<Pair<Int, Int>>> = mutableMapOf()
    for (r in input.indices) {
        for (c in input[0].indices) {
            if (input[r][c] != '.') {
                val locs = antennas.getOrDefault(input[r][c], mutableSetOf())
                locs.add(r to c)
                antennas[input[r][c]] = locs
            }
        }
    }

    val antiNodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
    for (r in input.indices) {
        for (c in input[0].indices) {
            antennas.values.forEach { vs ->
                for ((r1, c1) in vs) {
                    for ((r2, c2) in vs) {
                        if (!(r1 == r2 && c1 == c2)) {
                            val r4 = r
                            val c4 = c
                            val dr1 = r4 - r1
                            val dr2 = r4 - r2
                            val dc1 = c4 - c1
                            val dc2 = c4 - c2

                            if (dr1 * dc2 == dc1 * dr2) {
                                antiNodes.add(r4 to c4)
                            }
                        }
                    }
                }
            }
        }
    }

    println("result second ${antiNodes.size}")
}