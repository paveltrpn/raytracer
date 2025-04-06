package algebra

class Vector3() {
    private var data = DoubleArray(3) { 0.0 }

    fun set(x: Double, y:  Double, z: Double) {
        data[0] = x
        data[1] = y
        data[2] = z
    }
}