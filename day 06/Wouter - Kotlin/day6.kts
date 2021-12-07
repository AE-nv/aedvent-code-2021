import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.operations.sum
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val input = readFileAsLinesUsingUseLines("input.txt")[0].split(",").map { it.toInt() }
    val histogram = mk.ndarray<Long, D2>( (0..8).map { Collections.frequency(input, it).toLong() }, intArrayOf(9, 1))
    val d = mk.ndarray(
        longArrayOf(
            0L, 1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
            0L, 0L, 1L, 0L, 0L, 0L, 0L, 0L, 0L,
            0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L, 0L,
            0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L,
            0L, 0L, 0L, 0L, 0L, 1L, 0L, 0L, 0L,
            0L, 0L, 0L, 0L, 0L, 0L, 1L, 0L, 0L,
            1L, 0L, 0L, 0L, 0L, 0L, 0L, 1L, 0L,
            0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 1L,
            1L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
        ),9, 9)

    println((mk.linalg.pow(d, 80) dot histogram).sum())
    println((mk.linalg.pow(d, 256) dot histogram).sum())
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
        = File(fileName).useLines { it.toList() }
