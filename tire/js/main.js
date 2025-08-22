
import { gl } from "./render/context.js"
import { initGlobalAppState } from "./render/context.js"

import * as alg from "./algebra/algebra.js"
import { glProgram_c } from "./render/shaders.js"
import * as gmtry from "./render/geometry.js"

let gl_vert_buf
let gl_normal_buf
let gl_color_buf
let prog

let box

export let globAppState = {
	glc: null,
	width: 0,
	height: 0,
	aspect: 0,
}

async function initWGLState(state) {
	gl.viewport(0, 0, state.width, state.height)
	gl.clearColor(0.1, 0.1, 0.1, 1.0)
	gl.clearDepth(1.0)

	prog = new glProgram_c(gl)

	await prog.initShaderProgram("shaders/vert.glsl", "shaders/frag.glsl")

	box = new gmtry.gmtryInstance_c()
	box.dummyInit()
	gl_vert_buf = gl.createBuffer()
	gl.bindBuffer(gl.ARRAY_BUFFER, gl_vert_buf)
	gl.bufferData(gl.ARRAY_BUFFER, box.vertices, gl.STATIC_DRAW)

	let vertexPosition = gl.getAttribLocation(prog.program, "aVertexPosition")

	gl.vertexAttribPointer(vertexPosition, 3, gl.FLOAT, false, 0, 0)
	gl.enableVertexAttribArray(vertexPosition)
	gl_normal_buf = gl.createBuffer()
	gl.bindBuffer(gl.ARRAY_BUFFER, gl_normal_buf)
	gl.bufferData(gl.ARRAY_BUFFER, box.normals, gl.STATIC_DRAW)

	let vertexNormal = gl.getAttribLocation(prog.program, "aVertexNormal")

	gl.vertexAttribPointer(vertexNormal, 3, gl.FLOAT, false, 0, 0)
	gl.enableVertexAttribArray(vertexNormal)
	gl_color_buf = gl.createBuffer()
	gl.bindBuffer(gl.ARRAY_BUFFER, gl_color_buf)
	gl.bufferData(gl.ARRAY_BUFFER, box.colors, gl.STATIC_DRAW)

	let vertexColor = gl.getAttribLocation(prog.program, "aVertexColor")

	gl.vertexAttribPointer(vertexColor, 3, gl.FLOAT, false, 0, 0)
	gl.enableVertexAttribArray(vertexColor)
	gl.enable(gl.DEPTH_TEST)
	gl.depthFunc(gl.LEQUAL)
}

; (async function main() {
	await initGlobalAppState(globAppState)
	await initWGLState(globAppState)

	let projectionMatrix = new alg.mtrx4()

	projectionMatrix.setPerspective(alg.degToRad(45), globAppState.aspect, 0.1, 100.0)

	let transMtrx = new alg.mtrx4()
	transMtrx.setTranslate(new alg.vec3(0.0, 0.0, -7.0))
	projectionMatrix.mult(transMtrx)

	let fooQtnn = new alg.qtnn()
	fooQtnn.setAxisAngl(new alg.vec3(0.1, 0.4, 0.3), alg.degToRad(1))

	let rot = new alg.mtrx4()
	rot.fromQtnn(fooQtnn)

	let rot_trans = rot

	function loop() {
		gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT)
		prog.use()
		prog.passMatrix4fv("uProjectionMatrix", projectionMatrix)
		box.applyMtrx4ToVerts(rot)
		gl.bindBuffer(gl.ARRAY_BUFFER, gl_vert_buf)
		gl.bufferSubData(gl.ARRAY_BUFFER, 0, box.vertices)
		box.applyMtrx4ToNormals(rot_trans)
		gl.bindBuffer(gl.ARRAY_BUFFER, gl_normal_buf)
		gl.bufferSubData(gl.ARRAY_BUFFER, 0, box.normals)
		gl.drawArrays(gl.TRIANGLES, 0, 36)
		requestAnimationFrame(loop)
	}

	requestAnimationFrame(loop)
})()
