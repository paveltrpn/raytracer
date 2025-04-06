package image

import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

data class TgaHeader(val identsize: UByte,
                     val colorMapType : UByte,
                     val imageType : UByte,
                     val colorMapStart:  UShort,
                     val colorMapLength:  UShort,
                     val colorMapBits: UByte,
                     val xstart:  UShort,
                     val ystart:  UShort,
                     val width:  UShort,
                     val height:  UShort,
                     val bits: UByte,
                     val descriptor: UByte)

class Tga(val path: String) {
    private var width: Int = 0
    private var height: Int = 0
    private var bits: Int = 0
    private var imageType: Int = 0

    init {
        try {
            //
            val file = File(path)

            //
            val bytes = file.readBytes()

            println("${bytes.size} bytes readed")

            // Get image parameters
            imageType = bytes[2].toInt()
            width = ByteBuffer.wrap(bytes, 12, 2).order(ByteOrder.LITTLE_ENDIAN).short.toInt()
            height = ByteBuffer.wrap(bytes, 14, 2).order(ByteOrder.LITTLE_ENDIAN).short.toInt()
            bits = bytes[16].toInt()

            println("image type: $imageType; width: $width; height: $height; bits: $bits")

        } catch (e: Exception) {
            println("exception is: ${e.message}")
        }
    }
}
