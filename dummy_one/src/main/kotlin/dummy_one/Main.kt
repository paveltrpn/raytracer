package dummy_one

import algebra.Vector3
import image.Tga
import java.nio.file.Paths

fun main(args: Array<String>) {
    val wd = Paths.get("").toAbsolutePath().toString()
    println("working dir is: $wd")

    val file = Tga("resources/brick.tga")

    val bar = Vector3()
    bar.x = 1.0
    bar.xyz = Vector3(2.0, 3.0, 4.0)
    println("bar: $bar")

    val rbar = bar.zyx
    println("rbar: $rbar")
}
