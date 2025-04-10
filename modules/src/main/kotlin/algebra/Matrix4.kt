package algebra

class Matrix4 {
    private var data = Array<Double>(16) {0.0}

    constructor(other: Matrix4) {
        data = other.data
    }

    var row0: Vector4
        get(): Vector4 {
            return Vector4(data[0], data[1], data[2], data[3])
        }
        set(value) {
            data[0] = value.x
            data[1] = value.y
            data[2] = value.z
            data[3] = value.w
        }

    var row1: Vector4
        get(): Vector4 {
            return Vector4(data[4], data[5], data[6], data[7])
        }
        set(value) {
            data[4] = value.x
            data[5] = value.y
            data[6] = value.z
            data[7] = value.w
        }
}