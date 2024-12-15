import common.readInputAsLines
import common.readInputAsText
import kotlin.io.path.Path

fun main() {
    val cwd = Path("").toAbsolutePath().toString()
    val input: String = readInputAsText("09Dec24_01.txt")
    val files = ArrayList<Triple<Int, Int, Int>>()
    val spaces = ArrayList<Pair<Int, Int>>()
    var fileName = 0
    var pos = 0
    val fileSpace = ArrayList<String>()
    for (i in input.indices) {
        val itemSize = input[i].digitToInt()
        if (i % 2 == 0) {
            for (j in 0..<itemSize) {
                fileSpace.add(fileName.toString())
                files.add(Triple(pos, 1, fileName))
                pos++
            }
            fileName++
        } else {
            for (j in 0..<itemSize) {
                fileSpace.add(".")
                spaces.add(pos to 1)
                pos++
            }
        }
    }
    println("file space $fileSpace")

    for (i in files.indices.reversed()) {
        val filePos = files[i].first
        val fileSize = files[i].second
        val name = files[i].third
        for (j in spaces.indices) {
            val spacePos = spaces[j].first
            val spaceSize = spaces[j].second
            if (spacePos < filePos && fileSize <= spaceSize) {
                for (k in 0..<fileSize) {
                    fileSpace[spacePos + k] = name.toString()
                    fileSpace[filePos + k] = "."
                }
                spaces[j] = spacePos + fileSize to spaceSize - fileSize
                break
            }
        }
    }

    println("fileSpace sorted $fileSpace")
    var checksum = 0L
    for (i in fileSpace.indices) {
        if (fileSpace[i] != ".") {
            checksum += i * fileSpace[i].toLong()
        }
    }

    println("checksum $checksum")

}