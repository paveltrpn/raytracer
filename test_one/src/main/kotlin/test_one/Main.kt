package test_one

import algebra.Vector3
import image.Tga
import java.nio.file.Paths

fun main(args: Array<String>) {
    val wd = Paths.get("").toAbsolutePath().toString()
    println("working dir is: $wd")

    val file = Tga("resources/brick.tga")
}