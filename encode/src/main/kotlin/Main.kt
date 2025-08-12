package encode

import okio.AsyncTimeout
import com.github.ajalt.clikt.core.CliktCommand
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.CoroutineScope

fun main(args: Array<String>) {
    println("encode")

    var foo = AsyncTimeout()
    foo.withTimeout { println("ended") }
}
