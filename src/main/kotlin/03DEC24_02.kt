import common.readInputAsText

fun main() {
    val input: String = readInputAsText("03Dec24_02.txt")

    var currentInput = input
    var keep = true
    var result = 0L

    do {
        if (keep) {
            val endIdx = currentInput.indexOf("don't()")
            // stop if endIdx == -1
            if (endIdx == -1) {
                result += accumulateBetter(currentInput)
                break
            }
            val toKeep = currentInput.substring(0, endIdx)
            result += accumulateBetter(toKeep)
            currentInput = currentInput.substring(endIdx, currentInput.length)
            keep = false
        } else {
            val endIdx = currentInput.indexOf("do()")
            // stop if endIdx == -1
            if (endIdx == -1) {
                break
            }
            currentInput = currentInput.substring(endIdx, currentInput.length)
            keep = true
        }
    } while (true)

    println("Modified Accumulated Result: $result")

}