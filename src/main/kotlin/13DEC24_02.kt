import common.readInputAsText

fun main() {
    val input: String = readInputAsText("13Dec24_01.txt")

    val machines = input.split("\r\n\r\n")
    val xCost = 3L;
    val yCost = 1L
    val limit = 100L
    val error = 10000000000000L
    var result = 0L


    machines.forEach { machine ->
        val lines = machine.split("\r\n")
        val lineOne = lines[0]
        val lineTwo = lines[1]
        val lineThree = lines[2]

        val a1 = lineOne.substring(lineOne.indexOfFirst { it == '+' } + 1, lineOne.indexOfFirst { it == ',' }).trim()
            .toLong()
        val b1 = lineTwo.substring(lineTwo.indexOfFirst { it == '+' } + 1, lineTwo.indexOfFirst { it == ',' }).trim()
            .toLong()

        val a2 = lineOne.substring(lineOne.indexOfLast { it == '+' } + 1, lineOne.length).trim().toLong()
        val b2 = lineTwo.substring(lineTwo.indexOfLast { it == '+' } + 1, lineTwo.length).trim().toLong()

        val c1 =
            lineThree.substring(lineThree.indexOfFirst { it == '=' } + 1, lineThree.indexOfFirst { it == ',' }).trim()
                .toLong() + error
        val c2 = lineThree.substring(lineThree.indexOfLast { it == '=' } + 1, lineThree.length).trim().toLong() + error

        println(lineOne)
        println(lineTwo)
        println(lineThree)
        println("${a1}x + ${b1}y = $c1")
        println("${a2}x + ${b2}y = $c2")

        val d = a1 * b2 - a2 * b1
        val dx = c1 * b2 - c2 * b1
        val dy = a1 * c2 - a2 * c1

        if (d == 0L) {
            if (dx == 0L || dy == 0L) {
                println("infinite solutions")
            } else {
                println("no solution")
            }
        } else {
            if (dx % d == 0L && dy % d == 0L) {
                val x = dx / d
                val y = dy / d
                println("solution x = $x, and y = $y")
                if (x <= limit && y <= limit) {
                    println("both solutions in limit")
                }
                result += x * xCost + y * yCost
            } else {
                println("no solution")
            }
        }

        println("\n")
    }

    println("result = $result")
}