/**
 * @brief Contains mesh data for exactly one object.
 */
export class MeshData {
    /**
     * Array of vertecies, tri-component vectors.
     * [{float: x, float: y, float z}, {...}, {...}, ...]
     */
    vertecies: Float32Array;

    /**
     * Array of triangles, contains indecies to vertecies array.
     */
    indicies: Uint32Array;

    /**
     * Two component vectors.
     * [{float: u, float: v}, {...}, {...}, ...]
     */
    texcrds: Float32Array;

    /**
     * Per vertex normals.
     */
    normals: Float32Array;

    /**
     * Per vertex colors.
     */
    vertclrs: Float32Array;

    constructor() {
        //
    }

    setVertecies(vertecies: Float32Array) {
        this.vertecies = vertecies;
    }

    setIndicies(indicies: Uint32Array) {
        this.indicies = indicies;
    }

    setTexCoords(tc: Float32Array) {
        this.texcrds = this.texcrds;
    }

    setNormals(normals: Float32Array) {
        this.normals = normals;
    }

    setVertexColors(vc: Float32Array) {
        this.vertclrs = vc;
    }

    toJson() {
        //
    }
}
