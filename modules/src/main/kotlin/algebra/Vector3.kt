package algebra

class Vector3 {
    private var data = DoubleArray(3) { 0.0 }

    override fun toString(): String {
        return "x: $x, y: $y, z: $z"
    }

    constructor(other: Vector3) {
        data = other.data
    }

    constructor(x: Double = 0.0, y:  Double = 0.0, z: Double = 0.0) {
        data[0] = x
        data[1] = y
        data[2] = z
    }

    fun set(x: Double, y:  Double, z: Double) {
        data[0] = x
        data[1] = y
        data[2] = z
    }

    var x: Double
        get(): Double {
            return data[0]
        }
        set(value) {
            data[0] = value
        }

    var y: Double
        get(): Double {
            return data[1]
        }
        set(value) {
            data[1] = value
        }

    var z: Double
        get(): Double {
            return data[2]
        }
        set(value) {
            data[2] = value
        }

    var xyz: Vector3
        get() {
            return Vector3(this.x, this.y, this.z)
        }
        set(other) {
            data[0] = other.x
            data[1] = other.y
            data[2] = other.z
        }

    var zyx: Vector3
        get() {
            return Vector3(this.z, this.y, this.x)
        }
        set(other) {
            data[0] = other.z
            data[1] = other.y
            data[2] = other.x
        }

    var xyzw: Vector4
        get() {
            return Vector4(this.x, this.y, this.z, 0.0)
        }
        set(other) {
            data[0] = other.x
            data[1] = other.y
            data[2] = other.z
        }
}