package image

interface Bitmap {
    fun getData(): Array<Byte>
    fun getWidth(): Int
    fun getHeight(): Int
    fun getChannels(): Int
}
