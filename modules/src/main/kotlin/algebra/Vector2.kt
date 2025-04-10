package algebra

class Vector2 {
    private var data = Array<Double>(2) {it -> 0.0}
    // private var foo = Array<Double>(2) {0.0}

    override fun toString(): String {
        return "x: $x, y: $y"
    }

    constructor(other: Vector2) {
        data = other.data
    }

    constructor(x: Double = 0.0, y:  Double = 0.0) {
        data[0] = x
        data[1] = y
    }

    fun set(x: Double, y:  Double) {
        data[0] = x
        data[1] = y
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
}