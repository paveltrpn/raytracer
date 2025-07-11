package encode

import okio.AsyncTimeout
import com.github.ajalt.clikt.core.CliktCommand

fun main(args: Array<String>) {
    println("encode")

    var foo = AsyncTimeout()
    foo.withTimeout { println("ended") }
}