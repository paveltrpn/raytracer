package film

import image.Bitmap

class Film(private val width: Int, private val height: Int) : Bitmap {
    private val _data: Array<Byte>? = null

    override fun getWidth(): Int {
        return width
    }

    override fun getHeight(): Int {
        return height
    }

    override fun getChannels(): Int {
        return 32
    }

    override fun getData(): Array<Byte> {
        return _data!!
    }
}
