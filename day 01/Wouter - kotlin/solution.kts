fun solve(input: List<String>) : Int =
    input.map { it.toInt() }
        .windowed(2, 1)
        .sumOf { (a, b) -> compare(a, b) }

fun solve2(input: List<String>) : Int =
    input.asSequence().map { it.toInt() }
        .windowed(3, 1)
        .map { it.sum() }
        .windowed(2, 1)
        .sumOf { (a, b) -> compare(a, b) }


fun compare(a: Int, b: Int) : Int {
    if(b > a) {
        return 1
    }
    return 0
}


val testInput = """
    199
    200
    208
    210
    200
    207
    240
    269
    260
    263
""".trimIndent().split("\n")

println("Part 1: ${solve(testInput)}")
println("Part 2: ${solve2(testInput)}")
