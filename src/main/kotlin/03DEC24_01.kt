import common.readInputAsText

fun main() {
    val input: String = readInputAsText("03Dec24_01.txt")
    println("Accumulated Result: ${accumulateBetter(input)}")
}

fun accumulate(input: String): Long {
    val matcher = "mul\\(\\d+,\\d+\\)".toRegex()
    val matches: Sequence<String> = matcher.findAll(input).map { it.value }
    var result = 0L
    matches.forEach {
        println("input $it")
        val numOne = it.split("mul(")[1].split(",")[0].toLong()
        val numTwo = it.split("mul(")[1].split(",")[1].split(")")[0].toLong()
        println("$numOne x $numTwo = ${numOne * numTwo}")
        result += numOne * numTwo
    }
    return result
}

fun accumulateBetter(input: String): Long {
    var result = 0L

    val matcher = "mul\\((\\d+),(\\d+)\\)".toRegex()
    val matches: Sequence<MatchResult> = matcher.findAll(input)

    matches.forEach {
        val groups: MatchGroupCollection = it.groups
        if (groups.size != 3) {
            println("Illegal state or bad match")
        }
        val numOne = groups[1]!!.value.toLong()
        val numTwo = groups[2]!!.value.toLong()

        println("$numOne x $numTwo = ${numOne * numTwo}")
        result += numOne * numTwo
    }

    return result
}