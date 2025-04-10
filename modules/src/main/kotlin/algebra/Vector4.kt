package algebra

class Vector4 {
    private var data = DoubleArray(4) {0.0}

    override fun toString(): String {
        return "x: $x, y: $y, z: $z, w: $w"
    }

    constructor(other: Vector4) {
        data = other.data
    }

    constructor(x: Double = 0.0, y:  Double = 0.0, z: Double = 0.0, w: Double = 0.0) {
        data[0] = x
        data[1] = y
        data[2] = z
        data[3] = w
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

    var w: Double
        get(): Double {
            return data[3]
        }
        set(value) {
            data[3] = value
        }

    var xyzw: Vector4
        get() {
            return Vector4(this.x, this.y, this.z, this.w)
        }
        set(other) {
            data[0] = other.x
            data[1] = other.y
            data[2] = other.z
            data[3] = other.w
        }

    var wzyx: Vector4
        get() {
            return Vector4(this.w, this.z, this.y, this.x)
        }
        set(other) {
            data[0] = other.w
            data[1] = other.z
            data[2] = other.y
            data[3] = other.x
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
}