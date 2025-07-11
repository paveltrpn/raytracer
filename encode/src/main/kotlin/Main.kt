package encode

import okio.AsyncTimeout

fun main(args: Array<String>) {
    println("encode")

    var foo = AsyncTimeout()
    foo.withTimeout { println("ended") }
}