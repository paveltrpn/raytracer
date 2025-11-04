import { gl } from "../context.js";
import * as alg from "../algebra/algebra.js";
import { glProgram_c } from "./shaders.js";
import * as gmtry from "./geometry.js";

/**
 * @brief - Class manages all WebGL rendering related things.
 */
export class RenderWGL {
    width: number
    height: number
    aspect: number

    prog: any

    box: any

    glVertBuf: any
    glNormalBuf: any
    glColorBuf: any

    projectionMatrix: any

    rot: any
    rotTrans: any
    
    constructor() {
        this.width = gl.drawingBufferWidth;
        this.height = gl.drawingBufferHeight;
        this.aspect = this.width / this.height;
    }

    async init() {
        gl.viewport(0, 0, this.width, this.height);
        gl.clearColor(0.1, 0.1, 0.1, 1.0);
        gl.clearDepth(1.0);

        this.prog = new glProgram_c(gl);

        await this.prog.initShaderProgram("shaders/vert.glsl", "shaders/frag.glsl");

        this.box = new gmtry.gmtryInstance_c();
        this.box.dummyInit();
        this.glVertBuf = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, this.glVertBuf);
        gl.bufferData(gl.ARRAY_BUFFER, this.box.vertices, gl.STATIC_DRAW);

        const vertexPosition = gl.getAttribLocation(this.prog.program, "aVertexPosition");

        gl.vertexAttribPointer(vertexPosition, 3, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(vertexPosition);
        this.glNormalBuf = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, this.glNormalBuf);
        gl.bufferData(gl.ARRAY_BUFFER, this.box.normals, gl.STATIC_DRAW);

        const vertexNormal = gl.getAttribLocation(this.prog.program, "aVertexNormal");

        gl.vertexAttribPointer(vertexNormal, 3, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(vertexNormal);
        this.glColorBuf = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, this.glColorBuf);
        gl.bufferData(gl.ARRAY_BUFFER, this.box.colors, gl.STATIC_DRAW);

        const vertexColor = gl.getAttribLocation(this.prog.program, "aVertexColor");

        gl.vertexAttribPointer(vertexColor, 3, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(vertexColor);
        gl.enable(gl.DEPTH_TEST);
        gl.depthFunc(gl.LEQUAL);

        const aspect = gl.drawingBufferWidth / gl.drawingBufferHeight;
        this.projectionMatrix = new alg.mtrx4();

        this.projectionMatrix.setPerspective(alg.degToRad(45), aspect, 0.1, 100.0);

        const transMtrx = new alg.mtrx4();
        transMtrx.setTranslate(new alg.vec3(0.0, 0.0, -7.0));
        this.projectionMatrix.mult(transMtrx);

        const fooQtnn = new alg.qtnn();
        fooQtnn.setAxisAngl(new alg.vec3(0.1, 0.4, 0.3), alg.degToRad(1));

        this.rot = new alg.mtrx4();
        this.rot.fromQtnn(fooQtnn);

        this.rotTrans = this.rot;
    }

    /**
     *
     * @returns {string}
     */
    getExtensionsList() {
        return gl.getSupportedExtensions();
    }

    /**
     *
     * @returns {string}
     */
    getGlVersion() {
        return gl.getParameter(gl.VERSION);
    }

    /**
     *
     * @returns {string}
     */
    getGLSLVersion() {
        return gl.getParameter(gl.SHADING_LANGUAGE_VERSION);
    }

    /**
     *
     * @returns {Array<string>}
     */
    getGLVendor() {
        return gl.getParameter(gl.SHADING_LANGUAGE_VERSION);
    }

    loop() {
        gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
        this.prog.use();
        this.prog.passMatrix4fv("uProjectionMatrix", this.projectionMatrix);
        this.box.applyMtrx4ToVerts(this.rot);
        gl.bindBuffer(gl.ARRAY_BUFFER, this.glVertBuf);
        gl.bufferSubData(gl.ARRAY_BUFFER, 0, this.box.vertices);
        this.box.applyMtrx4ToNormals(this.rotTrans);
        gl.bindBuffer(gl.ARRAY_BUFFER, this.glNormalBuf);
        gl.bufferSubData(gl.ARRAY_BUFFER, 0, this.box.normals);
        gl.drawArrays(gl.TRIANGLES, 0, 36);
        requestAnimationFrame(this.loop.bind(this));
    }

    run() {
        requestAnimationFrame(this.loop.bind(this));
    }
}
