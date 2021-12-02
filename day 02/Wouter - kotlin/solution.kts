data class Position(val depth: Int, val horizontal: Int, val aim:Int) {
    fun execute(command: Command) = when(command.direction) {
        "forward" -> Position(depth, horizontal+command.amount, aim)
        "down" -> Position(depth+command.amount, horizontal, aim)
        "up" -> Position(depth-command.amount, horizontal, aim)
        else -> this
    }

    fun execute2(command: Command) = when(command.direction) {
        "forward" -> Position(depth + aim * command.amount, horizontal + command.amount, aim)
        "down" -> Position(depth, horizontal, aim + command.amount)
        "up" -> Position(depth, horizontal, aim - command.amount)
        else -> this
    }

    fun result() = depth * horizontal
}

data class Command(val direction: String, val amount: Int)

fun solve(input: List<String>) =
    input.map { it.split(" ") }
        .map { Command(it[0], it[1].toInt()) }
        .fold(Position(0,0,0)) { acc, command -> acc.execute(command) }
        .result()

fun solve2(input: List<String>) =
    input.map { it.split(" ") }
        .map { Command(it[0], it[1].toInt()) }
        .fold(Position(0,0,0)) { acc, command -> acc.execute2(command) }
        .result()

val testInput = """
forward 5
down 5
forward 8
up 3
down 8
forward 2
""".trimIndent().split("\n")

println("Part 1: ${solve(testInput)}")
println("Part 2: ${solve2(testInput)}")
