package algebra

class Matrix3 {
    private var data = Array<Double>(9) {0.0}

    constructor(other: Matrix3) {
        data = other.data
    }

    var row0: Vector3
        get(): Vector3 {
            return Vector3(data[0], data[1], data[2])
        }
        set(value) {
            data[0] = value.x
            data[1] = value.y
            data[2] = value.z
        }

    var row1: Vector3
        get(): Vector3 {
            return Vector3(data[3], data[4], data[5])
        }
        set(value) {
            data[3] = value.x
            data[4] = value.y
            data[5] = value.z
        }
}