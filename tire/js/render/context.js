export let gl = null

export async function initGlobalAppState(state) {
	const canvas_id = "#glcanvas"
	const text_field = "log_out"

	let html_canvas = document.querySelector(canvas_id)

	state.glc = gl = html_canvas.getContext("webgl2", {
		antialias: true,
		depth: true,
	})

	console.log(gl)

	state.width = gl.drawingBufferWidth
	state.height = gl.drawingBufferHeight
	state.aspect = state.width / state.height

	if (!gl) {
		alert("cube_c::setup(): Unable to initialize WebGL. Your browser or machine may not support it.")
		return
	}

	let log_out = document.getElementById(text_field)

	log_out.innerText =
		gl.getParameter(gl.VERSION) +
		"; " +
		gl.getParameter(gl.SHADING_LANGUAGE_VERSION) +
		"; " +
		gl.getParameter(gl.VENDOR)

	let gl_ext = gl.getSupportedExtensions()

	for (let i = 0; i < gl_ext.length; i++) {
		log_out.innerText = log_out.innerText + gl_ext[i] + " ;"
	}
}
