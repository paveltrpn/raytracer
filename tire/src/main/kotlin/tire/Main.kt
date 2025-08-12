package tire

import window.Window

fun main(args: Array<String>) {
    println("tire")

    val window: Window = Window()

    window.loop()

    window.destroy()
}
